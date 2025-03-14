package com.epam.mentoring.taf.steps;

import com.epam.mentoring.taf.api.SearchTagApiPlaywright;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.microsoft.playwright.APIResponse;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.testng.Assert;

import java.io.IOException;

public class SearchByTagApiStepDefinitions {

    private APIResponse response;

    @When("the user sends a request to search articles with tag {string}")
    public void theUserSendsARequestToSearchArticlesWithTag(String tagName) {
        response = SearchTagApiPlaywright.searchTag(tagName);
    }


    @Then("the response should contain articles related to tag {string}")
    public void theResponseShouldContainArticlesRelatedToTag(String tagName) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode jsonResponse = objectMapper.readTree(response.text());
        Assert.assertTrue(jsonResponse.get("articles").toString().contains(tagName),
                "the tag " + tagName + "not found");
    }

    @Then("the response should contain zero articles")
    public void theResponseShouldContainZeroArticles() throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode jsonResponse = objectMapper.readTree(response.text());
        Assert.assertEquals(jsonResponse.get("articlesCount").asInt(), 0,
                "expect 0 record with a invalid tag ");
    }
}
