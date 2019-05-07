package com.example.foodtruckclient.dashboard;

import com.example.foodtruckclient.dashboard.viewmodel.DashboardViewModel;
import com.example.foodtruckclient.generic.mvp.BaseModel;
import com.example.foodtruckclient.generic.mvp.BasePresenter;
import com.example.foodtruckclient.generic.mvp.BaseView;
import com.example.foodtruckclient.network.foodtruckapi.model.Foodtruck;
import com.example.foodtruckclient.permission.PermissionRequestDelegate;
import com.google.android.gms.maps.OnMapReadyCallback;

import java.util.List;

import io.reactivex.Observable;

public interface DashboardMVP {

    interface Model extends BaseModel<DashboardViewModel> {
        Observable<List<Foodtruck>> getFoodtrucks();
    }

    interface View extends BaseView {
        void updateFoodtrucks(List<Foodtruck> foodtrucks);
        void clearFoodtrucks();
        PermissionRequestDelegate getPermissionRequestDelegate();
        void switchToMapTab();
    }

    interface Presenter extends BasePresenter<View> {
        void loadViewModel();
        void reloadFoodtrucks();
        void disposeMap();
        OnMapReadyCallback getOnMapReadyCallback();
        void zoomOnCurrentDeviceLocation();
        void zoomOnLocation(double latitude, double longitude);
        void viewFoodtruck(String id, String name);
    }
}
