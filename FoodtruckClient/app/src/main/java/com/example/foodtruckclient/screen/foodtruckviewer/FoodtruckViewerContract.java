package com.example.foodtruckclient.screen.foodtruckviewer;

import androidx.annotation.Nullable;

import com.example.foodtruckclient.network.foodtruckapi.model.Account;
import com.google.android.gms.maps.MapView;

public interface FoodtruckViewerContract {

    void takeMapView(MapView mapView);
    boolean isUserAuthenticated();
    @Nullable String getAuthenticatedUserId();
    void submitReview(String title, String content, float rating);
    void updateReview(String reviewId, String title, String content, float rating);
    void removeReview(String reviewId);
    void onAccountSelected(Account account);
}
