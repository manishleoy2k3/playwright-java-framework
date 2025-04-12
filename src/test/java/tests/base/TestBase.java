package tests.base;

import org.testng.annotations.*;
import org.testng.annotations.Listeners;
import com.microsoft.playwright.*;

import io.qameta.allure.testng.AllureTestNg;

@Listeners({AllureTestNg.class})
public class TestBase {
    protected static Playwright playwright;
    protected static Browser browser;
    protected BrowserContext context;
    protected Page page;

    @BeforeClass
    public void setupAll() {
    	System.setProperty("allure.results.directory", "target/allure-results");
        playwright = Playwright.create();
        browser = playwright.chromium().launch(new BrowserType.LaunchOptions().setHeadless(false).setSlowMo(100));
    }

    @BeforeMethod
    public void setup() {
        context = browser.newContext();
        page = context.newPage();
    }

    @AfterMethod
    public void tearDown() {
        context.close();
    }

    @AfterClass
    public void tearDownAll() {
        playwright.close();
    }
}
