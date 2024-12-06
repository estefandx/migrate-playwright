package com.epam.mentoring.taf.pojos.models.request;

import com.epam.mentoring.taf.pojos.models.User;
import com.fasterxml.jackson.annotation.JsonProperty;

public class UserRequest {

    @JsonProperty("user")
    private User user;

    public UserRequest() {}

    public UserRequest(User user) {
        this.user = user;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}

