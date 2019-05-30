package com.example.foodtruckclient.di.activity.viewmodel;

import android.content.Context;

import com.example.foodtruckclient.authentication.AuthenticationRepository;
import com.example.foodtruckclient.di.activity.ActivityScope;
import com.example.foodtruckclient.dialog.DialogManager;
import com.example.foodtruckclient.generic.viewmodel.ViewModelManager;
import com.example.foodtruckclient.location.LocationManager;
import com.example.foodtruckclient.network.NetworkRepository;
import com.example.foodtruckclient.permission.PermissionManager;
import com.example.foodtruckclient.screen.dashboard.DashboardMVP;
import com.example.foodtruckclient.screen.dashboard.DashboardModel;
import com.example.foodtruckclient.screen.dashboard.DashboardPresenter;
import com.example.foodtruckclient.screen.dashboard.DashboardViewModelRepository;
import com.example.foodtruckclient.screen.foodtruckeditor.FoodtruckEditorMVP;
import com.example.foodtruckclient.screen.foodtruckeditor.FoodtruckEditorModel;
import com.example.foodtruckclient.screen.foodtruckeditor.FoodtruckEditorPresenter;
import com.example.foodtruckclient.screen.foodtruckeditor.FoodtruckEditorViewModelRepository;
import com.example.foodtruckclient.screen.foodtruckviewer.FoodtruckViewerMVP;
import com.example.foodtruckclient.screen.foodtruckviewer.FoodtruckViewerModel;
import com.example.foodtruckclient.screen.foodtruckviewer.FoodtruckViewerPresenter;
import com.example.foodtruckclient.screen.foodtruckviewer.FoodtruckViewerViewModelRepository;
import com.example.foodtruckclient.screen.login.LoginMVP;
import com.example.foodtruckclient.screen.login.LoginModel;
import com.example.foodtruckclient.screen.login.LoginPresenter;
import com.example.foodtruckclient.screen.profile.ProfileMVP;
import com.example.foodtruckclient.screen.profile.ProfileModel;
import com.example.foodtruckclient.screen.profile.ProfilePresenter;
import com.example.foodtruckclient.screen.profile.ProfileViewModelRepository;
import com.example.foodtruckclient.screen.register.RegisterMVP;
import com.example.foodtruckclient.screen.register.RegisterModel;
import com.example.foodtruckclient.screen.register.RegisterPresenter;

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
