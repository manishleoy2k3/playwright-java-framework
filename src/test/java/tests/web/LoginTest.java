package tests.web;

import io.qameta.allure.*;
import org.testng.Assert;
import org.testng.annotations.Test;
import tests.base.TestBase;

@Epic("Web Tests")
@Feature("Sample Login Page")
public class LoginTest extends TestBase {

    @Test(description = "Open Example.com and check title")
    @Story("Basic Page Title Check")
    @Severity(SeverityLevel.NORMAL)
    public void verifyPageTitle() {
        Allure.step("Navigating to example.com");
        page.navigate("https://example.com");

        String title = page.title();
        Allure.step("Page title is: " + title);

        Assert.assertEquals(title, "Example Domain", "Title should match expected value");
    }
}
