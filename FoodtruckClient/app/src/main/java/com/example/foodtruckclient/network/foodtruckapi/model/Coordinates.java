package com.example.foodtruckclient.network.foodtruckapi.model;

import com.google.gson.annotations.SerializedName;

public class Coordinates {

    @SerializedName("lat")
    private double latitude;

    @SerializedName("long")
    private double longitude;

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }
}
