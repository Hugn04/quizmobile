package com.hugn.quizmobile.data.remote.response;


import com.hugn.quizmobile.domain.model.User;

public class LoginResponse {
    private String token;
    private User user;

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}

