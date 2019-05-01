package com.example.foodtruckclient.dashboard;

import com.example.foodtruckclient.repository.NetworkRepository;
import com.example.foodtruckclient.network.foodtruckapi.model.Foodtruck;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;

public class DashboardModel implements DashboardMVP.Model {

    NetworkRepository networkRepository;

    public DashboardModel(NetworkRepository networkRepository) {
        this.networkRepository = networkRepository;
    }

    @Override
    public Observable<List<DashboardFoodtruckViewModel>> getResults() {
        return networkRepository.getAllFoodtrucks()
                .map((foodtrucks) -> {
                    List<DashboardFoodtruckViewModel> results = new ArrayList<>();
                    for (Foodtruck foodtruck : foodtrucks) {
                        results.add(DashboardFoodtruckViewModel.fromFoodtruck(foodtruck));
                    }
                    return results;
                });
    }
}
