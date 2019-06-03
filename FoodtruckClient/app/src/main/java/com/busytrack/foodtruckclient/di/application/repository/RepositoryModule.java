package com.busytrack.foodtruckclient.di.application.repository;

import android.content.SharedPreferences;

import com.busytrack.foodtruckclient.authentication.AuthenticationRepository;
import com.busytrack.foodtruckclient.di.application.ApplicationScope;
import com.busytrack.foodtruckclient.network.NetworkRepository;
import com.busytrack.foodtruckclient.network.foodtruckapi.FoodtruckApiService;
import com.busytrack.foodtruckclient.persistence.SharedPreferencesRepository;
import com.busytrack.foodtruckclient.screen.dashboard.DashboardViewModelRepository;
import com.busytrack.foodtruckclient.screen.foodtruckeditor.FoodtruckEditorViewModelRepository;
import com.busytrack.foodtruckclient.screen.foodtruckviewer.FoodtruckViewerViewModelRepository;
import com.busytrack.foodtruckclient.screen.profile.ProfileViewModelRepository;

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
