package com.epam.mentoring.taf.steps;


import com.epam.mentoring.taf.page.*;
import com.epam.mentoring.taf.utils.Utilities;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.When;
import io.cucumber.java.en.Then;
import org.testng.Assert;

public class ProfileUserStepDefinitions {


    private final Utilities utilities = new Utilities();
    private SignUpPage signUpPage;
    private HomePage homePage;
    private ProfilePage profilePage;
    private YourSettingsPage yourSettingsPage;
    private String username;
    private String email;
    private final String password = "test_password";

    public ProfileUserStepDefinitions() {
        PlaywrightHooks hooks = PlaywrightHooks.getInstance();
        this.signUpPage = new SignUpPage(hooks.page);
        this.homePage = new HomePage(hooks.page);
        this.profilePage = new ProfilePage(hooks.page);
        this.yourSettingsPage = new YourSettingsPage(hooks.page);
    }

    @Given("I am a registered user with username {string}, email {string}, and password {string}")
    public void iAmARegisteredUser(String username, String email, String password) {
        this.username = username;
        this.email = email;

        String[] userDetails = utilities.generateUniqueUserDetails(username, email);
        this.username = userDetails[0];
        this.email = userDetails[1];

        signUpPage.navigateToSignUp();
        signUpPage.fillSignUpForm(this.username, this.email, password);
        signUpPage.submitSignUpForm();
        signUpPage.waitForUserProfileImage(this.username);

        String actualUserName = signUpPage.getUserProfileName();
        Assert.assertEquals(actualUserName, this.username);
    }

    @When("I log in and update my profile bio to {string}")
    public void iUpdateMyProfileBio(String newBio) {


        homePage.openUpdateProfile();
        profilePage.editProfile();
        yourSettingsPage.fillBio(newBio);
        yourSettingsPage.updateSettings();
    }

    @Then("my profile bio should be updated to {string}")
    public void myProfileBioShouldBeUpdated(String expectedBio) {
        String actualBio = yourSettingsPage.getTextBio();
        Assert.assertEquals(actualBio, expectedBio);
    }
}
