package com.epam.mentoring.taf.utils;

public class Utilities {
    public Utilities() {
    }

    public String[] generateUniqueUserDetails(String baseUsername, String baseEmail) {
        int uniqueId = (int) (Math.random() * 10000);
        String username = baseUsername + uniqueId;
        String email = baseEmail.replace("@", "." + uniqueId + "@");
        return new String[]{username, email};
    }
}