package com.busytrack.foodtruckclient.di.service;

import com.busytrack.foodtruckclient.authentication.AuthenticationService;

import dagger.Subcomponent;

@ServiceScope
@Subcomponent(modules = {
        ServiceModule.class
})
public interface ServiceComponent {

    void inject(AuthenticationService authenticationService);
}
