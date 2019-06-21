package com.busytrack.foodtruckclient.di.activity;

import com.busytrack.foodtruckclient.activity.MainActivity;
import com.busytrack.foodtruckclient.di.activity.authentication.AuthenticationModule;
import com.busytrack.foodtruckclient.di.activity.location.LocationModule;
import com.busytrack.foodtruckclient.di.activity.mvp.MvpModule;
import com.busytrack.foodtruckclient.di.activity.permission.PermissionModule;
import com.busytrack.foodtruckclient.di.activity.viewmodel.ViewModelModule;
import com.busytrack.foodtruckclient.screen.dashboard.DashboardFragment;
import com.busytrack.foodtruckclient.screen.foodtruckeditor.FoodtruckEditorFragment;
import com.busytrack.foodtruckclient.screen.foodtruckviewer.FoodtruckViewerFragment;
import com.busytrack.foodtruckclient.screen.login.LoginFragment;
import com.busytrack.foodtruckclient.screen.profile.ProfileFragment;
import com.busytrack.foodtruckclient.screen.register.RegisterFragment;

import dagger.Subcomponent;

@ActivityScope
@Subcomponent(modules = {
        ActivityModule.class,
        LocationModule.class,
        PermissionModule.class,
        ViewModelModule.class,
        MvpModule.class,
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
