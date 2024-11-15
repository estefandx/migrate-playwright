package com.epam.mentoring.taf;

import io.restassured.http.ContentType;
import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.Assert;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasItem;

public class UserSignUpTest extends AbstractTest {
    private final String username = "Test User";
    private final String email = "test_user@example.com";
    private final String password = "test_password";


    private String[] generateUniqueUserDetails(String baseUsername, String baseEmail) {
        int uniqueId = (int) (Math.random() * 100);
        String username = baseUsername + uniqueId;
        String email = baseEmail.replace("@", "." + uniqueId + "@");
        return new String[]{username, email};
    }

    @Test
    public void uiVerification() {
        String[] userDetails = generateUniqueUserDetails(this.username, this.email);
        String username = userDetails[0];
        String email = userDetails[1];

        driver.get(UI_URL);
        driver.findElement(By.xpath("//li/a[text()='Sign up']")).click();
        driver.findElement(By.xpath("//input[@placeholder='Your Name']")).sendKeys(username);
        driver.findElement(By.xpath("//input[@placeholder='Email']")).sendKeys(email);
        driver.findElement(By.xpath("//input[@placeholder='Password']")).sendKeys(password);
        driver.findElement(By.xpath("//button[text()='Sign up']")).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//img[@alt='" + username + "']")));

        String actualUserName = driver.findElement(By.xpath("//ul[contains(@class,'navbar-nav')]/li[3]/div[1]")).getText();
        Assert.assertEquals(actualUserName, username);
    }

    @Test
    public void apiVerification() {
        String[] userDetails = generateUniqueUserDetails(this.username, this.email);
        String username = userDetails[0];
        String email = userDetails[1];

        given().baseUri(API_URL)
                .when().contentType(ContentType.JSON)
                .body(String.format("{\"user\":{\"email\":\"%s\",\"password\":\"%s\",\"username\":\"%s\"}}", email, password, username))
                .post("/api/users")
                .then().statusCode(201);
    }

    @Test
    public void apiAlreadyRegisteredVerification() {
        given()
                .baseUri(API_URL)
                .when()
                .contentType(ContentType.JSON)
                .body(String.format("{\"user\":{\"email\":\"%s\",\"password\":\"%s\",\"username\":\"%s\"}}", email, password, username))
                .post("/api/users").then().statusCode(422)
                 .body("errors.body[0]", equalTo("Email already exists.. try logging in"));

    }

    @Test
    public void apiWrongEmailVerification() {
        given()
                .baseUri(API_URL)
                .when()
                .contentType(ContentType.JSON)
                .body(String.format("{\"user\":{\"email\":\"%s\",\"password\":\"%s\",\"username\":\"%s\"}}", "wrong_email", password, username))
                .post("/api/users").then()
                .statusCode(422)
                .body("errors.body[0]", equalTo("Email already exists.. try logging in"));
    }

}
