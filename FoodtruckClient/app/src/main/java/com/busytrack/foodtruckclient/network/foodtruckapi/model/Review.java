package com.busytrack.foodtruckclient.network.foodtruckapi.model;

import com.google.common.base.Objects;
import com.google.gson.annotations.SerializedName;

import java.util.Date;

public class Review {

    @SerializedName("_id")
    private String id;

    @SerializedName("created")
    private Date created;

    @SerializedName("lastUpdate")
    private Date lastUpdate;

    @SerializedName("title")
    private String title;

    @SerializedName("text")
    private String text;

    @SerializedName("rating")
    private float rating;

    @SerializedName("author")
    private Account author;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Date getCreated() {
        return created;
    }

    public void setCreated(Date created) {
        this.created = created;
    }

    public Date getLastUpdate() {
        return lastUpdate;
    }

    public void setLastUpdate(Date lastUpdate) {
        this.lastUpdate = lastUpdate;
    }

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

    public Account getAuthor() {
        return author;
    }

    public void setAuthor(Account author) {
        this.author = author;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Review review = (Review) o;
        return Float.compare(review.rating, rating) == 0 &&
                Objects.equal(id, review.id) &&
                Objects.equal(created, review.created) &&
                Objects.equal(lastUpdate, review.lastUpdate) &&
                Objects.equal(title, review.title) &&
                Objects.equal(text, review.text) &&
                Objects.equal(author, review.author);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id, created, lastUpdate, title, text, rating, author);
    }
}
