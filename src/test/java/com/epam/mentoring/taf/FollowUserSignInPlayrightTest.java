package com.epam.mentoring.taf;

import com.epam.mentoring.taf.api.UserApiPlaywright;
import com.epam.mentoring.taf.page.HomePage;
import com.epam.mentoring.taf.page.LoginPage;
import com.epam.mentoring.taf.pojos.models.User;
import com.epam.mentoring.taf.pojos.models.request.UserRequest;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.microsoft.playwright.APIResponse;
import org.testng.Assert;
import org.testng.annotations.Test;

public class FollowUserSignInPlayrightTest extends AbstractPlayrightTest {

    private final String email = "tom_marvolo@example.com";
    private final String password = "Voldemort";

    @Test(groups = "UI")
    public void uiVerification() {
        logger.info("start scenario login successfully");
        LoginPage loginPage = new LoginPage(page);
        loginPage.goToLoginPage();

        loginPage.enterCredentials(email, password);
        loginPage.clickLoginButton();

        HomePage homePage = new HomePage(page);
        Assert.assertTrue(homePage.isUserProfileVisible());

        String expectedUserName = "Tom Marvolo Riddle";
        String actualUserName = homePage.getActualUserName();
        Assert.assertEquals(actualUserName, expectedUserName);
    }

    @Test(groups = "UI")
    public void uiNegativeVerification() {
        logger.info("start invalid password");

        LoginPage loginPage = new LoginPage(page);
        loginPage.goToLoginPage();
        loginPage.enterCredentials(email, "wrongPassword");
        loginPage.clickLoginButton();

        loginPage.waitForErrorMessage();
        String actualErrorMessage = loginPage.getErrorMessage();
        String expectedErrorMessage = "Wrong email/password combination";
        Assert.assertEquals(actualErrorMessage, expectedErrorMessage);
    }


}






