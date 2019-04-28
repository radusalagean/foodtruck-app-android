package com.example.foodtruckclient.application;

import dagger.android.AndroidInjector;
import dagger.android.DaggerApplication;

public class FoodtruckApplication extends DaggerApplication {

    @Override
    protected AndroidInjector<? extends DaggerApplication> applicationInjector() {
        return DaggerApplicationComponent.factory().create(this);
    }
}
