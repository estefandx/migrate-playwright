package com.epam.mentoring.taf.page;

import com.microsoft.playwright.Page;

public class HomePage  extends  BasePage{
    private final String userProfileImage = "//img[@alt='Tom Marvolo Riddle']";
    private final String userNameDisplay = "//ul[contains(@class,'navbar-nav')]/li[3]/div[1]";
    private  final String dropdownMenu = "//li[@class='nav-item dropdown']";
    private  final  String profile = "//a[contains(@href,'profile')]";

    public HomePage(Page page) {
        super(page);
    }

    // Métodos específicos de la página de inicio
    public boolean isUserProfileVisible() {
        try {
            page.waitForSelector(userProfileImage);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public String getActualUserName() {
        return page.textContent(userNameDisplay);
    }

    public void openUpdateProfile(){
        page.click(dropdownMenu);
        page.click(profile);
    }
}
