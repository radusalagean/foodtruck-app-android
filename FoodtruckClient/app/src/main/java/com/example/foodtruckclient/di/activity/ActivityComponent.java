package com.example.foodtruckclient.di.activity;

import com.example.foodtruckclient.activity.MainActivity;
import com.example.foodtruckclient.screens.dashboard.DashboardFragment;
import com.example.foodtruckclient.di.activity.location.LocationModule;
import com.example.foodtruckclient.di.activity.permission.PermissionModule;
import com.example.foodtruckclient.screens.foodtruckviewer.FoodtruckViewerFragment;
import com.example.foodtruckclient.screens.login.LoginFragment;
import com.example.foodtruckclient.screens.register.RegisterFragment;

import dagger.Subcomponent;

@ActivityScope
@Subcomponent(modules = {
        ActivityModule.class,
        LocationModule.class,
        PermissionModule.class
})
public interface ActivityComponent {

    void inject(MainActivity mainActivity);
    void inject(DashboardFragment dashboardFragment);
    void inject(FoodtruckViewerFragment foodtruckViewerFragment);
    void inject(LoginFragment loginFragment);
    void inject(RegisterFragment registerFragment);
}
