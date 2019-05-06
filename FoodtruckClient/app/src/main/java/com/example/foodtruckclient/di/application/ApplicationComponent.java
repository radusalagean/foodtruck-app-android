package com.example.foodtruckclient.di.application;

import com.example.foodtruckclient.di.application.network.FoodtruckApiModule;
import com.example.foodtruckclient.di.application.repository.RepositoryModule;
import com.example.foodtruckclient.di.activity.ActivityComponent;
import com.example.foodtruckclient.di.activity.ActivityModule;

import dagger.Component;

@ApplicationScope
@Component(modules = {
        ApplicationModule.class,
        FoodtruckApiModule.class,
        RepositoryModule.class
})
public interface ApplicationComponent {

    ActivityComponent newActivityComponent(ActivityModule activityModule);
}
