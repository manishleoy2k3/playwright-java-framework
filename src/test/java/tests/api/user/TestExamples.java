package tests.api.user;


import static io.restassured.RestAssured.given;

import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Paths;

import io.restassured.RestAssured;
import io.restassured.response.Response;


public class TestExamples {

	public static void main(String[] args) {
		try {
			Response response = given()
				.baseUri("https://reqres.in")
				.header("Content-Type", "application/json" )
			.when()
				.get("/api/users?page=2");

			//System.out.println("🔍 Response: " + response.asPrettyString());
			//System.out.println("🔍 Response time: " + response.time());
			//System.out.println("🔍 Response time: " + response.getTime());
			//System.out.println("🔍 Response content type: " + response.getContentType());
			//System.out.println("🔍 Response Status Line: " + response.statusLine());
			
			//System.out.println("🔍 Response Json path view: " + response.jsonPath().getString("data.email"));
			System.out.println("🔍 Response Json path view: " + response.jsonPath().getString("data.find { it.id == 7 }.avatar"));
			
			String avatarImageUrl = response.jsonPath().getString("data.find { it.id == 8 }.avatar");
			// Step 1: Fetch image as byte array
	        byte[] imageBytes = RestAssured
	            .given()
	            .when()
	                .get(avatarImageUrl)
	            .then()
	                .statusCode(200)
	                .extract()
	                .asByteArray();

	     // Step 2: Define the destination path in your framework
	        String filePath = Paths.get("src", "test", "resources", "images", "downloaded-image.jpg").toString();

	        // Step 2: Write byte array to local file
	        try (FileOutputStream outputStream = new FileOutputStream(filePath)) {
	            outputStream.write(imageBytes);
	        } catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

	        System.out.println("✅ Image saved successfully.");
			
			response.then().statusCode(200)
		    .body(matchesJsonSchemaInClasspath("schemas/UserList.json"));
			
			System.out.println("✅ Schema validation passed.");
		} catch (AssertionError e) {
			System.err.println("❌ Schema validation failed: " + e.getMessage());
		}
		/*
		RestAssured.baseURI = "https://jsonplaceholder.typicode.com";
		String data= "{\"completed\":false,\"Title\":\"NewTodotask\"}";
		
        Response response = RestAssured.given()
                .header("Content-Type","application/json")
                .body(data)
                .when()
                .post("/posts")
                .then()
                .log().all()
                .statusCode(201)
                .contentType(ContentType.JSON)
                .extract().response();

        
        System.out.println(response.asPrettyString());
        Assert.assertEquals(201, response.getStatusCode());
        Assert.assertEquals( response.jsonPath().getString("Title"), "NewTodotask");
        Assert.assertTrue(true, "completed");
        
        System.out.println("DONE");
        */

	}

}
