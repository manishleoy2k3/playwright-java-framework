package tests;

import builder.RequestBuilder;
import io.restassured.module.jsv.JsonSchemaValidator;
import io.restassured.response.Response;
import model.CreateUserRequest;
import org.testng.annotations.Test;

import utils.BaseTest;
import utils.ResponseValidator;
import java.util.HashMap;
import java.util.Map;

public class RealWorldApiTests extends BaseTest {

    @Test
    public void getUserListWithValidation() {
        HashMap<String, String> headers = new HashMap<>();
        headers.put("Accept", "application/json");

        Response response = getBaseRequestBuilder()
                .setHeaders(headers)
                .build()
                .get("/api/users?page=2");

        ResponseValidator.validateStatusCode(response, 200);
        ResponseValidator.validateResponseTime(response, 2000);
        ResponseValidator.validateResponseBodyContains(response, "data");

        response.then().assertThat()
                .body(JsonSchemaValidator.matchesJsonSchemaInClasspath("schemas/getUsersSchema.json"));
    }

    @Test
    public void updateUserTest() {
        HashMap<String, String> headers = new HashMap<>();
        headers.put("Content-Type", "application/json");

        CreateUserRequest body = new CreateUserRequest("morpheus", "zion resident");

        Response response = getBaseRequestBuilder()
                .setHeaders(headers)
                .setBody(body)
                .build()
                .put("/api/users/2");

        ResponseValidator.validateStatusCode(response, 200);
        ResponseValidator.validateResponseTime(response, 1500);
        ResponseValidator.validateResponseBodyContains(response, "updatedAt");
    }

    @Test
    public void deleteUserTest() {
        Response response = getBaseRequestBuilder()
                .build()
                .delete("/api/users/2");

        ResponseValidator.validateStatusCode(response, 204);
        ResponseValidator.validateResponseTime(response, 1500);
    }

    @Test
    public void validateHeadersTest() {
        Response response = getBaseRequestBuilder()
                .build()
                .get("/api/users/2");

        Map<String, String> expectedHeaders = new HashMap<>();
        expectedHeaders.put("Content-Type", "application/json; charset=utf-8");

        ResponseValidator.validateHeaders(response, expectedHeaders);
    }
}
