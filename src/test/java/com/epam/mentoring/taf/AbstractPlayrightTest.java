package com.epam.mentoring.taf;

import com.epam.mentoring.taf.listener.ScreenshotListener;
import com.epam.reportportal.testng.ReportPortalTestNGListener;
import com.microsoft.playwright.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;

import org.testng.annotations.Listeners;

@Listeners({ScreenshotListener.class, ReportPortalTestNGListener.class})
public class AbstractPlayrightTest {

    protected final static String baseUrl = "https://conduit-realworld-example-app.fly.dev/";
    protected final static String UI_URL = "https://conduit-realworld-example-app.fly.dev/";
    protected final static String API_URL = "https://conduit-realworld-example-app.fly.dev/";
    protected Playwright playwright;
    protected Browser browser;
    protected BrowserContext context;
    protected Page page;
    protected static final Logger logger = LogManager.getLogger(AbstractPlayrightTest.class);


    @BeforeMethod(onlyForGroups = "UI")
    public void setup() {
        logger.info("initializing  driver");
        playwright = Playwright.create();
        browser = playwright.chromium().launch(new BrowserType.LaunchOptions().setHeadless(false));
        context = browser.newContext();
        page = context.newPage();
        logger.info("open  page {}",baseUrl);
        page.navigate(baseUrl);
        ScreenshotListener.page = page;
    }

    @AfterMethod(onlyForGroups = "UI")
    public void teardown() {
        logger.info("close driver");
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
