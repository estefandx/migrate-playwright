package com.epam.mentoring.taf;

import com.epam.mentoring.taf.api.UserApiPlaywright;
import com.epam.mentoring.taf.pojos.models.User;
import com.epam.mentoring.taf.pojos.models.request.UserRequest;

import com.epam.mentoring.taf.utils.Utilities;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.microsoft.playwright.APIResponse;

import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;



import java.io.IOException;

import java.util.Iterator;


import static com.epam.mentoring.taf.CsvDataProvider.getFilteredUserData;

public class FollowUserSignUpApiPlaywrightTest extends AbstractPlayrightTest {


    private final Utilities utilities = new Utilities();


    @DataProvider(name = "alreadyRegisteredUserData")
    public Iterator<Object[]> getAlreadyRegisteredUserData() throws IOException {
        return getFilteredUserData("alreadyRegistered");
    }

    @DataProvider(name = "invalidEmailUserData")
    public Iterator<Object[]> getInvalidEmailUserData() throws IOException {
        return getFilteredUserData("invalidEmail");
    }




    @Test(dataProvider = "validUserData", dataProviderClass = CsvDataProvider.class)
    public void apiVerification(String username, String email, String password, int expectedStatus, String expectedMessage) {
        String[] newUser = utilities.generateUniqueUserDetails(username,email);
        User user = new User(newUser[0], password, newUser[1]);
        UserRequest userRequest = new UserRequest(user);


        APIResponse response = UserApiPlaywright.createUser(userRequest);

        Assert.assertEquals(response.status(), expectedStatus);
    }

    @Test(dataProvider = "alreadyRegisteredUserData")
    public void apiAlreadyRegisteredVerification(String username, String email, String password, int expectedStatus, String expectedMessage) throws JsonProcessingException {
        User user = new User(email, password, username);
        UserRequest userRequest = new UserRequest(user);
        APIResponse response = UserApiPlaywright.createUser(userRequest);

        String responseBody = response.text();
        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode jsonResponse = objectMapper.readTree(responseBody);

        String errorMessage = jsonResponse.path("errors").path("body").get(0).asText();
        Assert.assertEquals(response.status(), expectedStatus);
        Assert.assertEquals(errorMessage, expectedMessage);
    }

    @Test(dataProvider = "invalidEmailUserData")
    public void apiWrongEmailVerification(String username, String email, String password, int expectedStatus, String expectedMessage) throws JsonProcessingException {
        User user = new User(email, password, username);
        UserRequest userRequest = new UserRequest(user);
        APIResponse response = UserApiPlaywright.createUser(userRequest);

        String responseBody = response.text();
        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode jsonResponse = objectMapper.readTree(responseBody);
        String errorMessage = jsonResponse.path("errors").path("body").get(0).asText();

        Assert.assertEquals(response.status(), expectedStatus);
        Assert.assertEquals(errorMessage, expectedMessage);
    }
}
