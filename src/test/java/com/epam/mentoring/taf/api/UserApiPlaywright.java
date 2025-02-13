package com.epam.mentoring.taf.api;

import com.microsoft.playwright.APIResponse;
import io.restassured.response.Response;

public class UserApiPlaywright extends  BaseApiPlaywright{

    public static final String CREATE_USER = "/api/users";
    public static  final  String LOGIN_USER = "/api/users/login";

    public static APIResponse createUser(Object userData){
        return  post(CREATE_USER,userData);
    }

    public static APIResponse login(Object userData){
        return  post(LOGIN_USER,userData);
    }
}
