package com.epam.mentoring.taf.steps;

import com.epam.mentoring.taf.page.HomePage;
import com.epam.mentoring.taf.page.LoginPage;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.picocontainer.annotations.Inject;
import org.testng.Assert;


public class UserLoginUIStepDefinitions  {

    private static final Logger logger = LogManager.getLogger(UserLoginUIStepDefinitions.class);
    private final LoginPage loginPage;
    private final HomePage homePage;

    public UserLoginUIStepDefinitions() {
        PlaywrightHooks hooks = PlaywrightHooks.getInstance();
        this.loginPage = new LoginPage(hooks.page);
        this.homePage = new HomePage(hooks.page);
    }

    @Given("the user navigates to the login page")
    public void userNavigatesToLoginPage() {

        logger.info("Navigating to login page");
        loginPage.goToLoginPage();
    }

    @When("the user enters email {string} and password {string}")
    public void userEntersCredentials(String email, String password) {
        logger.info("Entering credentials: Email={} Password={}", email, password);
        loginPage.enterCredentials(email, password);
    }

    @When("clicks the login button")
    public void userClicksLoginButton() {
        logger.info("Clicking login button");
        loginPage.clickLoginButton();
    }

    @Then("the result should be {string}")
    public void verifyLoginResult(String expectedResult) {
        if (expectedResult.equals("success")) {

                    Assert.assertTrue(homePage.isUserProfileVisible(), "User profile should be visible");
        } else {
            loginPage.waitForErrorMessage();
            String actualErrorMessage = loginPage.getErrorMessage();
            Assert.assertEquals(expectedResult, actualErrorMessage, "Error messages do not match");
        }
    }
}
