package com.example.foodtruckclient.screens.foodtruckviewer;

import com.example.foodtruckclient.generic.mapmvp.BaseMapMVP;
import com.example.foodtruckclient.network.foodtruckapi.model.Foodtruck;
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
    }

    interface View extends BaseMapMVP.View {
        void updateFoodtruck(Foodtruck foodtruck);
        void updateReviews(List<Review> reviews);
        void updateMyReview(Review myReview);
    }

    interface Presenter extends BaseMapMVP.Presenter<View> {
        void loadViewModel(String foodtruckId, boolean refresh);
        void reloadData(String foodtruckId);
    }
}
