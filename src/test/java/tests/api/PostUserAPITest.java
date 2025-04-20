package tests.api;

import org.testng.annotations.*;
import io.qameta.allure.*;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import utils.BaseTest;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;
import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchema;

import java.io.File;

@Epic("API Automation")
@Feature("User API")
public class PostUserAPITest extends BaseTest{

	
    
    @Test(description = "POST user using REST Assured and validate JSON schema")
    @Severity(SeverityLevel.CRITICAL)
    @Story("POST User - Schema Validation with REST Assured")
    public void postUserWithSchemaValidation() {

    	File schemaFile = new File(
    		    getClass().getClassLoader().getResource("schemas/create-user-schema.json").getFile()
    		);
    	
        String payload = """
        {
          "name": "John",
          "job": "leader"
        }
        """;

        Allure.step("Sending POST request to create user...");

        Response response = given()
            .baseUri("https://reqres.in")
            .header("Content-Type", "application/json")
            .body(payload)
        .when()
            .post("/api/users")
        .then()
            .assertThat()
            .statusCode(201)
            .and()
            .contentType(ContentType.JSON)
            .body(matchesJsonSchema(schemaFile))
            .extract()
            .response();

        Allure.step("Response validated and schema matched successfully.");
        System.out.println("Response: " + response.asPrettyString());
    }
    
}
