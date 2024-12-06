package com.epam.mentoring.taf.api;

import io.restassured.response.Response;

public class FollowApi extends  BaseApi{

    public  static  final  String FOLLOW_USER = "/api/profiles/%s/follow";
    public static  final  String  USER_DETAILS = "/api/articles?author=%s";



    public static Response followUser(String user, String token){
        String path = String.format(FOLLOW_USER, user);
          return  post(token,path);
    }

    public  static  Response userDetails(String user){
        String path = String.format(USER_DETAILS,user);
        return  get(path);
    }

    public static Response unFollowUser(String user, String token){
        String path = String.format(FOLLOW_USER, user);
        return  delete(token,path);
    }



}
