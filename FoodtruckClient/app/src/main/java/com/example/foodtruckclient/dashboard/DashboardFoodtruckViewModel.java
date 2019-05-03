package com.example.foodtruckclient.dashboard;

import com.example.foodtruckclient.network.NetworkConstants;
import com.example.foodtruckclient.network.foodtruckapi.model.Foodtruck;
import com.google.common.base.Objects;

public class DashboardFoodtruckViewModel {

    private String id;
    private String name;
    private double latitude;
    private double longitude;
    private float averageRating;
    private int ratingCount;
    private String imageUrl;
    private String thumbnailUrl;


    private DashboardFoodtruckViewModel(String id, String name, double latitude, double longitude, float averageRating, int ratingCount, String imageUrl, String thumbnailUrl) {
        this.id = id;
        this.name = name;
        this.latitude = latitude;
        this.longitude = longitude;
        this.averageRating = averageRating;
        this.ratingCount = ratingCount;
        this.imageUrl = imageUrl;
        this.thumbnailUrl = thumbnailUrl;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public double getLatitude() {
        return latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public float getAverageRating() {
        return averageRating;
    }

    public int getRatingCount() {
        return ratingCount;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public String getThumbnailUrl() {
        return thumbnailUrl;
    }

    public static DashboardFoodtruckViewModel createFrom(Foodtruck foodtruck) {
        return new DashboardFoodtruckViewModel(
                foodtruck.getId(),
                foodtruck.getName(),
                foodtruck.getCoordinates().getLatitude(),
                foodtruck.getCoordinates().getLongitude(),
                foodtruck.getAverageRating(),
                foodtruck.getRatingCount(),
                NetworkConstants.FOODTRUCK_API_FOODTRUCK_IMAGES_BASE_URL + foodtruck.getImage(),
                NetworkConstants.FOODTRUCK_API_FOODTRUCK_THUMBNAILS_BASE_URL + foodtruck.getImage());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        DashboardFoodtruckViewModel that = (DashboardFoodtruckViewModel) o;
        return Double.compare(that.latitude, latitude) == 0 &&
                Double.compare(that.longitude, longitude) == 0 &&
                Float.compare(that.averageRating, averageRating) == 0 &&
                ratingCount == that.ratingCount &&
                Objects.equal(id, that.id) &&
                Objects.equal(name, that.name) &&
                Objects.equal(imageUrl, that.imageUrl) &&
                Objects.equal(thumbnailUrl, that.thumbnailUrl);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id, name, latitude, longitude, averageRating, ratingCount, imageUrl, thumbnailUrl);
    }
}
