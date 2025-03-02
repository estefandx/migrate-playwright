package com.epam.mentoring.taf.steps;

import com.epam.mentoring.taf.api.UserApiPlaywright;
import com.epam.mentoring.taf.pojos.models.User;
import com.epam.mentoring.taf.pojos.models.request.UserRequest;
import com.epam.mentoring.taf.utils.StringUtils;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.microsoft.playwright.APIResponse;
import io.cucumber.java.en.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.Assert;

import java.io.IOException;

public class UserApiStepDefinitions {

    private static final Logger logger = LogManager.getLogger(UserApiStepDefinitions.class);

    private String email;
    private String password;
    private APIResponse response;

    private String username;


    @Given("the user has the email {string} and password {string}")
    public void theUserHasTheEmailAndPassword(String email, String password) {
        this.email = email;
        this.password = password;
    }

    @When("the user sends a login request")
    public void theUserSendsALoginRequest() {
        User user = new User(email, password);
        UserRequest userRequest = new UserRequest(user);
        response = UserApiPlaywright.login(userRequest);
    }

    @Then("the response status should be {int}")
    public void theResponseStatusShouldBe(int expectedStatus) throws JsonProcessingException {
        Assert.assertEquals(response.status(), expectedStatus);
    }

    @Then("the response should contain the email {string}")
    public void theResponseShouldContainTheEmail(String expectedEmail) throws IOException {
        String responseBody = response.text();
        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode jsonResponse = objectMapper.readTree(responseBody);
        String emailFromResponse = jsonResponse.path("user").path("email").asText();
        Assert.assertEquals(emailFromResponse, expectedEmail);
    }

    @Then("the response should contain the error message {string}")
    public void theResponseShouldContainTheErrorMessage(String expectedMessage) throws IOException {
        String responseBody = response.text();
        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode jsonResponse = objectMapper.readTree(responseBody);
        String errorMessage = jsonResponse.path("errors").path("body").get(0).asText();
        Assert.assertEquals(errorMessage, expectedMessage);
    }

    @Given("I generate unique user details")
    public void generateUniqueUserDetails() {
        StringUtils stringUtils = new StringUtils();
        String[] userDetails = stringUtils.generateUniqueUserDetails("usertest", "emailtest@test");
        this.username = userDetails[0];
        this.email = userDetails[1];
        this.password = "testPasword*";
    }

    @Given("a user with email {string} and username {string} is already registered")
    public void userAlreadyRegistered(String email, String username) {
        this.username = username;
        this.email = email;
        User user = new User(email, password, username);
        UserRequest userRequest = new UserRequest(user);
        UserApiPlaywright.createUser(userRequest);
    }

    @Given("a user with email {string} and username {string}")
    public void setUserDetails(String email, String username) {
        this.email = email;
        this.username = username;
    }

    @Given("a user with email {string}, username {string}, and password {string}")
    public void givenUser(String email, String username, String password) {
        this.email = email;
        this.username = username;
        this.password = password;
    }

    @When("I send a request to register the user")
    public void registerUser() {
        User user = new User(email, password, username);
        UserRequest userRequest = new UserRequest(user);
        this.response = UserApiPlaywright.createUser(userRequest);
    }



    @Then("the error message should be {string}")
    public void validateErrorMessage(String expectedErrorMessage) throws JsonProcessingException {
        String responseBody = response.text();
        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode jsonResponse = objectMapper.readTree(responseBody);
        String actualErrorMessage = jsonResponse.path("errors").path("body").get(0).asText();
        Assert.assertEquals(actualErrorMessage, expectedErrorMessage);
    }


}