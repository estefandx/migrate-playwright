package com.epam.mentoring.taf.page;

import com.microsoft.playwright.Page;

public class SignUpPage extends BasePage {

    private final String signUpLink = "//li/a[text()='Sign up']";
    private final String nameInput = "//input[@placeholder='Your Name']";
    private final String emailInput = "//input[@placeholder='Email']";
    private final String passwordInput = "//input[@placeholder='Password']";
    private final String signUpButton = "//button[text()='Sign up']";
    private final String userProfileImage = "//img[@alt='%s']"; // Se usa el username en el selector

    public SignUpPage(Page page) {
        super(page);
    }

    public void navigateToSignUp() {
        page.click(signUpLink);
    }

    public void fillSignUpForm(String username, String email, String password) {
        page.fill(nameInput, username);
        page.fill(emailInput, email);
        page.fill(passwordInput, password);
    }

    public void submitSignUpForm() {
        page.click(signUpButton);
    }

    public void waitForUserProfileImage(String username) {
        page.waitForSelector(String.format(userProfileImage, username));
    }

    public String getUserProfileName() {
        return page.textContent("//ul[contains(@class,'navbar-nav')]/li[3]/div[1]");
    }
}

