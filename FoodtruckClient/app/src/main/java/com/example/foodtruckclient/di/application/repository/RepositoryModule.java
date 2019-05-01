package com.example.foodtruckclient.di.application.repository;

import com.example.foodtruckclient.network.foodtruckapi.FoodtruckApiService;
import com.example.foodtruckclient.repository.NetworkRepository;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class RepositoryModule {

    @Provides
    @Singleton
    NetworkRepository provideNetworkRepository(FoodtruckApiService foodtruckApiService) {
        return new NetworkRepository(foodtruckApiService);
    }
}
