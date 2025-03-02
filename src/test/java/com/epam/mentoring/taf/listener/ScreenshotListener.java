package com.epam.mentoring.taf.listener;


import com.microsoft.playwright.Page;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.ITestListener;
import org.testng.ITestResult;
import java.nio.file.Paths;

import com.microsoft.playwright.Page;
import org.testng.ITestListener;
import org.testng.ITestResult;

import java.nio.file.Paths;

public class ScreenshotListener implements ITestListener {

    public static Page page;
    protected static final Logger logger = LogManager.getLogger(ScreenshotListener.class);

    @Override
    public void onTestFailure(ITestResult result) {

        if (page != null) {
            String screenshotPath = "screenshots/" + result.getName() + ".png";
            try {
                page.screenshot(new Page.ScreenshotOptions().setPath(Paths.get(screenshotPath)).setFullPage(true));
                logger.info("Screenshot saved {}",screenshotPath);
            } catch (Exception e) {
                logger.info("erro saved the screenshot {}",e.getMessage());
            }
        } else {
            logger.warn("screenshot failed because the object page is null");
        }
    }
}
