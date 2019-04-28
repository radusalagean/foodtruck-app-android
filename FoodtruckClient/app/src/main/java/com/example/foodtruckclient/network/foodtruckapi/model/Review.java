package com.example.foodtruckclient.network.foodtruckapi.model;

import com.google.gson.annotations.SerializedName;

public class Review {

    @SerializedName("title")
    private String title;

    @SerializedName("text")
    private String text;

    @SerializedName("rating")
    private float rating;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public float getRating() {
        return rating;
    }

    public void setRating(float rating) {
        this.rating = rating;
    }
}
