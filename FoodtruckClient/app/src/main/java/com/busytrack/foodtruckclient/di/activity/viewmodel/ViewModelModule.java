package com.busytrack.foodtruckclient.di.activity.viewmodel;

import com.busytrack.foodtruckclient.di.activity.ActivityScope;
import com.busytrack.foodtruckclient.generic.viewmodel.ViewModelManager;
import com.busytrack.foodtruckclient.screen.dashboard.DashboardViewModelRepository;
import com.busytrack.foodtruckclient.screen.foodtruckeditor.FoodtruckEditorViewModelRepository;
import com.busytrack.foodtruckclient.screen.foodtruckviewer.FoodtruckViewerViewModelRepository;
import com.busytrack.foodtruckclient.screen.profile.ProfileViewModelRepository;

import dagger.Module;
import dagger.Provides;

@Module
public class ViewModelModule {

    // View Model Manager

    @Provides
    @ActivityScope
    ViewModelManager provideViewModelManager(DashboardViewModelRepository dashboardViewModelRepository,
                                             FoodtruckViewerViewModelRepository foodtruckViewerViewModelRepository,
                                             ProfileViewModelRepository profileViewModelRepository,
                                             FoodtruckEditorViewModelRepository foodtruckEditorViewModelRepository) {
        return new ViewModelManager(
                dashboardViewModelRepository,
                foodtruckViewerViewModelRepository,
                profileViewModelRepository,
                foodtruckEditorViewModelRepository
        );
    }
}
