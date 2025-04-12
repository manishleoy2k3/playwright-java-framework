package tests.web;

import io.qameta.allure.*;

import java.nio.file.Paths;
import java.util.regex.Pattern;

import org.testng.Assert;
import org.testng.annotations.Test;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.options.AriaRole;

import tests.base.TestBase;
import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;

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
        
        page.navigate("https://playwright.dev/");
        page.screenshot(new Page.ScreenshotOptions().setPath(Paths.get("playwright.png")));
       

        // Expect a title "to contain" a substring.
        assertThat(page).hasTitle(Pattern.compile("Playwright"));

        // create a locator
        Locator getStarted = page.getByRole(AriaRole.LINK, new Page.GetByRoleOptions().setName("Get Started"));

        // Expect an attribute "to be strictly equal" to the value.
        assertThat(getStarted).hasAttribute("href", "/docs/intro");

        // Click the get started link.
        getStarted.click();

        // Expects page to have a heading with the name of Installation.
        assertThat(page.getByRole(AriaRole.HEADING,
           new Page.GetByRoleOptions().setName("Installation"))).isVisible();
    }
}
