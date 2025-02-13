package com.epam.mentoring.taf;

import com.epam.mentoring.taf.api.UserApiPlaywright;
import com.epam.mentoring.taf.pojos.models.User;
import com.epam.mentoring.taf.pojos.models.request.UserRequest;
import com.epam.mentoring.taf.utils.StringUtils;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.microsoft.playwright.APIResponse;
import org.testng.annotations.BeforeMethod;

public class AbstracApiTestPlaywright {

    protected String  token;

    @BeforeMethod
    public void setup() throws JsonProcessingException {
        StringUtils stringUtils = new StringUtils();
        String[] userDetails = stringUtils.generateUniqueUserDetails("usertest", "emailtest@test");
        String username = userDetails[0];
        String email = userDetails[1];
        User user = new User(email, "password", username);
        UserRequest userRequest = new UserRequest(user);

        APIResponse response = UserApiPlaywright.createUser(userRequest);
        String responseBody = response.text();

        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode jsonResponse = objectMapper.readTree(responseBody);

        token = jsonResponse.path("user").path("token").asText();

    }
}
