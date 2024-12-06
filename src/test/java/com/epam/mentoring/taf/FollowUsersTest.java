package com.epam.mentoring.taf;

import com.epam.mentoring.taf.api.FollowApi;
import com.epam.mentoring.taf.pojos.FollowUser;
import com.epam.mentoring.taf.pojos.models.response.ProfileResponse;
import com.epam.mentoring.taf.pojos.models.response.articles.ArticleResponse;
import com.epam.mentoring.taf.utils.JsonReader;

import io.restassured.response.Response;

import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;



public class FollowUsersTest extends AbstractApiTest {

    @DataProvider(name = "validAuthors")
    public Object[][] getValidAuthors() throws IOException {
        List<FollowUser> authors = JsonReader.readAuthorsFromJson("src/test/resources/data/users.json");
        List<FollowUser> validAuthors = authors.stream()
                .filter(FollowUser::isValid)
                .collect(Collectors.toList());
        return validAuthors.stream()
                .map(author -> new Object[]{author})
                .toArray(Object[][]::new);
    }

    @DataProvider(name = "invalidAuthors")
    public Object[][] getInvalidAuthors() throws IOException {
        List<FollowUser> followUsers = JsonReader.readAuthorsFromJson("src/test/resources/data/users.json");
        List<FollowUser> invalidFollowUsers = followUsers.stream()
                .filter(followUser -> !followUser.isValid())
                .collect(Collectors.toList());
        return invalidFollowUsers.stream()
                .map(followUser -> new Object[]{followUser})
                .toArray(Object[][]::new);
    }



    @Test(dataProvider = "validAuthors")
    public void  followUserTest(FollowUser followUser){
        Response responseDetailsUser = FollowApi.userDetails(followUser.getName());
        ArticleResponse userDetails = responseDetailsUser.as(ArticleResponse.class);
        int previousFollowers = userDetails.getArticles().get(0).getAuthor().getFollowersCount();


        Response followResponse = FollowApi.followUser(followUser.getName(),token);
        ProfileResponse profileResponse = followResponse.as(ProfileResponse.class);
        int expectedFollowers = profileResponse.getProfile().getFollowersCount();


        Assert.assertEquals(followResponse.statusCode(),200);
        Assert.assertEquals(previousFollowers + 1,expectedFollowers);

    }


    @Test(dataProvider = "validAuthors")
    public void  unfollowUserTest(FollowUser followUser){
        Response responseDetailsUser = FollowApi.userDetails(followUser.getName());
        ArticleResponse userDetails = responseDetailsUser.as(ArticleResponse.class);
        int previousFollowers = userDetails.getArticles().get(0).getAuthor().getFollowersCount();


        Response followResponse = FollowApi.followUser(followUser.getName(),token);
        Response unFollowResponse = FollowApi.unFollowUser(followUser.getName(),token);
        ProfileResponse profileResponse = unFollowResponse.as(ProfileResponse.class);
        int expectedFollowers = profileResponse.getProfile().getFollowersCount();

        Assert.assertEquals(followResponse.statusCode(),200);
        Assert.assertEquals(unFollowResponse.statusCode(),200);
        Assert.assertEquals(previousFollowers,expectedFollowers);

    }


    @Test(dataProvider = "invalidAuthors")
    public void  followNotValidUserTest(FollowUser followUser){

        Response followResponse = FollowApi.followUser(followUser.getName(),token);
        String errorMessage = followResponse.path("errors.body[0]");
        Assert.assertEquals(followResponse.statusCode(),404);
        Assert.assertEquals(errorMessage,"User profile not found ");

    }


    @Test(dataProvider = "invalidAuthors")
    public void  unFollowNotValidUserTest(FollowUser followUser){

        Response followResponse = FollowApi.unFollowUser(followUser.getName(),token);
        String errorMessage = followResponse.path("errors.body[0]");
        Assert.assertEquals(followResponse.statusCode(),404);
        Assert.assertEquals(errorMessage,"User profile not found ");

    }






}
