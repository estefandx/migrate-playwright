package com.epam.mentoring.taf.api;

import com.epam.mentoring.taf.utils.ConfigLoader;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;

import java.util.Map;

import static io.restassured.RestAssured.given;

public class BaseApi {
    public static RequestSpecification getRequestSpec() {
        return new RequestSpecBuilder().
                setBaseUri(ConfigLoader.getInstance().getBaseUriAPI()).
                setContentType(ContentType.JSON).
                build();
    }

    public static RequestSpecification getRequestSpec(String token) {
        return new RequestSpecBuilder().
                setBaseUri(ConfigLoader.getInstance().getBaseUriAPI()).
                addHeader("Authorization", "Bearer " +  token).
                setContentType(ContentType.JSON).
                build();
    }

    public static ResponseSpecification getResponseSpec() {
        return new ResponseSpecBuilder().
                expectContentType(ContentType.JSON).
                log(LogDetail.ALL).
                build();
    }

    public static Response post(String token,String path) {

        return given(getRequestSpec(token)).
                when().
                post(path).
                then().
                spec(getResponseSpec()).
                extract().response();
    }

    public static Response delete(String token,String path) {

        return given(getRequestSpec(token)).
                when().
                delete(path).
                then().
                spec(getResponseSpec()).
                extract().response();
    }

    public static Response post(String path, Object payLoad) {

        return given(getRequestSpec()).
                body(payLoad).
                when().
                post(path).
                then().
                spec(getResponseSpec()).
                extract().response();
    }

    public static Response get(String path) {

        return given(getRequestSpec()).
                when().
                get(path).
                then().
                spec(getResponseSpec()).
                extract().response();
    }

    public static Response get(String path, Map<String, String> queryParams) {
        return given(getRequestSpec())
                .queryParams(queryParams)
                .get(path)
                .then().
                spec(getResponseSpec()).
                extract().response();
    }
}
