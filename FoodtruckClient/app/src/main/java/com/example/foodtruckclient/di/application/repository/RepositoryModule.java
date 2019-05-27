package com.example.foodtruckclient.di.application.repository;

import android.content.SharedPreferences;

import com.example.foodtruckclient.authentication.AuthenticationRepository;
import com.example.foodtruckclient.generic.viewmodel.ViewModelManager;
import com.example.foodtruckclient.persistence.SharedPreferencesRepository;
import com.example.foodtruckclient.screen.dashboard.DashboardViewModelRepository;
import com.example.foodtruckclient.di.application.ApplicationScope;
import com.example.foodtruckclient.screen.foodtruckeditor.FoodtruckEditorViewModelRepository;
import com.example.foodtruckclient.screen.foodtruckviewer.FoodtruckViewerViewModelRepository;
import com.example.foodtruckclient.network.foodtruckapi.FoodtruckApiService;
import com.example.foodtruckclient.network.NetworkRepository;
import com.example.foodtruckclient.screen.profile.ProfileViewModelRepository;

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
    DashboardViewModelRepository provideDashboardViewModelRepository() {
        return new DashboardViewModelRepository();
    }

    @Provides
    @ApplicationScope
    FoodtruckViewerViewModelRepository provideFoodtruckViewerViewModelRepository() {
        return new FoodtruckViewerViewModelRepository();
    }

    @Provides
    @ApplicationScope
    ProfileViewModelRepository provideProfileViewModelRepository() {
        return new ProfileViewModelRepository();
    }

    @Provides
    @ApplicationScope
    FoodtruckEditorViewModelRepository provideFoodtruckEditorViewModelRepository() {
        return new FoodtruckEditorViewModelRepository();
    }
}
