package com.busytrack.foodtruckclient.di.activity.mvp;

import android.content.Context;

import com.busytrack.foodtruckclient.authentication.AuthenticationRepository;
import com.busytrack.foodtruckclient.dialog.DialogManager;
import com.busytrack.foodtruckclient.generic.viewmodel.ViewModelManager;
import com.busytrack.foodtruckclient.location.LocationManager;
import com.busytrack.foodtruckclient.network.NetworkRepository;
import com.busytrack.foodtruckclient.permission.PermissionManager;
import com.busytrack.foodtruckclient.screen.dashboard.DashboardMVP;
import com.busytrack.foodtruckclient.screen.dashboard.DashboardModel;
import com.busytrack.foodtruckclient.screen.dashboard.DashboardPresenter;
import com.busytrack.foodtruckclient.screen.dashboard.DashboardViewModelRepository;
import com.busytrack.foodtruckclient.screen.foodtruckeditor.FoodtruckEditorMVP;
import com.busytrack.foodtruckclient.screen.foodtruckeditor.FoodtruckEditorModel;
import com.busytrack.foodtruckclient.screen.foodtruckeditor.FoodtruckEditorPresenter;
import com.busytrack.foodtruckclient.screen.foodtruckeditor.FoodtruckEditorViewModelRepository;
import com.busytrack.foodtruckclient.screen.foodtruckviewer.FoodtruckViewerMVP;
import com.busytrack.foodtruckclient.screen.foodtruckviewer.FoodtruckViewerModel;
import com.busytrack.foodtruckclient.screen.foodtruckviewer.FoodtruckViewerPresenter;
import com.busytrack.foodtruckclient.screen.foodtruckviewer.FoodtruckViewerViewModelRepository;
import com.busytrack.foodtruckclient.screen.login.LoginMVP;
import com.busytrack.foodtruckclient.screen.login.LoginModel;
import com.busytrack.foodtruckclient.screen.login.LoginPresenter;
import com.busytrack.foodtruckclient.screen.profile.ProfileMVP;
import com.busytrack.foodtruckclient.screen.profile.ProfileModel;
import com.busytrack.foodtruckclient.screen.profile.ProfilePresenter;
import com.busytrack.foodtruckclient.screen.profile.ProfileViewModelRepository;
import com.busytrack.foodtruckclient.screen.register.RegisterMVP;
import com.busytrack.foodtruckclient.screen.register.RegisterModel;
import com.busytrack.foodtruckclient.screen.register.RegisterPresenter;

import dagger.Module;
import dagger.Provides;

/**
 * Module that is responsible for providing the Model and Presenter classes to the Views (Fragments
 * in our case), for each screen type
 */
@Module
public class MvpModule {

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
                                                     DialogManager dialogManager) {
        return new DashboardPresenter(
                model, locationManager, permissionManager, dialogManager
        );
    }

    // Foodtruck Viewer

    @Provides
    FoodtruckViewerMVP.Model provideFoodtruckViewerModel(NetworkRepository networkRepository,
                                                         FoodtruckViewerViewModelRepository viewModelRepository) {
        return new FoodtruckViewerModel(networkRepository, viewModelRepository);
    }

    @Provides
    FoodtruckViewerMVP.Presenter provideFoodtruckViewerPresenter(FoodtruckViewerMVP.Model model,
                                                                 LocationManager locationManager,
                                                                 PermissionManager permissionManager,
                                                                 DialogManager dialogManager,
                                                                 ViewModelManager viewModelManager) {
        return new FoodtruckViewerPresenter(
                model, locationManager, permissionManager, dialogManager, viewModelManager
        );
    }

    // Login

    @Provides
    LoginMVP.Model provideLoginModel(NetworkRepository networkRepository,
                                     AuthenticationRepository authenticationRepository) {
        return new LoginModel(networkRepository, authenticationRepository);
    }

    @Provides
    LoginMVP.Presenter provideLoginPresenter(LoginMVP.Model model,
                                             DialogManager dialogManager) {
        return new LoginPresenter(model, dialogManager);
    }

    // Register

    @Provides
    RegisterMVP.Model provideRegisterModel(NetworkRepository networkRepository) {
        return new RegisterModel(networkRepository);
    }

    @Provides
    RegisterMVP.Presenter provideRegisterPresenter(RegisterMVP.Model model) {
        return new RegisterPresenter(model);
    }

    // Profile

    @Provides
    ProfileMVP.Model provideProfileModel(NetworkRepository networkRepository,
                                         ProfileViewModelRepository viewModelRepository) {
        return new ProfileModel(networkRepository, viewModelRepository);
    }

    @Provides
    ProfileMVP.Presenter provideProfilePresenter(ProfileMVP.Model model,
                                                 PermissionManager permissionManager,
                                                 DialogManager dialogManager,
                                                 ViewModelManager viewModelManager,
                                                 Context context) {
        return new ProfilePresenter(
                model, permissionManager, dialogManager, viewModelManager, context
        );
    }

    // Foodtruck Editor

    @Provides
    FoodtruckEditorMVP.Model provideFoodtruckEditorModel(NetworkRepository networkRepository,
                                                         FoodtruckEditorViewModelRepository viewModelRepository) {
        return new FoodtruckEditorModel(networkRepository, viewModelRepository);
    }

    @Provides
    FoodtruckEditorMVP.Presenter provideFoodtruckEditorPresenter(FoodtruckEditorMVP.Model model,
                                                                 LocationManager locationManager,
                                                                 PermissionManager permissionManager,
                                                                 DialogManager dialogManager,
                                                                 ViewModelManager viewModelManager) {
        return new FoodtruckEditorPresenter(
                model, locationManager, permissionManager, dialogManager, viewModelManager
        );
    }
}
