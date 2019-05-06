package com.example.foodtruckclient.di.activity;

import com.example.foodtruckclient.activity.MainActivity;
import com.example.foodtruckclient.dashboard.DashboardFragment;
import com.example.foodtruckclient.di.activity.location.LocationModule;
import com.example.foodtruckclient.di.activity.permission.PermissionModule;
import com.example.foodtruckclient.foodtruckviewer.FoodtruckViewerFragment;

import dagger.Subcomponent;

@ActivityScope
@Subcomponent(modules = {
        ActivityModule.class,
        LocationModule.class,
        PermissionModule.class
})
public interface ActivityComponent {

    void inject(MainActivity activity);
    void inject(DashboardFragment dashboardFragment);
    void inject(FoodtruckViewerFragment foodtruckViewerFragment);
}
