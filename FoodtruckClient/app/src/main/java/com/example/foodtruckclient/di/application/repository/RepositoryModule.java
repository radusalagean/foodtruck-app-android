package com.example.foodtruckclient.di.application.repository;

import com.example.foodtruckclient.di.application.ApplicationScope;
import com.example.foodtruckclient.network.foodtruckapi.FoodtruckApiService;
import com.example.foodtruckclient.repository.NetworkRepository;

import dagger.Module;
import dagger.Provides;

@Module
public class RepositoryModule {

    @Provides
    @ApplicationScope
    NetworkRepository provideNetworkRepository(FoodtruckApiService foodtruckApiService) {
        return new NetworkRepository(foodtruckApiService);
    }
}
