package tests.api.user;

import io.restassured.module.jsv.JsonSchemaValidator;
import io.restassured.response.Response;
import model.CreateUserRequest;
import utils.AuthManager;
import utils.BaseTest;
import utils.ResponseValidator;

import org.testng.annotations.Test;
import static org.testng.Assert.assertEquals;
import java.util.HashMap;

public class UserApiTests extends BaseTest{
    @Test
    public void createUserTest() {
        HashMap<String, String> headers = new HashMap<>();
        headers.put("Content-Type", "application/json");

        CreateUserRequest body = new CreateUserRequest("morpheus", "leader");

        Response response = getBaseRequestBuilder()
            .setHeaders(headers)
            .setBody(body)
            .build()
            .post("/api/users");

        assertEquals(response.getStatusCode(), 201);
    }
    
    @Test
    public void createUserTestWithAllValidations() {
        HashMap<String, String> headers = new HashMap<>();
        headers.put("Content-Type", "application/json");
        headers.put("Authorization", "Bearer " + AuthManager.getToken());

        CreateUserRequest body = new CreateUserRequest("morpheus", "leader");

        Response response = getBaseRequestBuilder()
                .setBaseUri("https://reqres.in")
                .setHeaders(headers)
                .setBody(body)
                .build()
                .post("/api/users");

        ResponseValidator.validateStatusCode(response, 201);
        ResponseValidator.validateResponseBodyContains(response, "morpheus");
        ResponseValidator.validateResponseTime(response, 2000);

        response.then().assertThat()
                .body(JsonSchemaValidator.matchesJsonSchemaInClasspath("schemas/createUserSchema.json"));
    }
}