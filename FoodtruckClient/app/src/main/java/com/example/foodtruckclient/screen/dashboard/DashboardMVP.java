package com.example.foodtruckclient.screen.dashboard;

import com.example.foodtruckclient.generic.mapmvp.BaseMapMVP;
import com.example.foodtruckclient.network.foodtruckapi.model.Foodtruck;

import java.util.List;

import io.reactivex.Observable;

public interface DashboardMVP {

    interface Model extends BaseMapMVP.Model<DashboardViewModel> {
        Observable<DashboardViewModel> getViewModel();
        Observable<List<Foodtruck>> getFoodtrucks();
    }

    interface View extends BaseMapMVP.View {
        void updateFoodtrucks(List<Foodtruck> foodtrucks);
        void clearFoodtrucks();
        void switchToMapTab();
    }

    interface Presenter extends BaseMapMVP.Presenter<View> {
        void loadViewModel();
        void reloadFoodtrucks();
    }
}
