package com.epam.mentoring.taf;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.Assert;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasItem;

public class SearchingByTagTest extends AbstractTest {

    @Test
    public void uiVerification() {
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//button[contains(@class, 'tag-pill') and contains(@class, 'tag-default')]")));


        int randomTag = (int) (Math.random() * 25);
        WebElement tag = driver.findElement(By.xpath("//button[contains(@class,'tag-pill')][" + randomTag + "]"));
        String tagName =   tag.getText();
        tag.click();

        String selectedTag = driver.findElement(By.xpath("//button[@class='nav-link active']")).getText();
        Assert.assertEquals(selectedTag.replaceAll("[^a-zA-Z0-9\\s]", ""), tagName);
    }

    @Test
    public void apiVerification1() {
        String tag = "test";
        given()
                .baseUri(API_URL)
                .when().
                get("/api/articles?tag={tag}&limit=10&offset=0", tag)
                .then().statusCode(200)
                .body("articles.tagList.flatten()", hasItem("test"));
    }

    @Test
    public void apiVerification2() {
        String tag = "simple";
        given()
                .baseUri(API_URL)
                .when()
                .get("/api/articles?tag={tag}&limit=10&offset=0", tag)
                .then().statusCode(200)
                .body("articles.tagList.flatten()", hasItem("simple"));
    }

    @Test
    public void apiVerification3() {
        String tag = "cypress";
        given()
                .baseUri(API_URL)
                .when()
                .get("/api/articles?tag={tag}&limit=10&offset=0", tag)
                .then()
                .statusCode(200).body("articles.tagList.flatten()", hasItem("cypress"));
    }

    @Test
    public void apiNegativeVerification() {
        String tag = "invalid_tag_name";
        given()
                .baseUri(API_URL)
                .when()
                .get("/api/articles?tag={tag}&limit=10&offset=0", tag)
                .then().statusCode(200)
                .body("articlesCount", equalTo(0));
    }

}
