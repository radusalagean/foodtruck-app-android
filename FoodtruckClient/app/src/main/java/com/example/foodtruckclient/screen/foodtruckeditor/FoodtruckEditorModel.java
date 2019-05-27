package com.example.foodtruckclient.screen.foodtruckeditor;

import androidx.annotation.Nullable;

import com.example.foodtruckclient.generic.mvp.BaseModel;
import com.example.foodtruckclient.network.NetworkRepository;
import com.example.foodtruckclient.network.foodtruckapi.model.Foodtruck;
import com.example.foodtruckclient.network.foodtruckapi.model.Message;

import io.reactivex.Observable;
import okhttp3.MultipartBody;

public class FoodtruckEditorModel extends BaseModel<FoodtruckEditorViewModel, FoodtruckEditorViewModelRepository>
        implements FoodtruckEditorMVP.Model {

    @Nullable
    private Foodtruck originalFoodtruck;

    public FoodtruckEditorModel(NetworkRepository networkRepository,
                                FoodtruckEditorViewModelRepository viewModelRepository) {
        super(networkRepository, viewModelRepository);
    }

    @Override
    public FoodtruckEditorViewModel getViewModel() {
        if (getCachedViewModel() == null) {
            FoodtruckEditorViewModel viewModel;
            if (originalFoodtruck != null) {
                viewModel = FoodtruckEditorViewModel.createFrom(originalFoodtruck);
            } else {
                viewModel = new FoodtruckEditorViewModel();
            }
            viewModelRepository.addViewModel(uuid, viewModel);
        }
        return getCachedViewModel();
    }

    @Override
    @Nullable
    public Foodtruck getOriginalFoodtruck() {
        return originalFoodtruck;
    }

    @Override
    public void setOriginalFoodtruck(Foodtruck originalFoodtruck) {
        this.originalFoodtruck = originalFoodtruck;
    }

    @Override
    public Observable<Foodtruck> addFoodtruck(Foodtruck foodtruck) {
        return networkRepository.addFoodtruck(foodtruck);
    }

    @Override
    public Observable<Message> updateFoodtruck(String foodtruckId, Foodtruck foodtruck) {
        return networkRepository.updateFoodtruck(foodtruckId, foodtruck);
    }

    @Override
    public Observable<Message> uploadFoodtruckImage(String foodtruckId, MultipartBody.Part image) {
        return networkRepository.uploadFoodtruckImage(foodtruckId, image);
    }

    @Override
    public Observable<Message> deleteFoodtruckImage(String foodtruckId) {
        return networkRepository.deleteFoodtruckImage(foodtruckId);
    }
}
