package com.example.foodtruckclient.dashboard.viewmodel;

import com.example.foodtruckclient.network.foodtruckapi.model.Foodtruck;

import java.util.List;

public class DashboardViewModel {

    private List<Foodtruck> foodtrucks;

    public DashboardViewModel(List<Foodtruck> foodtrucks) {
        this.foodtrucks = foodtrucks;
    }

    public List<Foodtruck> getFoodtrucks() {
        return foodtrucks;
    }

    public void setFoodtrucks(List<Foodtruck> foodtrucks) {
        this.foodtrucks = foodtrucks;
    }

    public static DashboardViewModel createFrom(List<Foodtruck> foodtrucks) {
        return new DashboardViewModel(
                foodtrucks
        );
    }
}
