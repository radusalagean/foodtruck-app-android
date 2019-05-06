package com.example.foodtruckclient.dashboard;

public interface DashboardListListener {

    void onFoodtruckSelected(DashboardFoodtruckViewModel model);
    void onFoodtruckLocationButtonClicked(DashboardFoodtruckViewModel model);
}
