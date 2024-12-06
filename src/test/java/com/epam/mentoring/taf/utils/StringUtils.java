package com.epam.mentoring.taf.utils;

import java.util.Random;

public class StringUtils {

    private static final String CHARACTERS = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";


    public String generateRandomString(int length) {
        StringBuilder randomString = new StringBuilder();
        Random random = new Random();

        for (int i = 0; i < length; i++) {
            int index = random.nextInt(CHARACTERS.length());
            randomString.append(CHARACTERS.charAt(index));
        }

        return randomString.toString();
    }

    public String[] generateUniqueUserDetails(String baseUsername, String baseEmail) {
        StringUtils stringUtils = new StringUtils();
        int uniqueId = (int) (Math.random() * 1000);
        String randomString = stringUtils.generateRandomString(5);
        String username = baseUsername + uniqueId + randomString;
        String email = baseEmail.replace("@", "." + uniqueId + randomString + "@");
        return new String[]{username, email};
    }
}
