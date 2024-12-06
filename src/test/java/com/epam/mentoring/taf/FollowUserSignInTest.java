package com.epam.mentoring.taf;

import com.epam.mentoring.taf.api.UserApi;
import com.epam.mentoring.taf.pojos.models.User;
import com.epam.mentoring.taf.pojos.models.request.UserRequest;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.Assert;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

public class FollowUserSignInTest extends AbstractTest {
    private final String email = "tom_marvolo@example.com";
    private final String password = "Voldemort";
    private final String loginMenu = "//li/a[text()='Login']";
    private  final String emailInput = "//input[@placeholder='Email']";
    private final String passwordInput = "//input[@placeholder='Password']";
    private  final String loginButton = "//button[contains(text(),'Login')]";

    @Test
    public void uiVerification() {
        driver.findElement(By.xpath(loginMenu)).click();
        driver.findElement(By.xpath(emailInput)).sendKeys(email);
        driver.findElement(By.xpath(passwordInput)).sendKeys(password);
        driver.findElement(By.xpath(loginButton)).click();

        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//img[@alt='Tom Marvolo Riddle']")));
        String actualUserName = driver.findElement(By.xpath("//ul[contains(@class,'navbar-nav')]/li[3]/div[1]")).getText();
        String username = "Tom Marvolo Riddle";
        Assert.assertEquals(actualUserName, username);
    }

    @Test
    public void uiNegativeVerification() {
        driver.findElement(By.xpath(loginMenu)).click();
        driver.findElement(By.xpath(emailInput)).sendKeys(email);
        driver.findElement(By.xpath(passwordInput)).sendKeys("fdsf23");
        driver.findElement(By.xpath(loginButton)).click();

        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//ul[@class='error-messages']/li")));
        String actualUserName = driver.findElement(By.xpath("//ul[@class='error-messages']/li")).getText();
        Assert.assertEquals(actualUserName, "Wrong email/password combination");
    }

    @Test
    public void apiVerification() {

        User user = new User(email, password);
        UserRequest userRequest = new UserRequest(user);
        Response response =  UserApi.login(userRequest);

        Assert.assertEquals(response.statusCode(),200);
        Assert.assertEquals(response.path("user.email"),email);

    }

    @Test
    public void apiNegativeVerification() {

        User user = new User(email, "wrong_password");
        UserRequest userRequest = new UserRequest(user);
        Response response =  UserApi.login(userRequest);
        Assert.assertEquals(response.statusCode(),422);
        Assert.assertEquals(response.path("errors.body[0]"),"Wrong email/password combination");
    }

}
