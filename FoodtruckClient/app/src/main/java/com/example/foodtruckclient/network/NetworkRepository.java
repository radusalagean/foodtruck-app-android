package com.example.foodtruckclient.network;

import androidx.annotation.Nullable;

import com.example.foodtruckclient.network.foodtruckapi.FoodtruckApiService;
import com.example.foodtruckclient.network.foodtruckapi.model.Account;
import com.example.foodtruckclient.network.foodtruckapi.model.Foodtruck;
import com.example.foodtruckclient.network.foodtruckapi.model.LoginResponse;
import com.example.foodtruckclient.network.foodtruckapi.model.Message;
import com.example.foodtruckclient.network.foodtruckapi.model.Review;

import java.util.List;

import io.reactivex.Completable;
import io.reactivex.Observable;
import okhttp3.MultipartBody;

public class NetworkRepository {

    private FoodtruckApiService foodtruckApiService;

    public NetworkRepository(FoodtruckApiService foodtruckApiService) {
        this.foodtruckApiService = foodtruckApiService;
    }

    // Accounts

    public Observable<Message> register(Account account) {
        return foodtruckApiService.registerAccount(account);
    }

    public Observable<LoginResponse> login(Account account) {
        return foodtruckApiService.login(account);
    }

    public Observable<Account> me(@Nullable String token) {
        return foodtruckApiService.me(token);
    }

    public Observable<Account> getAccount(String id) {
        return foodtruckApiService.getAccount(id);
    }

    public Completable checkUsernameAvailability(String username) {
        return foodtruckApiService.checkUsernameAvailability(username);
    }

    public Observable<Message> uploadAccountImage(MultipartBody.Part image) {
        return foodtruckApiService.uploadAccountImage(image);
    }

    public Observable<Message> deleteAccountImage() {
        return foodtruckApiService.deleteAccountImage();
    }

    // Foodtrucks

    public Observable<Foodtruck> addFoodtruck(Foodtruck foodtruck) {
        return foodtruckApiService.addFoodtruck(foodtruck);
    }

    public Observable<List<Foodtruck>> getAllFoodtrucks() {
        return foodtruckApiService.getAllFoodtrucks();
    }

    public Observable<List<Foodtruck>> getFoodtrucks(String ownerId) {
        return foodtruckApiService.getFoodtrucks(ownerId);
    }

    public Observable<Foodtruck> getFoodtruck(String id) {
        return foodtruckApiService.getFoodtruck(id);
    }

    public Observable<Message> updateFoodtruck(String id, Foodtruck foodtruck) {
        return foodtruckApiService.updateFoodtruck(id, foodtruck);
    }

    public Observable<Message> deleteFoodtruck(String id) {
        return foodtruckApiService.deleteFoodtruck(id);
    }

    public Observable<Message> uploadFoodtruckImage(String id, MultipartBody.Part image) {
        return foodtruckApiService.uploadFoodtruckImage(id, image);
    }

    public Observable<Message> deleteFoodtruckImage(String id) {
        return foodtruckApiService.deleteFoodtruckImage(id);
    }

    // Reviews

    public Observable<Message> addFoodtruckReview(String foodtruckId, Review review) {
        return foodtruckApiService.addFoodtruckReview(foodtruckId, review);
    }

    public Observable<List<Review>> getAllFoodtruckReviews(String foodtruckId) {
        return foodtruckApiService.getAllFoodtruckReviews(foodtruckId);
    }

    public Observable<Review> getMyFoodtruckReview(String foodtruckId) {
        return foodtruckApiService.getMyFoodtruckReview(foodtruckId);
    }

    public Observable<Message> updateFoodtruckReview(String reviewId, Review review) {
        return foodtruckApiService.updateFoodtruckReview(reviewId, review);
    }

    public Observable<Message> deleteFoodtruckReview(String id) {
        return foodtruckApiService.deleteFoodtruckReview(id);
    }
}
