package com.busytrack.foodtruckclient.network.foodtruckapi.model;

import com.google.gson.annotations.SerializedName;

public class LoginResponse {

    @SerializedName("user")
    private String user;

    @SerializedName("token")
    private String token;

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
