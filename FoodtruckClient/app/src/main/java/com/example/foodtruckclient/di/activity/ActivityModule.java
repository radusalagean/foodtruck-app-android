package com.example.foodtruckclient.di.activity;

import android.content.Context;

import androidx.fragment.app.FragmentActivity;

import com.example.foodtruckclient.screens.dashboard.DashboardMVP;
import com.example.foodtruckclient.screens.dashboard.DashboardModel;
import com.example.foodtruckclient.screens.dashboard.DashboardPresenter;
import com.example.foodtruckclient.screens.dashboard.DashboardViewModelRepository;
import com.example.foodtruckclient.dialog.DialogManager;
import com.example.foodtruckclient.generic.activity.ActivityContract;
import com.example.foodtruckclient.location.LocationManager;
import com.example.foodtruckclient.permission.PermissionManager;
import com.example.foodtruckclient.network.NetworkRepository;
import com.example.foodtruckclient.screens.foodtruckviewer.FoodtruckViewerMVP;
import com.example.foodtruckclient.screens.foodtruckviewer.FoodtruckViewerModel;
import com.example.foodtruckclient.screens.foodtruckviewer.FoodtruckViewerPresenter;
import com.example.foodtruckclient.screens.foodtruckviewer.FoodtruckViewerViewModelRepository;

import dagger.Module;
import dagger.Provides;

@Module
public class ActivityModule {

    private final FragmentActivity activity;

    public ActivityModule(FragmentActivity activity) {
        this.activity = activity;
    }

    @Provides
    @ActivityScope
    FragmentActivity provideActivity() {
        return activity;
    }

    @Provides
    @ActivityScope
    Context provideContext() {
        return activity;
    }

    @Provides
    @ActivityScope
    ActivityContract provideActivityContract() {
        return (ActivityContract) activity;
    }

    // Dialog

    @Provides
    @ActivityScope
    DialogManager provideDialogManager() {
        return new DialogManager(activity);
    }

    // Dashboard

    @Provides
    DashboardMVP.Model provideDashboardModel(NetworkRepository networkRepository,
                                             DashboardViewModelRepository viewModelRepository) {
        return new DashboardModel(networkRepository, viewModelRepository);
    }

    @Provides
    DashboardMVP.Presenter provideDashboardPresenter(DashboardMVP.Model model,
                                                     LocationManager locationManager,
                                                     PermissionManager permissionManager,
                                                     DialogManager dialogManager,
                                                     ActivityContract activityContract) {
        return new DashboardPresenter(
                model, locationManager, permissionManager, dialogManager, activityContract
        );
    }

    // Foodtruck Viewer

    @Provides
    FoodtruckViewerMVP.Model provideFoodtruckViewerModel(NetworkRepository networkRepository,
                                                         FoodtruckViewerViewModelRepository viewModelRepository) {
        return new FoodtruckViewerModel(networkRepository, viewModelRepository);
    }

    @Provides
    FoodtruckViewerMVP.Presenter provideFoodtruckViewerPresenter(FoodtruckViewerMVP.Model model) {
        return new FoodtruckViewerPresenter(model);
    }
}
