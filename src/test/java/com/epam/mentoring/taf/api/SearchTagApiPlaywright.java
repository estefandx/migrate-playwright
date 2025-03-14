package com.epam.mentoring.taf.api;

import com.microsoft.playwright.APIResponse;

import java.util.Map;

public class SearchTagApiPlaywright extends  BaseApiPlaywright {

    private static final String SEARCH_ARTICLES = "/api/articles";

    public static APIResponse searchTag(String tagName) {
        return get(SEARCH_ARTICLES, Map.of("tag", tagName, "limit", "10", "offset", "0"));
    }


}
