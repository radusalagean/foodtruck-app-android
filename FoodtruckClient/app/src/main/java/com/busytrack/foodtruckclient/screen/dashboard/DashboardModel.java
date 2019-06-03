package com.busytrack.foodtruckclient.screen.dashboard;

import com.busytrack.foodtruckclient.generic.mvp.BaseModel;
import com.busytrack.foodtruckclient.network.NetworkRepository;
import com.busytrack.foodtruckclient.network.foodtruckapi.model.Foodtruck;

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
