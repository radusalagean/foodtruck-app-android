package com.busytrack.foodtruckclient.di.activity.authentication;

import com.busytrack.foodtruckclient.authentication.AuthenticationBroadcastReceiver;
import com.busytrack.foodtruckclient.authentication.AuthenticationRepository;
import com.busytrack.foodtruckclient.di.activity.ActivityScope;
import com.busytrack.foodtruckclient.generic.activity.ActivityContract;

import dagger.Module;
import dagger.Provides;

@Module
public class AuthenticationModule {

    @Provides
    @ActivityScope
    AuthenticationBroadcastReceiver provideAuthenticationProadcastReceiver(
            AuthenticationRepository authenticationRepository,
            ActivityContract activityContract) {
        return new AuthenticationBroadcastReceiver(authenticationRepository, activityContract);
    }
}
