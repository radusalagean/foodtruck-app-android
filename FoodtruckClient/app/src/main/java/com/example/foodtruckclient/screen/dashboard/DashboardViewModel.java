package com.example.foodtruckclient.screen.dashboard;

import com.example.foodtruckclient.generic.viewmodel.BaseViewModel;
import com.example.foodtruckclient.network.foodtruckapi.model.Foodtruck;

import java.util.List;

public class DashboardViewModel extends BaseViewModel {

    private List<Foodtruck> foodtrucks;

    private DashboardViewModel(List<Foodtruck> foodtrucks) {
        this.foodtrucks = foodtrucks;
    }

    public List<Foodtruck> getFoodtrucks() {
        return foodtrucks;
    }

    public void setFoodtrucks(List<Foodtruck> foodtrucks) {
        this.foodtrucks = foodtrucks;
    }

    public static DashboardViewModel createFrom(List<Foodtruck> foodtrucks) {
        return new DashboardViewModel(foodtrucks);
    }
}
