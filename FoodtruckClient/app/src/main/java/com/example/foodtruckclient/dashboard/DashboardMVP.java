package com.example.foodtruckclient.dashboard;

import com.example.foodtruckclient.generic.mvp.BasePresenter;

import java.util.List;

import io.reactivex.Observable;

public interface DashboardMVP {

    interface Model {
        Observable<List<DashboardFoodtruckViewModel>> getResults();
    }

    interface View {
        void updateData(List<DashboardFoodtruckViewModel> results);
    }

    interface Presenter extends BasePresenter<View> {
        void loadData();
    }
}
