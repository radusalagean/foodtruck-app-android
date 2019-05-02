package com.example.foodtruckclient.di.presentation;

import android.content.Context;

import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;

import com.example.foodtruckclient.dashboard.DashboardMVP;
import com.example.foodtruckclient.dashboard.DashboardModel;
import com.example.foodtruckclient.dashboard.DashboardPresenter;
import com.example.foodtruckclient.dialog.DialogManager;
import com.example.foodtruckclient.generic.fragment.FragmentContract;
import com.example.foodtruckclient.location.LocationManager;
import com.example.foodtruckclient.permission.PermissionManager;
import com.example.foodtruckclient.repository.NetworkRepository;

import dagger.Module;
import dagger.Provides;

@Module
public class PresentationModule {

    private final FragmentActivity activity;

    public PresentationModule(FragmentActivity activity) {
        this.activity = activity;
    }

    @Provides
    FragmentActivity provideActivity() {
        return activity;
    }

    @Provides
    Context provideContext() {
        return activity;
    }

    @Provides
    FragmentManager provideFragmentManager() {
        return activity.getSupportFragmentManager();
    }

    @Provides
    FragmentContract provideFragmentContract() {
        return (FragmentContract) activity;
    }

    // Dialog

    @Provides
    DialogManager provideDialogManager() {
        return new DialogManager(activity);
    }

    // Dashboard

    @Provides
    DashboardMVP.Model provideDashboardModel(NetworkRepository networkRepository) {
        return new DashboardModel(networkRepository);
    }

    @Provides
    DashboardMVP.Presenter provideDashboardPresenter(DashboardMVP.Model model,
                                                     LocationManager locationManager,
                                                     PermissionManager permissionManager,
                                                     DialogManager dialogManager) {
        return new DashboardPresenter(model, locationManager, permissionManager, dialogManager);
    }
}
