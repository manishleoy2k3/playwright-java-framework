package tests.api.user;

import org.testng.annotations.*;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

import utils.BaseTest;
import utils.WireMockUtils;

public class UserServiceMockTests extends BaseTest{

	@BeforeClass
    public void setUpStubs() throws Exception {
        WireMockUtils.stubGetFromFile("/api/user/123",
            "src/test/resources/mocks/user-123.json", 200);
    }

    @Test
    public void testMockedUserFetch() {
        given()
            .when().get("/api/user/123")
            .then()
            .statusCode(200)
            .body("name", equalTo("John Doe"))
            .body("status", equalTo("active"));
    }
}
