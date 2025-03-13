package com.epam.mentoring.taf.page;

import com.microsoft.playwright.Page;

public class LoginPage  extends  BasePage{
    private final String loginMenu = "//li/a[text()='Login']";
    private final String emailInput = "//input[@placeholder='Email']";
    private final String passwordInput = "//input[@placeholder='Password']";
    private final String loginButton = "//button[contains(text(),'Login')]";
    private final String errorMessageSelector = "//ul[@class='error-messages']/li";

    public LoginPage(Page page) {
        super(page);
    }


    public void goToLoginPage() {
        page.click(loginMenu);
    }

    public void enterCredentials(String email, String password) {
        page.fill(emailInput, email);
        page.fill(passwordInput, password);
    }

    public void clickLoginButton() {
        page.click(loginButton);
    }

    public String getErrorMessage() {
        return page.textContent(errorMessageSelector);
    }

    public void waitForErrorMessage() {
        page.waitForSelector(errorMessageSelector);
    }
}
