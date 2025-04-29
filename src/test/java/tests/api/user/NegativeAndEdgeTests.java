package tests.api.user;

import builder.RequestBuilder;
import io.restassured.response.Response;
import model.CreateUserRequest;
import org.testng.annotations.Test;
import utils.BaseTest;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.testng.Assert.assertEquals;

public class NegativeAndEdgeTests extends BaseTest {

    @Test(description = "Test creating a user with missing name")
    public void createUserMissingName_ShouldReturn400() {
        CreateUserRequest requestBody = new CreateUserRequest(null, "leader");

        Response response = getBaseRequestBuilder()
                .setBody(requestBody)
                .build()
                .when()
                .post("/api/users");

        assertEquals(response.getStatusCode(), 400, "Expected status 400 for missing name field");
    }

    @Test(description = "Test calling non-existing endpoint")
    public void invalidEndpoint_ShouldReturn404() {
        Response response = getBaseRequestBuilder()
                .build()
                .when()
                .get("/api/nonexisting");

        assertEquals(response.getStatusCode(), 404);
        assertThat(response.body().asString(), containsString(""));
    }

    @Test(description = "Test GET with invalid query param")
    public void invalidQueryParam_ShouldReturnEmptyOrError() {
        Response response = getBaseRequestBuilder()
                .build()
                .when()
                .get("/api/users?page=-99");

        assertEquals(response.getStatusCode(), 200);
        assertThat(response.jsonPath().getList("data"), empty());
    }

    @Test(description = "Test with invalid HTTP method")
    public void invalidHttpMethod_ShouldReturn405() {
        Response response = getBaseRequestBuilder()
                .build()
                .when()
                .put("/api/users/2");

        assertThat(response.statusCode(), is(oneOf(400, 405)));
    }

    @Test(description = "Test API without required Authorization header")
    public void missingAuth_ShouldReturnUnauthorized() {
        Response response = new RequestBuilder()
                .setBaseUri(BASE_URL)
                .build()
                .when()
                .get("/api/protected/resource"); // hypothetical protected resource

        assertThat(response.statusCode(), is(oneOf(401, 403)));
    }

    @Test(description = "Test API with very large payload")
    public void largePayload_ShouldHandleGracefully() {
        StringBuilder bigString = new StringBuilder();
        for (int i = 0; i < 10000; i++) bigString.append("x");

        CreateUserRequest requestBody = new CreateUserRequest(bigString.toString(), "leader");

        Response response = getBaseRequestBuilder()
                .setBody(requestBody)
                .build()
                .when()
                .post("/api/users");

        assertThat(response.statusCode(), is(oneOf(400, 413, 500)));
    }

    @Test(description = "Test response time for API")
    public void responseTimeShouldBeAcceptable() {
        Response response = getBaseRequestBuilder()
                .build()
                .when()
                .get("/api/users?page=2");

        assertThat(response.getTime(), lessThan(3000L)); // max 3 seconds
    }
}