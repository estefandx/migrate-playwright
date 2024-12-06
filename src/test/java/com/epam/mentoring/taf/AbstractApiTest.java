package com.epam.mentoring.taf;

import com.epam.mentoring.taf.api.UserApi;
import com.epam.mentoring.taf.pojos.models.User;
import com.epam.mentoring.taf.pojos.models.request.UserRequest;
import com.epam.mentoring.taf.utils.StringUtils;
import io.restassured.response.Response;
import org.testng.annotations.BeforeMethod;

public class AbstractApiTest {


    protected String  token;




    @BeforeMethod
    public void setup(){
        StringUtils stringUtils = new StringUtils();
        String[] userDetails = stringUtils.generateUniqueUserDetails("usertest", "emailtest@test");
        String username = userDetails[0];
        String email = userDetails[1];
        User user = new User(email, "password", username);
        UserRequest userRequest = new UserRequest(user);

        Response response = UserApi.createUser(userRequest);
        token = response.jsonPath().getString("user.token");

    }
}
