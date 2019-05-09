package com.example.foodtruckclient.network;

import com.example.foodtruckclient.network.foodtruckapi.FoodtruckApiService;
import com.example.foodtruckclient.network.foodtruckapi.model.Foodtruck;
import com.example.foodtruckclient.network.foodtruckapi.model.Review;

import java.util.List;

import io.reactivex.Observable;

public class NetworkRepository {

    private FoodtruckApiService foodtruckApiService;

    public NetworkRepository(FoodtruckApiService foodtruckApiService) {
        this.foodtruckApiService = foodtruckApiService;
    }

    // Foodtrucks

    public Observable<List<Foodtruck>> getAllFoodtrucks() {
        return foodtruckApiService.getAllFoodtrucks();
    }

    public Observable<Foodtruck> getFoodtruck(String id) {
        return foodtruckApiService.getFoodtruck(id);
    }

    // Reviews

    public Observable<List<Review>> getAllReviews(String foodtruckId) {
        return foodtruckApiService.getAllReviews(foodtruckId);
    }

    public Observable<Review> getMyReview(String foodtruckId) {
        return foodtruckApiService.getMyReview(foodtruckId);
    }
}
