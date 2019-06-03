package com.busytrack.foodtruckclient.application;

import androidx.appcompat.app.AppCompatDelegate;
import androidx.multidex.MultiDexApplication;

import com.busytrack.foodtruckclient.di.application.ApplicationComponent;
import com.busytrack.foodtruckclient.di.application.ApplicationModule;
import com.busytrack.foodtruckclient.di.application.DaggerApplicationComponent;
import com.busytrack.foodtruckclient.di.application.network.FoodtruckApiModule;
import com.busytrack.foodtruckclient.di.application.repository.RepositoryModule;

public class FoodtruckApplication extends MultiDexApplication {

    private ApplicationComponent applicationComponent;

    @Override
    public void onCreate() {
        super.onCreate();
        AppCompatDelegate.setCompatVectorFromResourcesEnabled(true);
    }

    public ApplicationComponent getApplicationComponent() {
        if (applicationComponent == null) {
            applicationComponent = DaggerApplicationComponent.builder()
                    .applicationModule(new ApplicationModule(this))
                    .foodtruckApiModule(new FoodtruckApiModule())
                    .repositoryModule(new RepositoryModule())
                    .build();
        }
        return applicationComponent;
    }
}
