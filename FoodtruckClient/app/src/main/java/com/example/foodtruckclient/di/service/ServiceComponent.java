package com.example.foodtruckclient.di.service;

import com.example.foodtruckclient.authentication.AuthenticationService;

import dagger.Subcomponent;

@ServiceScope
@Subcomponent(modules = {
        ServiceModule.class
})
public interface ServiceComponent {

    void inject(AuthenticationService authenticationService);
}
