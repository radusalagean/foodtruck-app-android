package com.example.foodtruckclient.di.presentation;

import com.example.foodtruckclient.activity.MainActivity;
import com.example.foodtruckclient.dashboard.DashboardFragment;
import com.example.foodtruckclient.di.presentation.location.LocationModule;
import com.example.foodtruckclient.di.presentation.permission.PermissionModule;

import dagger.Subcomponent;

@Subcomponent(modules = {
        PresentationModule.class,
        LocationModule.class,
        PermissionModule.class
})
public interface PresentationComponent {

    void inject(MainActivity activity);
    void inject(DashboardFragment fragment);
}
