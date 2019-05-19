package com.example.foodtruckclient.screens.foodtruckviewer;

import com.google.android.gms.maps.MapView;

public interface FoodtruckViewerContract {

    void takeMapView(MapView mapView);
    boolean isUserAuthenticated();
    void submitReview(String title, String content, float rating);
    void updateReview(String reviewId, String title, String content, float rating);
    void removeReview(String reviewId);
}
