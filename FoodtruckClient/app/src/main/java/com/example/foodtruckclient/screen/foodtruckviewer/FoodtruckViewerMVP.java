package com.example.foodtruckclient.screen.foodtruckviewer;

import com.example.foodtruckclient.generic.mapmvp.BaseMapMVP;
import com.example.foodtruckclient.network.foodtruckapi.model.Foodtruck;
import com.example.foodtruckclient.network.foodtruckapi.model.Message;
import com.example.foodtruckclient.network.foodtruckapi.model.Review;

import java.util.List;

import io.reactivex.Observable;

public interface FoodtruckViewerMVP {

    interface Model extends BaseMapMVP.Model<FoodtruckViewerViewModel> {
        Observable<FoodtruckViewerViewModel> getViewModel(String foodtruckId);
        Observable<FoodtruckViewerViewModel> getFreshViewModel(String foodtruckId);
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
        void triggerDataRefresh();
    }

    interface Presenter extends BaseMapMVP.Presenter<View> {
        void loadViewModel(String foodtruckId, boolean refresh);
        void reloadData(String foodtruckId);
        void submitReview(String foodtruckId, String title, String content, float rating);
        void updateReview(String reviewId, String title, String content, float rating);
        void removeReview(String reviewId);
        void removeFoodtruck(String foodtruckId);
    }
}
