package com.busytrack.foodtruckclient.di.application;

import com.busytrack.foodtruckclient.di.activity.ActivityComponent;
import com.busytrack.foodtruckclient.di.activity.ActivityModule;
import com.busytrack.foodtruckclient.di.application.network.FoodtruckApiModule;
import com.busytrack.foodtruckclient.di.application.repository.RepositoryModule;
import com.busytrack.foodtruckclient.di.service.ServiceComponent;
import com.busytrack.foodtruckclient.di.service.ServiceModule;

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
