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
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class FollowUserSignUpPlaywrightTest extends AbstractPlayrightTest {


    private final Utilities utilities = new Utilities();

    @Test(dataProvider = "validUserData", dataProviderClass = CsvDataProvider.class,groups = "UI")
    public void uiVerification(String username, String email, String password, int expectedStatus, String expectedMessage) {
       logger.info("start scenario Sing up successfully ");

        String[] userDetails = utilities.generateUniqueUserDetails(username, email);
        String usernameNew = userDetails[0];
        String emailNew = userDetails[1];


        SignUpPage signUpPage = new SignUpPage(page);
        signUpPage.navigateToSignUp();
        signUpPage.fillSignUpForm(usernameNew, emailNew, password);
        signUpPage.submitSignUpForm();
        signUpPage.waitForUserProfileImage(usernameNew);
        String actualUserName = signUpPage.getUserProfileName();
        Assert.assertEquals(actualUserName, usernameNew);
    }




}
