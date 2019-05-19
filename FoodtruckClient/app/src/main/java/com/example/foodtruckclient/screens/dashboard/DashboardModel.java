package com.example.foodtruckclient.screens.dashboard;

import com.example.foodtruckclient.generic.mvp.BaseModel;
import com.example.foodtruckclient.network.NetworkRepository;
import com.example.foodtruckclient.network.foodtruckapi.model.Foodtruck;

import java.util.List;

import io.reactivex.Observable;

public class DashboardModel extends BaseModel<DashboardViewModel, DashboardViewModelRepository>
        implements DashboardMVP.Model {

    public DashboardModel(NetworkRepository networkRepository,
                          DashboardViewModelRepository viewModelRepository) {
        super(networkRepository, viewModelRepository);
    }

    @Override
    public Observable<DashboardViewModel> getViewModel() {
        DashboardViewModel cachedViewModel = getCachedViewModel();
        if (cachedViewModel != null) {
            return Observable.just(cachedViewModel);
        }
        return networkRepository.getAllFoodtrucks()
                .map(DashboardViewModel::createFrom)
                .doOnNext(viewModel -> viewModelRepository.addViewModel(uuid, viewModel));
    }

    @Override
    public Observable<List<Foodtruck>> getFoodtrucks() {
        return networkRepository.getAllFoodtrucks()
                .doOnNext(foodtrucks ->
                        viewModelRepository.getViewModel(uuid).setFoodtrucks(foodtrucks));
    }
}
