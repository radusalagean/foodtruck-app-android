package com.busytrack.foodtruckclient.screen.foodtruckviewer;

import androidx.annotation.Nullable;

import com.busytrack.foodtruckclient.network.foodtruckapi.model.Account;
import com.busytrack.foodtruckclient.network.foodtruckapi.model.Review;

public interface FoodtruckViewerContract {

    boolean isUserAuthenticated();
    @Nullable String getAuthenticatedUserId();
    void submitReview(String title, String content, float rating);
    void updateReview(String reviewId, String title, String content, float rating);
    void removeReview(String reviewId);
    void onAccountSelected(Account account);
    @Nullable Review getMyReview();
    void setMyReviewViewModel(@FoodtruckViewerMyReviewState int myReviewState,
                               String title,
                               String content,
                               float rating);
    FoodtruckViewerMyReviewViewModel getMyReviewViewModel();
}
