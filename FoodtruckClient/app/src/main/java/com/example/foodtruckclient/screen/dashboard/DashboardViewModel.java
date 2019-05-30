package com.example.foodtruckclient.screen.dashboard;

import com.example.foodtruckclient.generic.contentinvalidation.ContentType;
import com.example.foodtruckclient.generic.contentinvalidation.InvalidationBundle;
import com.example.foodtruckclient.generic.contentinvalidation.InvalidationEffect;
import com.example.foodtruckclient.generic.viewmodel.BaseViewModel;
import com.example.foodtruckclient.network.foodtruckapi.model.Foodtruck;

import java.util.List;

public class DashboardViewModel extends BaseViewModel {

    private List<Foodtruck> foodtrucks;

    private DashboardViewModel(List<Foodtruck> foodtrucks) {
        this.foodtrucks = foodtrucks;
    }

    public List<Foodtruck> getFoodtrucks() {
        return foodtrucks;
    }

    public void setFoodtrucks(List<Foodtruck> foodtrucks) {
        this.foodtrucks = foodtrucks;
    }

    public static DashboardViewModel createFrom(List<Foodtruck> foodtrucks) {
        return new DashboardViewModel(foodtrucks);
    }

    @Override
    public void processInvalidationBundle(InvalidationBundle invalidationBundle) {
        if ((invalidationBundle.getContentType() &
                (ContentType.FOODTRUCK | ContentType.REVIEW)) != 0) {
            invalidationEffects |= InvalidationEffect.FOODTRUCK_RELOAD;
        }
    }
}
