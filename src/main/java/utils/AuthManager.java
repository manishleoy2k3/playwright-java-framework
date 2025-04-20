package utils;

import io.restassured.http.ContentType;
import io.restassured.response.Response;

import static io.restassured.RestAssured.given;

public class AuthManager {

    private static String token;

    public static String getToken() {
        if (token == null || token.isEmpty()) {
            token = generateToken();
        }
        return token;
    }

    private static String generateToken() {
        Response response = given()
                .baseUri("https://reqres.in")
                .contentType(ContentType.JSON)
                .body("{\"email\": \"eve.holt@reqres.in\", \"password\": \"cityslicka\"}")
                .post("/api/login");

        if (response.getStatusCode() != 200) {
            throw new RuntimeException("Failed to get token: " + response.getStatusLine());
        }

        String tokenValue = response.jsonPath().getString("token");

        if (tokenValue == null || tokenValue.isEmpty()) {
            throw new RuntimeException("Token not found in login response");
        }

        return tokenValue;
    }

    public static void refreshToken() {
        token = generateToken();
    }
}
