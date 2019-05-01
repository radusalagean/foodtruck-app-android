package com.example.foodtruckclient.di.presentation;

import com.example.foodtruckclient.activity.MainActivity;
import com.example.foodtruckclient.dashboard.DashboardFragment;

import dagger.Subcomponent;

@Subcomponent(modules = {
        PresentationModule.class
})
public interface PresentationComponent {

    void inject(MainActivity activity);
    void inject(DashboardFragment fragment);
}
