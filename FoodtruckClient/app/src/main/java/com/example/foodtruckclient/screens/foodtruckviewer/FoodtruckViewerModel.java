package com.example.foodtruckclient.screens.foodtruckviewer;

import com.example.foodtruckclient.generic.mvp.BaseModel;
import com.example.foodtruckclient.network.NetworkRepository;
import com.example.foodtruckclient.network.foodtruckapi.model.Foodtruck;
import com.example.foodtruckclient.network.foodtruckapi.model.Review;

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
        FoodtruckViewerViewModel cachedViewModel = getCachedViewModel();
        if (cachedViewModel != null) {
            return Observable.just(cachedViewModel);
        }
        return getFreshViewModel(foodtruckId);
    }

    @Override
    public Observable<FoodtruckViewerViewModel> getFreshViewModel(String foodtruckId) {
        return Observable.zip(networkRepository.getFoodtruck(foodtruckId),
                networkRepository.getAllReviews(foodtruckId),
                networkRepository.getMyReview(foodtruckId).onErrorReturnItem(new Review()),
                FoodtruckViewerViewModel::createFrom)
                .doOnNext(viewModel -> viewModelRepository.setViewModel(viewModel));
    }

    @Override
    public Observable<Foodtruck> getFoodtruck(String id) {
        return networkRepository.getFoodtruck(id)
                .doOnNext(foodtruck -> viewModelRepository.getViewModel().setFoodtruck(foodtruck));
    }

    @Override
    public Observable<List<Review>> getAllReviews(String foodtruckId) {
        return networkRepository.getAllReviews(foodtruckId)
                .doOnNext(reviews -> viewModelRepository.getViewModel().setReviews(reviews));
    }

    @Override
    public Observable<Review> getMyReview(String foodtruckId) {
        return networkRepository.getMyReview(foodtruckId)
                .doOnNext(myReview -> viewModelRepository.getViewModel().setMyReview(myReview));
    }
}
