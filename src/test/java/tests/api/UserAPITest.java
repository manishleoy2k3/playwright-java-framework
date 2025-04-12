package tests.api;

import org.testng.annotations.*;
import io.qameta.allure.*;
import io.restassured.RestAssured;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;

@Epic("API Automation")
@Feature("User API")
public class UserAPITest {

	@BeforeClass
    public static void setup() {
        RestAssured.baseURI = "https://jsonplaceholder.typicode.com";
    }

    @Test
    @Story("Get User by ID")
    @Description("Verify user details are fetched successfully")
    public void testGetUser() {
        given()
        .when()
            .get("/users/1")
        .then()
            .statusCode(200)
            .body("username", equalTo("Bret"));
    }
}
