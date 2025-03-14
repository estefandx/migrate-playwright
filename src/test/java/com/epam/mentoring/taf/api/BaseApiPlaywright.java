package com.epam.mentoring.taf.api;

import com.epam.mentoring.taf.utils.ConfigLoader;
import com.microsoft.playwright.APIRequest;
import com.microsoft.playwright.APIRequestContext;
import com.microsoft.playwright.APIResponse;
import com.microsoft.playwright.Playwright;
import com.microsoft.playwright.options.HttpHeader;
import com.microsoft.playwright.options.RequestOptions;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.specification.RequestSpecification;
import org.testng.annotations.BeforeClass;

import java.util.List;
import java.util.Map;

public class BaseApiPlaywright {

    private static APIRequestContext request;
    private static Playwright playwright;

    private static void initializeRequest() {
        if (request == null) {
            playwright = Playwright.create();
            request = playwright.request().newContext(new APIRequest.NewContextOptions()
                    .setBaseURL(ConfigLoader.getInstance().getBaseUriAPI()));
        }
    }

    public static APIResponse post(String token, String path) {
        initializeRequest();
        APIRequestContext context = playwright.request().newContext(new APIRequest.NewContextOptions()
                .setBaseURL(ConfigLoader.getInstance().getBaseUriAPI())
                .setExtraHTTPHeaders(Map.of("Authorization", "Bearer " + token)));
        return context.post(path);
    }

    public static APIResponse delete(String token, String path) {
        initializeRequest();
        APIRequestContext context = playwright.request().newContext(new APIRequest.NewContextOptions()
                .setBaseURL(ConfigLoader.getInstance().getBaseUriAPI())
                .setExtraHTTPHeaders(Map.of("Authorization", "Bearer " + token)));
        return context.delete(path);
    }

    public static APIResponse post(String path, Object payload) {
        initializeRequest();
        return request.post(path, RequestOptions.create().setData(payload));
    }

    public static APIResponse get(String path) {
        initializeRequest();
        return request.get(path);
    }

    public static APIResponse get(String path, Map<String, String> queryParams) {
        initializeRequest();
        RequestOptions options = RequestOptions.create();
        for (Map.Entry<String, String> entry : queryParams.entrySet()) {
            options.setQueryParam(entry.getKey(), entry.getValue());
        }
        return request.get(path, options);
    }




    }



