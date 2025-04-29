package tests.api.user;

import io.restassured.response.Response;
import org.testng.annotations.Test;
import utils.BaseTest;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.testng.Assert.*;

public class AdvancedEdgeCaseTests extends BaseTest {

    @Test(groups = {"rate-limiting"}, description = "Test API rate limiting by sending rapid requests")
    public void testApiRateLimiting() {
        for (int i = 0; i < 10; i++) {
            Response response = getBaseRequestBuilder()
                    .build()
                    .when()
                    .get("/api/users?page=2");

            assertTrue(response.statusCode() == 200 || response.statusCode() == 429, "Rate limit triggered or request successful");
        }
    }

    @Test(groups = {"timeout"}, description = "Test API timeout handling")
    public void testApiTimeoutHandling() {
        long startTime = System.currentTimeMillis();
        Response response = getBaseRequestBuilder()
                .build()
                .when()
                .get("/api/users?page=2");

        long responseTime = System.currentTimeMillis() - startTime;
        System.out.println("Response Time: " + responseTime + " ms");
        assertThat("API should respond within 3 seconds", responseTime, lessThan(3000L));
    }

    @Test(groups = {"concurrency"}, description = "Test concurrent requests for thread safety")
    public void testConcurrentRequests() {
        Runnable apiTask = () -> {
            Response response = getBaseRequestBuilder()
                    .build()
                    .when()
                    .get("/api/users?page=2");
            assertEquals(response.getStatusCode(), 200);
        };

        Thread thread1 = new Thread(apiTask);
        Thread thread2 = new Thread(apiTask);
        Thread thread3 = new Thread(apiTask);

        thread1.start();
        thread2.start();
        thread3.start();

        try {
            thread1.join();
            thread2.join();
            thread3.join();
        } catch (InterruptedException e) {
            fail("Thread execution interrupted");
        }
    }
}