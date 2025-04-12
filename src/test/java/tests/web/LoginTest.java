package tests.web;

import org.testng.annotations.Test;
import org.testng.AssertJUnit;
import io.qameta.allure.*;
import tests.base.TestBase;

@Epic("Web Automation")
@Feature("Login")
public class LoginTest extends TestBase {

    @Test
    @Story("Valid Login")
    @Description("Verify user can log in with valid credentials")
    public void testLogin() {
        page.navigate("https://example.com/login");
        page.fill("#username", "testuser");
        page.fill("#password", "password123");
        page.click("#loginBtn");
        AssertJUnit.assertTrue(page.url().contains("dashboard"));
    }
}
