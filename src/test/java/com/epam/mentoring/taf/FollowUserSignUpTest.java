package com.epam.mentoring.taf;

import com.epam.mentoring.taf.api.UserApi;
import com.epam.mentoring.taf.pojos.models.User;
import com.epam.mentoring.taf.pojos.models.request.UserRequest;
import com.epam.mentoring.taf.utils.StringUtils;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.Assert;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasItem;

public class FollowUserSignUpTest extends AbstractTest {
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
        StringUtils stringUtils = new StringUtils();
        String[] userDetails = stringUtils.generateUniqueUserDetails("usertest", "emailtest@test");
        String username = userDetails[0];
        String email = userDetails[1];

        User user = new User(email, "password", username);
        UserRequest userRequest = new UserRequest(user);
        Response response = UserApi.createUser(userRequest);
        Assert.assertEquals(response.statusCode(),201);
    }

    @Test
    public void apiAlreadyRegisteredVerification() {

        User user = new User(email, password, username);
        UserRequest userRequest = new UserRequest(user);
        Response response = UserApi.createUser(userRequest);

        Assert.assertEquals(response.statusCode(),422);
        Assert.assertEquals(response.body().path("errors.body[0]"),"Email already exists.. try logging in");
    }

    @Test
    public void apiWrongEmailVerification() {

        User user = new User("wrong_email", password, username);
        UserRequest userRequest = new UserRequest(user);
        Response response = UserApi.createUser(userRequest);
        Assert.assertEquals(response.statusCode(),422);
        Assert.assertEquals(response.body().path("errors.body[0]"),"Email already exists.. try logging in");

    }

}
