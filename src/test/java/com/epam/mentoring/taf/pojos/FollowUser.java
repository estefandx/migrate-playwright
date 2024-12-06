package com.epam.mentoring.taf.pojos;

import com.fasterxml.jackson.annotation.JsonProperty;

public class FollowUser {

    private String name;
    @JsonProperty("isValid")
    private boolean isValid;

    // Getters y Setters
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isValid() {
        return isValid;
    }

    public void setValid(boolean isValid) {
        this.isValid = isValid;
    }
}
