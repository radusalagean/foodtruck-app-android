package com.busytrack.foodtruckclient.application;

import androidx.appcompat.app.AppCompatDelegate;
import androidx.multidex.MultiDexApplication;

import com.busytrack.foodtruckclient.di.application.ApplicationComponent;
import com.busytrack.foodtruckclient.di.application.ApplicationModule;
import com.busytrack.foodtruckclient.di.application.DaggerApplicationComponent;

public class FoodtruckApplication extends MultiDexApplication {

    private ApplicationComponent applicationComponent;

    @Override
    public void onCreate() {
        super.onCreate();
        AppCompatDelegate.setCompatVectorFromResourcesEnabled(true);
    }

    /**
     * @return the {@link ApplicationComponent} instance, the root component used for
     * dependency injection
     */
    public ApplicationComponent getApplicationComponent() {
        if (applicationComponent == null) {
            // Note: Modules with empty constructor parameters are added automatically
            applicationComponent = DaggerApplicationComponent.builder()
                    .applicationModule(new ApplicationModule(this))
                    .build();
        }
        return applicationComponent;
    }
}
