package com.example.foodtruckclient.dashboard;

import androidx.annotation.NonNull;

import com.example.foodtruckclient.generic.mvp.BasePresenter;
import com.example.foodtruckclient.permission.PermissionRequestDelegate;
import com.google.android.gms.maps.OnMapReadyCallback;

import java.util.List;

import io.reactivex.Observable;

public interface DashboardMVP {

    interface Model {
        Observable<List<DashboardFoodtruckViewModel>> getResults();
    }

    interface View {
        void updateFoodtrucks(List<DashboardFoodtruckViewModel> results);
        PermissionRequestDelegate getPermissionRequestDelegate();
        void switchToMapTab();
    }

    interface Presenter extends BasePresenter<View> {
        void loadFoodtrucks();
        void disposeMap();
        OnMapReadyCallback getOnMapReadyCallback();
        void zoomOnCurrentDeviceLocation();
        void zoomOnLocation(double latitude, double longitude);
        void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults);
    }
}
