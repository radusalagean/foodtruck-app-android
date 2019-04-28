package com.example.foodtruckclient.application;

import android.app.Application;
import android.content.Context;

import dagger.Binds;
import dagger.Module;

@Module
public abstract class ApplicationModule {

    @Binds
    abstract Application provideApplication(FoodtruckApplication foodtruckApplication);

    @Binds
    abstract Context provideContext(Application application);
}
