package com.example.foodtruckclient.di.application.repository;

import android.content.SharedPreferences;

import com.example.foodtruckclient.authentication.AuthenticationRepository;
import com.example.foodtruckclient.generic.viewmodel.ViewModelManager;
import com.example.foodtruckclient.persistence.SharedPreferencesRepository;
import com.example.foodtruckclient.screens.dashboard.DashboardViewModelRepository;
import com.example.foodtruckclient.di.application.ApplicationScope;
import com.example.foodtruckclient.screens.foodtruckviewer.FoodtruckViewerViewModelRepository;
import com.example.foodtruckclient.network.foodtruckapi.FoodtruckApiService;
import com.example.foodtruckclient.network.NetworkRepository;

import dagger.Module;
import dagger.Provides;

@Module
public class RepositoryModule {

    // Repositories

    @Provides
    @ApplicationScope
    NetworkRepository provideNetworkRepository(FoodtruckApiService foodtruckApiService) {
        return new NetworkRepository(foodtruckApiService);
    }

    @Provides
    @ApplicationScope
    SharedPreferencesRepository provideSharedPreferencesRepository(SharedPreferences sharedPreferences) {
        return new SharedPreferencesRepository(sharedPreferences);
    }

    @Provides
    @ApplicationScope
    AuthenticationRepository provideAuthenticationRepository(SharedPreferencesRepository sharedPreferencesRepository) {
        return new AuthenticationRepository(sharedPreferencesRepository);
    }

    // View Model Repositories

    @Provides
    @ApplicationScope
    ViewModelManager provideViewModelManager(DashboardViewModelRepository dashboardViewModelRepository,
                                             FoodtruckViewerViewModelRepository foodtruckViewerViewModelRepository) {
        return new ViewModelManager(dashboardViewModelRepository, foodtruckViewerViewModelRepository);
    }

    @Provides
    @ApplicationScope
    DashboardViewModelRepository provideViewModelRepository() {
        return new DashboardViewModelRepository();
    }

    @Provides
    @ApplicationScope
    FoodtruckViewerViewModelRepository provideFoodtruckViewerViewModelRepository() {
        return new FoodtruckViewerViewModelRepository();
    }
}
