package com.busytrack.foodtruckclient.screen.foodtruckviewer;

import androidx.annotation.Nullable;

import com.busytrack.foodtruckclient.generic.mapmvp.BaseMapMVP;
import com.busytrack.foodtruckclient.network.foodtruckapi.model.Foodtruck;
import com.busytrack.foodtruckclient.network.foodtruckapi.model.Message;
import com.busytrack.foodtruckclient.network.foodtruckapi.model.Review;

import java.util.List;

import io.reactivex.Observable;

public interface FoodtruckViewerMVP {

    interface Model extends BaseMapMVP.Model<FoodtruckViewerViewModel> {
        Observable<FoodtruckViewerViewModel> getViewModel(String foodtruckId);
        Observable<Foodtruck> getFoodtruck(String id);
        Observable<List<Review>> getAllReviews(String foodtruckId);
        Observable<Review> getMyReview(String foodtruckId);
        Observable<Message> submitReview(String foodtruckId, Review review);
        Observable<Message> updateReview(String reviewId, Review review);
        Observable<Message> removeReview(String reviewId);
        Observable<Message> removeFoodtruck(String foodtruckId);
    }

    interface View extends BaseMapMVP.View {
        void updateFoodtruck(Foodtruck foodtruck);
        void updateMyReview(Review myReview);
        void updateReviews(List<Review> reviews);
    }

    interface Presenter extends BaseMapMVP.Presenter<View> {
        void loadViewModel(String foodtruckId);
        void reloadFoodtruck(String foodtruckId);
        void reloadAllReviews(String foodtruckId);
        void reloadMyReview(String foodtruckId);
        void submitReview(String foodtruckId, String title, String content, float rating);
        void updateReview(String reviewId, String title, String content, float rating);
        void removeReview(String reviewId);
        void removeFoodtruck(String foodtruckId);
        void zoomOnFoodtruck(boolean instant);
        @Nullable Review getMyReview();
        void setMyReviewViewModel(FoodtruckViewerMyReviewViewModel myReviewViewModel);
        @Nullable FoodtruckViewerMyReviewViewModel getMyReviewViewModel();
    }
}
