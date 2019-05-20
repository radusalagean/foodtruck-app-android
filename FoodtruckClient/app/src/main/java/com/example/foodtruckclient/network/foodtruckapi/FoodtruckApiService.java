package com.example.foodtruckclient.network.foodtruckapi;

import androidx.annotation.Nullable;

import com.example.foodtruckclient.network.NetworkConstants;
import com.example.foodtruckclient.network.foodtruckapi.model.Account;
import com.example.foodtruckclient.network.foodtruckapi.model.Foodtruck;
import com.example.foodtruckclient.network.foodtruckapi.model.LoginResponse;
import com.example.foodtruckclient.network.foodtruckapi.model.Message;
import com.example.foodtruckclient.network.foodtruckapi.model.Review;

import java.util.List;

import io.reactivex.Completable;
import io.reactivex.Observable;
import okhttp3.RequestBody;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Part;
import retrofit2.http.Path;

public interface FoodtruckApiService {

    // Accounts

    @POST("account/register")
    Observable<Message> registerAccount(@Body Account account);

    @POST("account/login")
    Observable<LoginResponse> login(@Body Account account);

    @POST("account/logout")
    Observable<Message> logout();

    @GET("account/me")
    Observable<Account> me(@Nullable @Header(NetworkConstants.HEADER_KEY_AUTHORIZATION) String token);

    @GET("account/get/{id}")
    Observable<Account> getAccount(@Path("id") String id);

    @GET("account/availability/{username}")
    Completable checkUsernameAvailability(@Path("username") String username);

    @Multipart
    @POST("account/image")
    Observable<Message> uploadAccountImage(@Part("image") RequestBody image);

    @DELETE("account/image")
    Observable<Message> deleteAccountImage();

    // Foodtrucks

    @POST("foodtrucks/add")
    Observable<Message> addFoodtruck(@Body Foodtruck foodtruck);

    @GET("foodtrucks/get")
    Observable<List<Foodtruck>> getAllFoodtrucks();

    @GET("foodtrucks/get/{id}")
    Observable<Foodtruck> getFoodtruck(@Path("id") String id);

    @PUT("foodtrucks/update/{id}")
    Observable<Message> updateFoodtruck(@Path("id") String id, @Body Foodtruck foodtruck);

    @DELETE("foodtrucks/delete/{id}")
    Observable<Message> deleteFoodtruck(@Path("id") String id);

    @Multipart
    @POST("foodtruck/image/{id}")
    Observable<Message> uploadFoodtruckImage(@Path("id") String id, @Part("image") RequestBody image);

    @DELETE("foodtruck/image/{id}")
    Observable<Message> deleteFoodtruckImage(@Path("id") String id);

    // Reviews

    @POST("foodtrucks/reviews/add/{foodtruck_id}")
    Observable<Message> addFoodtruckReview(@Path("foodtruck_id") String foodtruckId, @Body Review review);

    @GET("foodtrucks/reviews/get/{foodtruck_id}")
    Observable<List<Review>> getAllReviews(@Path("foodtruck_id") String foodtruckId);

    @GET("foodtrucks/reviews/get/my/{foodtruck_id}")
    Observable<Review> getMyReview(@Path("foodtruck_id") String foodtruckId);

    @PUT("foodtrucks/reviews/update/{id}")
    Observable<Message> updateReview(@Path("id") String id, @Body Review review);

    @DELETE("foodtrucks/reviews/delete/{id}")
    Observable<Message> deleteReview(@Path("id") String id);
}
