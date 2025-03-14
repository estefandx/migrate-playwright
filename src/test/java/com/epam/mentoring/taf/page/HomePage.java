package com.epam.mentoring.taf.page;

import com.microsoft.playwright.ElementHandle;
import com.microsoft.playwright.Page;

import java.util.List;
import java.util.Random;

public class HomePage  extends  BasePage{
    private final String userProfileImage = "//img[@alt='Tom Marvolo Riddle']";
    private final String userNameDisplay = "//ul[contains(@class,'navbar-nav')]/li[3]/div[1]";
    private  final String dropdownMenu = "//li[@class='nav-item dropdown']";
    private  final  String profile = "//a[contains(@href,'profile')]";
    private final String tagButton = "//button[contains(@class, 'tag-pill')]";
    private final String activeTag = "//button[@class='nav-link active']";

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

    public void waitForTagsToLoad() {
        page.waitForSelector(tagButton);
    }

    public List<ElementHandle> getAvailableTags() {
        return page.querySelectorAll(tagButton);
    }

    public String selectRandomTag() {
        waitForTagsToLoad();
        List<ElementHandle> tags = getAvailableTags();
        int randomIndex = new Random().nextInt(tags.size());
        ElementHandle randomTag = tags.get(randomIndex);
        String tagName = randomTag.innerText();
        randomTag.click();
        return tagName;
    }

    public String getActiveTag() {
        return page.textContent(activeTag);
    }
}
