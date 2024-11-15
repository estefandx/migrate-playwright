package com.epam.mentoring.taf;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;

import java.net.MalformedURLException;
import java.net.URL;
import java.time.Duration;

abstract public class AbstractTest {

    protected final static String baseUrl = "https://conduit-realworld-example-app.fly.dev/";
    protected final static String UI_URL = "https://conduit-realworld-example-app.fly.dev/";
    protected final static String API_URL = "https://conduit-realworld-example-app.fly.dev/";
    protected WebDriver driver;
    protected WebDriverWait wait;

    @BeforeMethod
    public void initialisation() throws MalformedURLException {
        String gridUrl = System.getProperty("grid.url");
        if (gridUrl == null) {
            WebDriverManager.chromedriver().setup();
            driver = new ChromeDriver();
        } else {
            ChromeOptions options = new ChromeOptions();
            driver = new RemoteWebDriver(new URL(gridUrl), options);
        }
        driver.get(baseUrl);
        wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    @AfterMethod
    public void terminate() {
        driver.quit();
    }

}
