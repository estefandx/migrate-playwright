package com.epam.mentoring.taf;

import com.epam.mentoring.taf.api.UserApiPlaywright;
import com.epam.mentoring.taf.page.SignUpPage;
import com.epam.mentoring.taf.pojos.models.User;
import com.epam.mentoring.taf.pojos.models.request.UserRequest;
import com.epam.mentoring.taf.utils.StringUtils;
import com.epam.mentoring.taf.utils.Utilities;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.microsoft.playwright.APIResponse;
import org.testng.Assert;
import org.testng.annotations.Test;

public class FollowUserSignUpPlaywrightTest extends AbstractPlayrightTest {

    private final String username = "Test User";
    private final String email = "test_user@example.com";
    private final String password = "test_password";
    private final Utilities utilities = new Utilities();



    @Test(groups = "UI")
    public void uiVerification() {
       logger.info("start scenario Sing up successfully ");

        String[] userDetails = utilities.generateUniqueUserDetails(this.username, this.email);
        String username = userDetails[0];
        String email = userDetails[1];


        SignUpPage signUpPage = new SignUpPage(page);
        signUpPage.navigateToSignUp();
        signUpPage.fillSignUpForm(username, email, password);
        signUpPage.submitSignUpForm();
        signUpPage.waitForUserProfileImage(username);
        String actualUserName = signUpPage.getUserProfileName();
        Assert.assertEquals(actualUserName, username);
    }




}
