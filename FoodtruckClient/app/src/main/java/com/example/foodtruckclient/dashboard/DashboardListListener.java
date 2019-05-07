package com.example.foodtruckclient.dashboard;

import com.example.foodtruckclient.network.foodtruckapi.model.Foodtruck;

public interface DashboardListListener {

    void onFoodtruckSelected(Foodtruck foodtruck);
    void onFoodtruckLocationButtonClicked(Foodtruck foodtruck);
}
