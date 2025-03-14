package com.epam.mentoring.taf.steps;

import com.epam.mentoring.taf.page.HomePage;
import com.epam.mentoring.taf.page.ProfilePage;
import com.epam.mentoring.taf.page.SignUpPage;
import com.epam.mentoring.taf.page.YourSettingsPage;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.testng.Assert;



public class SearchByTagStepsDefinitions {
    private HomePage homePage;
    private String selectedTag;

    public SearchByTagStepsDefinitions() {
        PlaywrightHooks hooks = PlaywrightHooks.getInstance();
        this.homePage = new HomePage(hooks.page);

    }

    @Given("I open the application")
    public void iOpenTheApplication() {
        System.out.println("page open in the hook");
    }

    @When("I select a random tag")
    public void iSelectARandomTag() {
        selectedTag = homePage.selectRandomTag();
    }

    @Then("the selected tag should be displayed in the navigation bar")
    public void theSelectedTagShouldBeDisplayedInTheNavigationBar() {
        String activeTag = homePage.getActiveTag().trim();
        Assert.assertEquals(activeTag.replaceAll("[^a-zA-Z0-9\\s]", ""), selectedTag);
    }
}
