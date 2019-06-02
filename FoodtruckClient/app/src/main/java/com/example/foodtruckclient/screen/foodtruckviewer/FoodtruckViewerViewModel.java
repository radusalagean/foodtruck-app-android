package com.example.foodtruckclient.screen.foodtruckviewer;

import com.example.foodtruckclient.generic.contentinvalidation.ContentType;
import com.example.foodtruckclient.generic.contentinvalidation.InvalidationBundle;
import com.example.foodtruckclient.generic.contentinvalidation.InvalidationEffect;
import com.example.foodtruckclient.generic.contentinvalidation.InvalidationType;
import com.example.foodtruckclient.generic.viewmodel.BaseViewModel;
import com.example.foodtruckclient.network.foodtruckapi.model.Foodtruck;
import com.example.foodtruckclient.network.foodtruckapi.model.Review;

import java.util.List;

public class FoodtruckViewerViewModel extends BaseViewModel {

    private Foodtruck foodtruck;
    private List<Review> reviews;
    private Review myReview;
    private FoodtruckViewerMyReviewViewModel myReviewViewModel;

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

    public FoodtruckViewerMyReviewViewModel getMyReviewViewModel() {
        return myReviewViewModel;
    }

    public void setMyReviewViewModel(FoodtruckViewerMyReviewViewModel myReviewViewModel) {
        this.myReviewViewModel = myReviewViewModel;
    }

    public static FoodtruckViewerViewModel createFrom(Foodtruck foodtruck,
                                                      List<Review> reviews,
                                                      Review myReview) {
        return new FoodtruckViewerViewModel(foodtruck, reviews, myReview);
    }

    @Override
    public void processInvalidationBundle(InvalidationBundle invalidationBundle) {
        // Check if the fragment needs to be popped or the foodtruck needs to be reloaded
        if (invalidationBundle.getContentId().equals(foodtruck.getId()) &&
                invalidationBundle.getContentType() == ContentType.FOODTRUCK) {
            if (invalidationBundle.getInvalidationType() == InvalidationType.REMOVE) {
                invalidationEffects |= InvalidationEffect.POP_FRAGMENT;
            } else {
                invalidationEffects |= InvalidationEffect.FOODTRUCK_RELOAD;
            }
        }
        // Check if reviews need to be reloaded
        if (invalidationBundle.getContentId().equals(foodtruck.getId()) &&
                invalidationBundle.getContentType() == ContentType.REVIEW) {
            invalidationEffects |= InvalidationEffect.REVIEW_RELOAD;
        }
        // Check if profile needs to be reloaded
        if (invalidationBundle.getContentId().equals(foodtruck.getOwner().getId()) &&
                invalidationBundle.getContentType() == ContentType.PROFILE) {
            // Since the profile info comes with the foodtruck, we just reload it
            invalidationEffects |= InvalidationEffect.FOODTRUCK_RELOAD;
        }
    }
}
