package com.epam.mentoring.taf;


import com.epam.mentoring.taf.page.HomePage;
import com.epam.mentoring.taf.page.LoginPage;

import org.testng.Assert;
import org.testng.annotations.Test;

public class FollowUserSignInPlayrightTest extends AbstractPlayrightTest {

    @Test(dataProvider = "validUserData", dataProviderClass = CsvDataProvider.class,groups = "UI")
    public void uiVerification(String username, String email, String password, int expectedStatus, String expectedMessage) {
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

    @Test(dataProvider = "invalidPassword", dataProviderClass = CsvDataProvider.class,groups = "UI")
    public void uiNegativeVerification(String username, String email, String password, int expectedStatus, String expectedMessage) {
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






