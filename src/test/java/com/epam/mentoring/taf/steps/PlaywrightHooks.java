package com.epam.mentoring.taf.steps;


import com.epam.mentoring.taf.listener.ScreenshotListener;
import com.microsoft.playwright.*;
import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.Scenario;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class PlaywrightHooks {

    private static PlaywrightHooks instance;
    private static final Logger logger = LogManager.getLogger(PlaywrightHooks.class);

    private Playwright playwright;
    private Browser browser;
    private BrowserContext context;
    public Page page;
    private APIResponse response;

    public PlaywrightHooks() {
        instance = this;
    }

    public static PlaywrightHooks getInstance() {
        if (instance == null) {
            throw new IllegalStateException("PlaywrightHooks has not been initialized.");
        }
        return instance;
    }

    @Before("@UI")
    public void setup(Scenario scenario) {
        logger.info("Running Before Hook for the scenario:" + scenario.getName());
        initializePlaywright();
        navigateToBaseUrl();
    }

    @After("@UI")
    public void teardown(Scenario scenario) {
        logger.info("Running After Hook for the scenario:" + scenario.getName());
        closePlaywright();
    }

    private void initializePlaywright() {
        logger.info("Inicializando Playwright...");
        playwright = Playwright.create();
        browser = playwright.chromium().launch(new BrowserType.LaunchOptions().setHeadless(false));
        context = browser.newContext();
        page = context.newPage();
        ScreenshotListener.page = page;
    }

    private void closePlaywright() {
        logger.info("closing Playwright...");
        if (page != null) page.close();
        if (browser != null) browser.close();
        if (playwright != null) playwright.close();
    }

    private void navigateToBaseUrl() {
        String baseUrl = "https://conduit-realworld-example-app.fly.dev/";
        logger.info("navigate to {}", baseUrl);
        page.navigate(baseUrl);
    }

}