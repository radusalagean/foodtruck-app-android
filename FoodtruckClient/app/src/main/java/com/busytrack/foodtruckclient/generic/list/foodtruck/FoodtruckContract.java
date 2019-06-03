package com.busytrack.foodtruckclient.generic.list.foodtruck;

import com.busytrack.foodtruckclient.network.foodtruckapi.model.Account;
import com.busytrack.foodtruckclient.network.foodtruckapi.model.Foodtruck;

public interface FoodtruckContract {

    void onFoodtruckSelected(Foodtruck foodtruck);
    void onFoodtruckLocationButtonClicked(Foodtruck foodtruck);
    void onOwnerSelected(Account owner);
}
