package com.example.foodtruckclient.di.application;

import com.example.foodtruckclient.di.application.network.FoodtruckApiModule;
import com.example.foodtruckclient.di.application.repository.RepositoryModule;
import com.example.foodtruckclient.di.presentation.PresentationComponent;
import com.example.foodtruckclient.di.presentation.PresentationModule;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = {
        ApplicationModule.class,
        FoodtruckApiModule.class,
        RepositoryModule.class
})
public interface ApplicationComponent {

    PresentationComponent newPresentationComponent(PresentationModule presentationModule);
}
