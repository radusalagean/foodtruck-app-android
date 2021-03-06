package com.busytrack.foodtruckclient.network.foodtruckapi;

import androidx.annotation.Nullable;

import com.busytrack.foodtruckclient.network.NetworkConstants;
import com.busytrack.foodtruckclient.network.foodtruckapi.model.Account;
import com.busytrack.foodtruckclient.network.foodtruckapi.model.Foodtruck;
import com.busytrack.foodtruckclient.network.foodtruckapi.model.LoginResponse;
import com.busytrack.foodtruckclient.network.foodtruckapi.model.Message;
import com.busytrack.foodtruckclient.network.foodtruckapi.model.Review;

import java.util.List;

import io.reactivex.Completable;
import io.reactivex.Observable;
import okhttp3.MultipartBody;
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
    Observable<Message> uploadAccountImage(@Part MultipartBody.Part image);

    @DELETE("account/image")
    Observable<Message> deleteAccountImage();

    // Foodtrucks

    @POST("foodtrucks/add")
    Observable<Foodtruck> addFoodtruck(@Body Foodtruck foodtruck);

    @GET("foodtrucks/get")
    Observable<List<Foodtruck>> getAllFoodtrucks();

    @GET("foodtrucks/get/owned_by/{owner_id}")
    Observable<List<Foodtruck>> getFoodtrucks(@Path("owner_id") String ownerId);

    @GET("foodtrucks/get/{id}")
    Observable<Foodtruck> getFoodtruck(@Path("id") String id);

    @PUT("foodtrucks/update/{id}")
    Observable<Message> updateFoodtruck(@Path("id") String id, @Body Foodtruck foodtruck);

    @DELETE("foodtrucks/delete/{id}")
    Observable<Message> deleteFoodtruck(@Path("id") String id);

    @Multipart
    @POST("foodtrucks/image/{id}")
    Observable<Message> uploadFoodtruckImage(@Path("id") String id, @Part MultipartBody.Part image);

    @DELETE("foodtrucks/image/{id}")
    Observable<Message> deleteFoodtruckImage(@Path("id") String id);

    // Reviews

    @POST("foodtrucks/reviews/add/{foodtruck_id}")
    Observable<Message> addFoodtruckReview(@Path("foodtruck_id") String foodtruckId, @Body Review review);

    @GET("foodtrucks/reviews/get/{foodtruck_id}")
    Observable<List<Review>> getAllFoodtruckReviews(@Path("foodtruck_id") String foodtruckId);

    @GET("foodtrucks/reviews/get/my/{foodtruck_id}")
    Observable<Review> getMyFoodtruckReview(@Path("foodtruck_id") String foodtruckId);

    @PUT("foodtrucks/reviews/update/{id}")
    Observable<Message> updateFoodtruckReview(@Path("id") String id, @Body Review review);

    @DELETE("foodtrucks/reviews/delete/{id}")
    Observable<Message> deleteFoodtruckReview(@Path("id") String id);
}
