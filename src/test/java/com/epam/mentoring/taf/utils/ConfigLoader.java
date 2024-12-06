package com.epam.mentoring.taf.utils;

import java.util.Properties;

public class ConfigLoader {

    private static final String BASE_URI_API = "base_uri_api";

    private static final String CONFIG_PROPERTIES = "config.properties";

    private static final String RESOURCES_PATH = System.getProperty("user.dir") + "/src/test/resources/";
    private final Properties properties;
    private static ConfigLoader configLoader;

    private ConfigLoader() {
        properties = getConfigPropertyFile();

    }

    private Properties getConfigPropertyFile() {
        return PropertyUtils.propertyLoader(RESOURCES_PATH + ConfigLoader.CONFIG_PROPERTIES);
    }

    private String getPropertyValue() {
        String prop = properties.getProperty(ConfigLoader.BASE_URI_API);
        if (prop != null) {
            return prop.trim();
        } else {
            throw new RuntimeException("Property " + ConfigLoader.BASE_URI_API + " is not specified in the config.config.properties file");
        }
    }

    public static ConfigLoader getInstance() {
        if (configLoader == null) {
            configLoader = new ConfigLoader();
        }
        return configLoader;
    }

    public String getBaseUriAPI() {
        return getPropertyValue();
    }



}