package com.example.foodtruckclient.dashboard;

import com.example.foodtruckclient.network.foodtruckapi.model.Foodtruck;

public class DashboardFoodtruckViewModel {

    private String name;
    private double latitude;
    private double longitude;
    private float averageRating;

    private DashboardFoodtruckViewModel(String name, double latitude, double longitude, float averageRating) {
        this.name = name;
        this.latitude = latitude;
        this.longitude = longitude;
        this.averageRating = averageRating;
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

    public static DashboardFoodtruckViewModel fromFoodtruck(Foodtruck foodtruck) {
        return new DashboardFoodtruckViewModel(
                foodtruck.getName(),
                foodtruck.getCoordinates().getLatitude(),
                foodtruck.getCoordinates().getLongitude(),
                foodtruck.getAverageRating());
    }
}
