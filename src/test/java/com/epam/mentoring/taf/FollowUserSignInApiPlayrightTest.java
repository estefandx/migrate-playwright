package com.epam.mentoring.taf;

import com.epam.mentoring.taf.api.UserApiPlaywright;
import com.epam.mentoring.taf.pojos.models.User;
import com.epam.mentoring.taf.pojos.models.request.UserRequest;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.microsoft.playwright.APIResponse;
import org.testng.Assert;
import org.testng.annotations.Test;

public class FollowUserSignInApiPlayrightTest extends AbstractPlayrightTest {


    @Test(dataProvider = "validUserData", dataProviderClass = CsvDataProvider.class)
    public void apiVerification(String username, String email, String password, int expectedStatus, String expectedMessage) throws JsonProcessingException {

        User user = new User(email, password);
        UserRequest userRequest = new UserRequest(user);
        APIResponse response = UserApiPlaywright.login(userRequest);

        Assert.assertEquals(response.status(), expectedStatus);
        String responseBody = response.text();

        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode jsonResponse = objectMapper.readTree(responseBody);

        String emailFromResponse = jsonResponse.path("user").path("email").asText();
        Assert.assertEquals(emailFromResponse, email);

    }

    @Test(dataProvider = "invalidPassword", dataProviderClass = CsvDataProvider.class)
    public void apiNegativeVerification(String username, String email, String password, int expectedStatus, String expectedMessage) throws JsonProcessingException {

        User user = new User(email, expectedMessage);
        UserRequest userRequest = new UserRequest(user);
        APIResponse response = UserApiPlaywright.login(userRequest);
        Assert.assertEquals(response.status(), 422);

        String responseBody = response.text();

        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode jsonResponse = objectMapper.readTree(responseBody);
        String errorMessage = jsonResponse.path("errors").path("body").get(0).asText();
        Assert.assertEquals(errorMessage, "Wrong email/password combination");


    }
}
