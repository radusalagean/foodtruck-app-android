package com.example.foodtruckclient.di.application.repository;

import com.example.foodtruckclient.dashboard.viewmodel.DashboardViewModelRepository;
import com.example.foodtruckclient.di.application.ApplicationScope;
import com.example.foodtruckclient.network.foodtruckapi.FoodtruckApiService;
import com.example.foodtruckclient.network.NetworkRepository;

import dagger.Module;
import dagger.Provides;

@Module
public class RepositoryModule {

    @Provides
    @ApplicationScope
    NetworkRepository provideNetworkRepository(FoodtruckApiService foodtruckApiService) {
        return new NetworkRepository(foodtruckApiService);
    }

    // View Model Repositories

    @Provides
    @ApplicationScope
    DashboardViewModelRepository provideViewModelRepository() {
        return new DashboardViewModelRepository();
    }
}
