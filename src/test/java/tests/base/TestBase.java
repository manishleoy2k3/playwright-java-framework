package tests.base;

import org.testng.annotations.*;
import com.microsoft.playwright.*;

public class TestBase {
    protected static Playwright playwright;
    protected static Browser browser;
    protected BrowserContext context;
    protected Page page;

    @BeforeClass
    static void setupAll() {
        playwright = Playwright.create();
        browser = playwright.chromium().launch(new BrowserType.LaunchOptions().setHeadless(true));
    }

    @BeforeMethod
    void setup() {
        context = browser.newContext();
        page = context.newPage();
    }

    @AfterMethod
    void tearDown() {
        context.close();
    }

    @AfterClass
    static void tearDownAll() {
        playwright.close();
    }
}
