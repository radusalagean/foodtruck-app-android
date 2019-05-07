package com.example.foodtruckclient.network;

import com.example.foodtruckclient.network.foodtruckapi.FoodtruckApiService;
import com.example.foodtruckclient.network.foodtruckapi.model.Foodtruck;

import java.util.List;

import io.reactivex.Observable;

public class NetworkRepository {

    FoodtruckApiService foodtruckApiService;

    public NetworkRepository(FoodtruckApiService foodtruckApiService) {
        this.foodtruckApiService = foodtruckApiService;
    }

    // Foodtrucks

    public Observable<List<Foodtruck>> getAllFoodtrucks() {
        return foodtruckApiService.getAllFoodtrucks();
    }
}
