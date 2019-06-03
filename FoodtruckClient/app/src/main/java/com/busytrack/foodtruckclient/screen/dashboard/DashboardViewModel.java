package com.busytrack.foodtruckclient.screen.dashboard;

import com.busytrack.foodtruckclient.generic.contentinvalidation.ContentType;
import com.busytrack.foodtruckclient.generic.contentinvalidation.InvalidationBundle;
import com.busytrack.foodtruckclient.generic.contentinvalidation.InvalidationEffect;
import com.busytrack.foodtruckclient.generic.viewmodel.BaseViewModel;
import com.busytrack.foodtruckclient.network.foodtruckapi.model.Foodtruck;

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
