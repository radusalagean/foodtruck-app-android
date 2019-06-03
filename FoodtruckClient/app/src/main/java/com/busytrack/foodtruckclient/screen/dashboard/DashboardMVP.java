package com.busytrack.foodtruckclient.screen.dashboard;

import com.busytrack.foodtruckclient.generic.mapmvp.BaseMapMVP;
import com.busytrack.foodtruckclient.network.foodtruckapi.model.Foodtruck;

import java.util.List;

import io.reactivex.Observable;

public interface DashboardMVP {

    interface Model extends BaseMapMVP.Model<DashboardViewModel> {
        Observable<DashboardViewModel> getViewModel();
        Observable<List<Foodtruck>> getFoodtrucks();
    }

    interface View extends BaseMapMVP.View {
        void updateFoodtrucks(List<Foodtruck> foodtrucks);
        void switchToMapTab();
    }

    interface Presenter extends BaseMapMVP.Presenter<View> {
        void loadViewModel();
        void reloadFoodtrucks();
    }
}
