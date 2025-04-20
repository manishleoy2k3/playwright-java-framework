package utils;

import io.restassured.response.Response;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

import java.util.Map;

public class ResponseValidator {

    public static void validateStatusCode(Response response, int expectedCode) {
        assertEquals(response.getStatusCode(), expectedCode, "Status code mismatch");
    }

    public static void validateHeaders(Response response, Map<String, String> expectedHeaders) {
        for (String key : expectedHeaders.keySet()) {
            assertTrue(response.getHeaders().hasHeaderWithName(key), "Missing header: " + key);
            assertEquals(response.getHeader(key), expectedHeaders.get(key), "Header value mismatch for: " + key);
        }
    }

    public static void validateResponseTime(Response response, long maxResponseTimeMillis) {
        assertTrue(response.time() <= maxResponseTimeMillis, "Response time exceeded limit");
    }

    public static void validateResponseBodyContains(Response response, String expectedContent) {
        assertTrue(response.getBody().asString().contains(expectedContent), "Body does not contain expected content");
    }
}