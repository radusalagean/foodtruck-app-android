package com.example.foodtruckclient.application;

import com.facebook.stetho.Stetho;

import timber.log.Timber;

public class FoodtruckDebugApplication extends FoodtruckApplication {

    @Override
    public void onCreate() {
        super.onCreate();
        Timber.plant(new Timber.DebugTree());
        Stetho.initializeWithDefaults(this);
    }
}
