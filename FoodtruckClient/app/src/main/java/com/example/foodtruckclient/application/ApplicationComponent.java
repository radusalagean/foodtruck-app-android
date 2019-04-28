package com.example.foodtruckclient.application;

import com.example.foodtruckclient.activity.ActivityBuilder;
import com.example.foodtruckclient.network.foodtruckapi.FoodtruckApiModule;

import javax.inject.Singleton;

import dagger.Component;
import dagger.android.AndroidInjector;
import dagger.android.support.AndroidSupportInjectionModule;

@Singleton
@Component(modules = {
        AndroidSupportInjectionModule.class,
        ActivityBuilder.class,
        ApplicationModule.class,
        FoodtruckApiModule.class
})
public interface ApplicationComponent extends AndroidInjector<FoodtruckApplication> {

    @Component.Factory
    interface Factory extends AndroidInjector.Factory<FoodtruckApplication> {}
}
