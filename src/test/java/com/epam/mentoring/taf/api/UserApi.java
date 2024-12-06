package com.epam.mentoring.taf.api;

import io.restassured.response.Response;


public class UserApi extends  BaseApi{

    public static final String CREATE_USER = "/api/users";


     public static Response createUser(Object userData){
        return  post(CREATE_USER,userData);
     }





}
