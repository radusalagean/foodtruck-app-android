package com.example.foodtruckclient.dashboard;

import com.example.foodtruckclient.dashboard.viewmodel.DashboardViewModel;
import com.example.foodtruckclient.dashboard.viewmodel.DashboardViewModelRepository;
import com.example.foodtruckclient.network.NetworkRepository;
import com.example.foodtruckclient.network.foodtruckapi.model.Foodtruck;

import java.util.List;

import javax.annotation.Nullable;

import io.reactivex.Observable;

public class DashboardModel implements DashboardMVP.Model {

    private NetworkRepository networkRepository;
    private DashboardViewModelRepository viewModelRepository;

    public DashboardModel(NetworkRepository networkRepository,
                          DashboardViewModelRepository viewModelRepository) {
        this.networkRepository = networkRepository;
        this.viewModelRepository = viewModelRepository;
    }

    @Override
    public Observable<DashboardViewModel> getViewModel() {
        DashboardViewModel viewModel = getCachedViewModel();
        if (viewModel != null) {
            return Observable.just(viewModel);
        }
        return networkRepository.getAllFoodtrucks()
                .map(DashboardViewModel::createFrom)
                .doOnNext(model -> viewModelRepository.setViewModel(model));
    }

    @Override
    @Nullable
    public DashboardViewModel getCachedViewModel() {
        return viewModelRepository.getViewModel();
    }

    @Override
    public Observable<List<Foodtruck>> getFoodtrucks() {
        return networkRepository.getAllFoodtrucks()
                .doOnNext(foodtrucks -> viewModelRepository.getViewModel().setFoodtrucks(foodtrucks));
    }
}
