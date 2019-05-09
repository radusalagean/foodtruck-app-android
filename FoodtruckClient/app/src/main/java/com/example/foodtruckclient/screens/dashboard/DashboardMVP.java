package com.example.foodtruckclient.screens.dashboard;

import com.example.foodtruckclient.generic.mvp.BaseMVP;
import com.example.foodtruckclient.network.foodtruckapi.model.Foodtruck;
import com.example.foodtruckclient.permission.PermissionRequestDelegate;
import com.google.android.gms.maps.OnMapReadyCallback;

import java.util.List;

import io.reactivex.Observable;

public interface DashboardMVP {

    interface Model extends BaseMVP.Model<DashboardViewModel> {
        Observable<DashboardViewModel> getViewModel();
        Observable<List<Foodtruck>> getFoodtrucks();
    }

    interface View extends BaseMVP.View {
        void updateFoodtrucks(List<Foodtruck> foodtrucks);
        void clearFoodtrucks();
        PermissionRequestDelegate getPermissionRequestDelegate();
        void switchToMapTab();
    }

    interface Presenter extends BaseMVP.Presenter<View> {
        void loadViewModel();
        void reloadFoodtrucks();
        void disposeMap();
        OnMapReadyCallback getOnMapReadyCallback();
        void zoomOnCurrentDeviceLocation();
        void zoomOnLocation(double latitude, double longitude);
        void viewFoodtruck(String id, String name);
    }
}
