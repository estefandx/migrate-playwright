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

    private final String email = "tom_marvolo@example.com";
    private final String password = "Voldemort";

    @Test
    public void apiVerification() throws JsonProcessingException {
        User user = new User(email, password);
        UserRequest userRequest = new UserRequest(user);
        APIResponse response = UserApiPlaywright.login(userRequest);

        Assert.assertEquals(response.status(), 200);
        String responseBody = response.text();

        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode jsonResponse = objectMapper.readTree(responseBody);

        String emailFromResponse = jsonResponse.path("user").path("email").asText();
        Assert.assertEquals(emailFromResponse, email);

    }

    @Test
    public void apiNegativeVerification() throws JsonProcessingException {

        User user = new User(email, "wrong_password");
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
