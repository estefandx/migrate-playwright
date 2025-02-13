package com.epam.mentoring.taf;

import com.microsoft.playwright.*;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;


public class AbstractTestPlayright {

    protected final static String baseUrl = "https://conduit-realworld-example-app.fly.dev/";
    protected final static String UI_URL = "https://conduit-realworld-example-app.fly.dev/";
    protected final static String API_URL = "https://conduit-realworld-example-app.fly.dev/";
    protected Playwright playwright;
    protected Browser browser;
    protected BrowserContext context;
    protected Page page;


    @BeforeMethod
    public void setup() {
        playwright = Playwright.create();
        browser = playwright.chromium().launch(new BrowserType.LaunchOptions().setHeadless(false));
        context = browser.newContext();
        page = context.newPage();
        page.navigate(baseUrl);
    }

    @AfterMethod
    public void teardown() {
        if (page != null) {
            page.close();
        }
        if (browser != null) {
            browser.close();
        }
        if (playwright != null) {
            playwright.close();
        }
    }
}
