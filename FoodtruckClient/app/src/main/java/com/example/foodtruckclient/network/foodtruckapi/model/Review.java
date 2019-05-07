package com.example.foodtruckclient.network.foodtruckapi.model;

import com.google.common.base.Objects;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Review review = (Review) o;
        return Float.compare(review.rating, rating) == 0 &&
                Objects.equal(title, review.title) &&
                Objects.equal(text, review.text);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(title, text, rating);
    }
}
