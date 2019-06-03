package com.busytrack.foodtruckclient.di.service;

import android.app.Service;
import android.content.Context;

import dagger.Module;
import dagger.Provides;

@Module
public class ServiceModule {

    private final Service service;

    public ServiceModule(Service service) {
        this.service = service;
    }

    @Provides
    Service provideService() {
        return service;
    }

    @Provides
    Context provideContext() {
        return service;
    }
}
