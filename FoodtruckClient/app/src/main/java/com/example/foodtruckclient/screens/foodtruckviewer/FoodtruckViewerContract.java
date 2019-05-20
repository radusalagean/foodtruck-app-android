package com.example.foodtruckclient.screens.foodtruckviewer;

import com.google.android.gms.maps.MapView;

import javax.annotation.Nullable;

public interface FoodtruckViewerContract {

    void takeMapView(MapView mapView);
    boolean isUserAuthenticated();
    @Nullable String getAuthenticatedUserId();
    void submitReview(String title, String content, float rating);
    void updateReview(String reviewId, String title, String content, float rating);
    void removeReview(String reviewId);
}
