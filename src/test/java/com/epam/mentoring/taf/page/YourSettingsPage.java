package com.epam.mentoring.taf.page;

import com.microsoft.playwright.Page;

public class YourSettingsPage extends BasePage{

    String bioProfile = "//textarea[@name='bio']";
    String updateSettings = "//button[text()='Update Settings']";


    public YourSettingsPage(Page page) {
        super(page);
    }

    public void fillBio(String bio){
        page.fill(bioProfile,bio);
    }

    public String getTextBio(){
        return page.textContent(bioProfile);
    }

    public void updateSettings(){
        page.click(updateSettings);
    }


}
