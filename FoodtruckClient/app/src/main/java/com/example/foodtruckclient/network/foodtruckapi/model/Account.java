package com.example.foodtruckclient.network.foodtruckapi.model;

import com.example.foodtruckclient.network.NetworkConstants;
import com.google.gson.annotations.SerializedName;

import java.util.Date;

public class Account {

    @SerializedName("_id")
    private String id;

    @SerializedName("username")
    private String username;

    @SerializedName("password")
    private String password;

    @SerializedName("joined")
    private Date joined;

    @SerializedName("image")
    private String image;

    @SerializedName("token")
    private String token;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Date getJoined() {
        return joined;
    }

    public void setJoined(Date joined) {
        this.joined = joined;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getImageUrl() {
        return image != null ? NetworkConstants.FOODTRUCK_API_PROFILE_IMAGES_BASE_URL + image : null;
    }

    public String getThumbnailUrl() {
        return image != null ? NetworkConstants.FOODTRUCK_API_PROFILE_THUMBNAILS_BASE_URL + image : null;
    }
}
