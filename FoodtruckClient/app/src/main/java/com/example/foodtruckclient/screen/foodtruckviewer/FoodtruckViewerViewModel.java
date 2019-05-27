package com.example.foodtruckclient.screen.foodtruckviewer;

import com.example.foodtruckclient.generic.viewmodel.BaseViewModel;
import com.example.foodtruckclient.network.foodtruckapi.model.Foodtruck;
import com.example.foodtruckclient.network.foodtruckapi.model.Review;

import java.util.List;

public class FoodtruckViewerViewModel extends BaseViewModel {

    private Foodtruck foodtruck;
    private List<Review> reviews;
    private Review myReview;

    private FoodtruckViewerViewModel(Foodtruck foodtruck, List<Review> reviews, Review myReview) {
        this.foodtruck = foodtruck;
        this.reviews = reviews;
        this.myReview = myReview;
    }

    public Foodtruck getFoodtruck() {
        return foodtruck;
    }

    public void setFoodtruck(Foodtruck foodtruck) {
        this.foodtruck = foodtruck;
    }

    public List<Review> getReviews() {
        return reviews;
    }

    public void setReviews(List<Review> reviews) {
        this.reviews = reviews;
    }

    public Review getMyReview() {
        return myReview;
    }

    public void setMyReview(Review myReview) {
        this.myReview = myReview;
    }

    public static FoodtruckViewerViewModel createFrom(Foodtruck foodtruck,
                                                      List<Review> reviews,
                                                      Review myReview) {
        return new FoodtruckViewerViewModel(foodtruck, reviews, myReview);
    }
}
