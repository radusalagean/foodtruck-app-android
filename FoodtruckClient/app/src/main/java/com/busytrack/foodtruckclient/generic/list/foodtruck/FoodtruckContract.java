package com.busytrack.foodtruckclient.generic.list.foodtruck;

import com.busytrack.foodtruckclient.network.foodtruckapi.model.Foodtruck;

/**
 * Interface that links foodtruck list items with the outside fragment
 */
public interface FoodtruckContract {

    void onFoodtruckSelected(Foodtruck foodtruck);
    void onFoodtruckLocationButtonClicked(Foodtruck foodtruck);
}
