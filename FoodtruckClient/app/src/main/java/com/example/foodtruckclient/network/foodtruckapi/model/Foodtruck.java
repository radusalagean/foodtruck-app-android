package com.example.foodtruckclient.network.foodtruckapi.model;

import com.example.foodtruckclient.network.NetworkConstants;
import com.google.common.base.Objects;
import com.google.gson.annotations.SerializedName;

import java.util.Date;

public class Foodtruck {

    @SerializedName("_id")
    private String id;

    @SerializedName("name")
    private String name;

    @SerializedName("foodtype")
    private String foodtype;

    @SerializedName("coordinates")
    private Coordinates coordinates;

    @SerializedName("image")
    private String image;

    @SerializedName("owner")
    private Account owner;

    @SerializedName("created")
    private Date created;

    @SerializedName("lastUpdate")
    private Date lastUpdate;

    @SerializedName("avgRating")
    private float averageRating;

    @SerializedName("ratingCount")
    private int ratingCount;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getFoodtype() {
        return foodtype;
    }

    public void setFoodtype(String foodtype) {
        this.foodtype = foodtype;
    }

    public Coordinates getCoordinates() {
        return coordinates;
    }

    public void setCoordinates(Coordinates coordinates) {
        this.coordinates = coordinates;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public Account getOwner() {
        return owner;
    }

    public void setOwner(Account owner) {
        this.owner = owner;
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

    public float getAverageRating() {
        return averageRating;
    }

    public void setAverageRating(float averageRating) {
        this.averageRating = averageRating;
    }

    public int getRatingCount() {
        return ratingCount;
    }

    public void setRatingCount(int ratingCount) {
        this.ratingCount = ratingCount;
    }

    public String getImageUrl() {
        return image != null ? NetworkConstants.FOODTRUCK_API_FOODTRUCK_IMAGES_BASE_URL + image : null;
    }

    public String getThumbnailUrl() {
        return image != null ? NetworkConstants.FOODTRUCK_API_FOODTRUCK_THUMBNAILS_BASE_URL + image : null;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Foodtruck foodtruck = (Foodtruck) o;
        return Float.compare(foodtruck.averageRating, averageRating) == 0 &&
                ratingCount == foodtruck.ratingCount &&
                Objects.equal(id, foodtruck.id) &&
                Objects.equal(name, foodtruck.name) &&
                Objects.equal(foodtype, foodtruck.foodtype) &&
                Objects.equal(coordinates, foodtruck.coordinates) &&
                Objects.equal(image, foodtruck.image) &&
                Objects.equal(owner, foodtruck.owner) &&
                Objects.equal(created, foodtruck.created) &&
                Objects.equal(lastUpdate, foodtruck.lastUpdate);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id, name, foodtype, coordinates, image, owner, created, lastUpdate, averageRating, ratingCount);
    }
}
