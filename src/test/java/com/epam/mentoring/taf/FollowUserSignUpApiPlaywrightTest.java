package com.epam.mentoring.taf;

import com.epam.mentoring.taf.api.UserApiPlaywright;
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

public class FollowUserSignUpApiPlaywrightTest extends AbstractPlayrightTest {

    private final String username = "Test User";
    private final String email = "test_user@example.com";
    private final String password = "test_password";
    private final Utilities utilities = new Utilities();

    @Test
    public void apiVerification() {
        StringUtils stringUtils = new StringUtils();
        String[] userDetails = stringUtils.generateUniqueUserDetails("usertest", "emailtest@test");
        String username = userDetails[0];
        String email = userDetails[1];

        User user = new User(email, "password", username);
        UserRequest userRequest = new UserRequest(user);
        APIResponse response = UserApiPlaywright.createUser(userRequest);
        Assert.assertEquals(response.status(),201);
    }

    @Test
    public void apiAlreadyRegisteredVerification() throws JsonProcessingException {

        User user = new User(email, password, username);
        UserRequest userRequest = new UserRequest(user);
        APIResponse response = UserApiPlaywright.createUser(userRequest);


        String responseBody = response.text();
        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode jsonResponse = objectMapper.readTree(responseBody);
        String errorMessage = jsonResponse.path("errors").path("body").get(0).asText();

        Assert.assertEquals(response.status(),422);
        Assert.assertEquals(errorMessage, "Email already exists.. try logging in");
    }

    @Test
    public void apiWrongEmailVerification() throws JsonProcessingException {

        User user = new User("wrong_email", password, username);
        UserRequest userRequest = new UserRequest(user);
        APIResponse response = UserApiPlaywright.createUser(userRequest);

        String responseBody = response.text();
        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode jsonResponse = objectMapper.readTree(responseBody);
        String errorMessage = jsonResponse.path("errors").path("body").get(0).asText();

        Assert.assertEquals(response.status(),422);
        Assert.assertEquals(errorMessage, "Email already exists.. try logging in");

    }
}
