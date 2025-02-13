package com.epam.mentoring.taf.page;

import com.microsoft.playwright.Page;

public class ProfilePage extends BasePage{

    private final String editProfile = "//div[@class='profile-page']//a[contains(@href,'settings')]";

    public ProfilePage(Page page) {
        super(page);
    }

    public void editProfile(){
        page.click(editProfile);
    }


}
