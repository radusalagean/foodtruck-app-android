package com.example.foodtruckclient.generic.list.foodtruck;

import com.example.foodtruckclient.network.foodtruckapi.model.Foodtruck;

public interface FoodtruckContract {

    void onFoodtruckSelected(Foodtruck foodtruck);
    void onFoodtruckLocationButtonClicked(Foodtruck foodtruck);
}
