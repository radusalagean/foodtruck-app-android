package com.busytrack.foodtruckclient.network.foodtruckapi.model;

import com.busytrack.foodtruckclient.network.NetworkConstants;
import com.google.common.base.Objects;
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

    @SerializedName("lastUpdate")
    private Date lastUpdate;

    @SerializedName("image")
    private String image;

    @SerializedName("token")
    private String token;

    @SerializedName("serviceAccessCode")
    private String serviceAccessCode;

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

    public Date getLastUpdate() {
        return lastUpdate;
    }

    public void setLastUpdate(Date lastUpdate) {
        this.lastUpdate = lastUpdate;
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

    public String getServiceAccessCode() {
        return serviceAccessCode;
    }

    public void setServiceAccessCode(String serviceAccessCode) {
        this.serviceAccessCode = serviceAccessCode;
    }

    public String getImageUrl() {
        return image != null ? NetworkConstants.FOODTRUCK_API_PROFILE_500_BASE_URL + image : null;
    }

    public String getThumbnailUrl() {
        return image != null ? NetworkConstants.FOODTRUCK_API_PROFILE_THUMBNAILS_BASE_URL + image : null;
    }

    public String getImageSignature() {
        return getImageUrl() + "@" + lastUpdate;
    }

    public String getThumbnailSignature() {
        return getThumbnailUrl() + "@" + lastUpdate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Account account = (Account) o;
        return Objects.equal(id, account.id) &&
                Objects.equal(username, account.username) &&
                Objects.equal(image, account.image);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id, username, image);
    }

    public static Account createFrom(LoginResponse loginResponse, Account account) {
        account.setToken(loginResponse.getToken());
        return account;
    }
}
