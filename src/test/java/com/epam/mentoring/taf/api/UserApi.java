package com.epam.mentoring.taf.api;

import io.restassured.response.Response;


public class UserApi extends  BaseApi{

    public static final String CREATE_USER = "/api/users";
    public static  final  String LOGIN_USER = "/api/users/login";


     public static Response createUser(Object userData){
        return  post(CREATE_USER,userData);
     }

    public static Response login(Object userData){
        return  post(LOGIN_USER,userData);
    }







}
