package com.example.foodtruckclient.di.activity;

import com.example.foodtruckclient.activity.MainActivity;
import com.example.foodtruckclient.di.activity.authentication.AuthenticationModule;
import com.example.foodtruckclient.di.activity.location.LocationModule;
import com.example.foodtruckclient.di.activity.permission.PermissionModule;
import com.example.foodtruckclient.di.activity.viewmodel.ViewModelModule;
import com.example.foodtruckclient.screen.dashboard.DashboardFragment;
import com.example.foodtruckclient.screen.foodtruckeditor.FoodtruckEditorFragment;
import com.example.foodtruckclient.screen.foodtruckviewer.FoodtruckViewerFragment;
import com.example.foodtruckclient.screen.login.LoginFragment;
import com.example.foodtruckclient.screen.profile.ProfileFragment;
import com.example.foodtruckclient.screen.register.RegisterFragment;

import dagger.Subcomponent;

@ActivityScope
@Subcomponent(modules = {
        ActivityModule.class,
        LocationModule.class,
        PermissionModule.class,
        ViewModelModule.class,
        AuthenticationModule.class
})
public interface ActivityComponent {

    void inject(MainActivity mainActivity);
    void inject(DashboardFragment dashboardFragment);
    void inject(FoodtruckViewerFragment foodtruckViewerFragment);
    void inject(LoginFragment loginFragment);
    void inject(RegisterFragment registerFragment);
    void inject(ProfileFragment profileFragment);
    void inject(FoodtruckEditorFragment foodtruckEditorFragment);
}
