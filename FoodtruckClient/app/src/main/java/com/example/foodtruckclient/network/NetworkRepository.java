package com.example.foodtruckclient.network;

import androidx.annotation.Nullable;

import com.example.foodtruckclient.network.foodtruckapi.FoodtruckApiService;
import com.example.foodtruckclient.network.foodtruckapi.model.Account;
import com.example.foodtruckclient.network.foodtruckapi.model.Foodtruck;
import com.example.foodtruckclient.network.foodtruckapi.model.LoginResponse;
import com.example.foodtruckclient.network.foodtruckapi.model.Message;
import com.example.foodtruckclient.network.foodtruckapi.model.Review;

import java.util.List;

import io.reactivex.Observable;

public class NetworkRepository {

    private FoodtruckApiService foodtruckApiService;

    public NetworkRepository(FoodtruckApiService foodtruckApiService) {
        this.foodtruckApiService = foodtruckApiService;
    }

    // Accounts

    public Observable<LoginResponse> login(Account account) {
        return foodtruckApiService.login(account);
    }

    public Observable<Account> me(@Nullable String token) {
        return foodtruckApiService.me(token);
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

    public Observable<Message> addFoodtruckReview(String foodtruckId, Review review) {
        return foodtruckApiService.addFoodtruckReview(foodtruckId, review);
    }

    public Observable<Message> updateFoodtruckReview(String reviewId, Review review) {
        return foodtruckApiService.updateReview(reviewId, review);
    }

    public Observable<Message> deleteReview(String id) {
        return foodtruckApiService.deleteReview(id);
    }
}
