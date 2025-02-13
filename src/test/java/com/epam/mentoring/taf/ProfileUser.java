package com.epam.mentoring.taf;

import com.epam.mentoring.taf.api.UserApiPlaywright;
import com.epam.mentoring.taf.page.*;
import com.epam.mentoring.taf.pojos.models.User;
import com.epam.mentoring.taf.pojos.models.request.UserRequest;
import com.epam.mentoring.taf.utils.StringUtils;
import com.epam.mentoring.taf.utils.Utilities;
import com.microsoft.playwright.APIResponse;
import org.testng.Assert;
import org.testng.annotations.Test;

public class ProfileUser  extends  AbstractTestPlayright{

    private final String username = "Test User";
    private final String email = "test_user@example.com";
    private final String password = "test_password";
    private final Utilities utilities = new Utilities();

    @Test
    public void UpdateProfileUser() throws InterruptedException {
        String[] userDetails = utilities.generateUniqueUserDetails(this.username, this.email);
        String username = userDetails[0];
        String email = userDetails[1];


        SignUpPage signUpPage = new SignUpPage(page);
        HomePage homePage = new HomePage(page);
        ProfilePage profilePage = new ProfilePage(page);
        YourSettingsPage yourSettingsPage = new YourSettingsPage(page);
        signUpPage.navigateToSignUp();
        signUpPage.fillSignUpForm(username, email, password);
        signUpPage.submitSignUpForm();
        signUpPage.waitForUserProfileImage(username);

        String actualUserName = signUpPage.getUserProfileName();
        Assert.assertEquals(actualUserName, username);

        homePage.openUpdateProfile();
        profilePage.editProfile();
        yourSettingsPage.fillBio("new bio");
        yourSettingsPage.updateSettings();
        String actualBio = yourSettingsPage.getTextBio();
        Assert.assertEquals(actualBio,"new bio");
    }
}
