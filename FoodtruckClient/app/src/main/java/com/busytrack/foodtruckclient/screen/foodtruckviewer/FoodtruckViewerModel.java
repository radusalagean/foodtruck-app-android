package com.busytrack.foodtruckclient.screen.foodtruckviewer;

import com.busytrack.foodtruckclient.generic.mvp.BaseModel;
import com.busytrack.foodtruckclient.network.NetworkRepository;
import com.busytrack.foodtruckclient.network.foodtruckapi.model.Foodtruck;
import com.busytrack.foodtruckclient.network.foodtruckapi.model.Message;
import com.busytrack.foodtruckclient.network.foodtruckapi.model.Review;

import java.util.List;

import io.reactivex.Observable;

public class FoodtruckViewerModel extends BaseModel<FoodtruckViewerViewModel, FoodtruckViewerViewModelRepository>
        implements FoodtruckViewerMVP.Model {

    public FoodtruckViewerModel(NetworkRepository networkRepository,
                                FoodtruckViewerViewModelRepository viewModelRepository) {
        super(networkRepository, viewModelRepository);
    }

    @Override
    public Observable<FoodtruckViewerViewModel> getViewModel(String foodtruckId) {
        return Observable.zip(networkRepository.getFoodtruck(foodtruckId),
                networkRepository.getAllFoodtruckReviews(foodtruckId),
                networkRepository.getMyFoodtruckReview(foodtruckId).onErrorReturnItem(new Review()),
                FoodtruckViewerViewModel::createFrom)
                .doOnNext(viewModel -> viewModelRepository.addViewModel(uuid, viewModel));
    }

    @Override
    public Observable<Foodtruck> getFoodtruck(String id) {
        return networkRepository.getFoodtruck(id)
                .doOnNext(foodtruck -> viewModelRepository.getViewModel(uuid).setFoodtruck(foodtruck));
    }

    @Override
    public Observable<List<Review>> getAllReviews(String foodtruckId) {
        return networkRepository.getAllFoodtruckReviews(foodtruckId)
                .doOnNext(reviews -> viewModelRepository.getViewModel(uuid).setReviews(reviews));
    }

    @Override
    public Observable<Review> getMyReview(String foodtruckId) {
        return networkRepository.getMyFoodtruckReview(foodtruckId)
                .doOnNext(myReview -> viewModelRepository.getViewModel(uuid).setMyReview(myReview));
    }

    @Override
    public Observable<Message> submitReview(String foodtruckId, Review review) {
        return networkRepository.addFoodtruckReview(foodtruckId, review);
    }

    @Override
    public Observable<Message> updateReview(String reviewId, Review review) {
        return networkRepository.updateFoodtruckReview(reviewId, review);
    }

    @Override
    public Observable<Message> removeReview(String reviewId) {
        return networkRepository.deleteFoodtruckReview(reviewId);
    }

    @Override
    public Observable<Message> removeFoodtruck(String foodtruckId) {
        return networkRepository.deleteFoodtruck(foodtruckId);
    }
}
