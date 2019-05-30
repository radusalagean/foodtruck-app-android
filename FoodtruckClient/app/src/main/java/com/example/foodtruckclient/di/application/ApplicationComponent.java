package com.example.foodtruckclient.di.application;

import com.example.foodtruckclient.di.application.network.FoodtruckApiModule;
import com.example.foodtruckclient.di.application.repository.RepositoryModule;
import com.example.foodtruckclient.di.activity.ActivityComponent;
import com.example.foodtruckclient.di.activity.ActivityModule;
import com.example.foodtruckclient.di.service.ServiceComponent;
import com.example.foodtruckclient.di.service.ServiceModule;

import dagger.Component;

@ApplicationScope
@Component(modules = {
        ApplicationModule.class,
        FoodtruckApiModule.class,
        RepositoryModule.class
})
public interface ApplicationComponent {

    ActivityComponent newActivityComponent(ActivityModule activityModule);
    ServiceComponent newServiceComponent(ServiceModule serviceModule);
}
