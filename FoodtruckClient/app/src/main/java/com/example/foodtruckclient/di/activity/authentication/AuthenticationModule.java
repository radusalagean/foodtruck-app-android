package com.example.foodtruckclient.di.activity.authentication;

import com.example.foodtruckclient.authentication.AuthenticationBroadcastReceiver;
import com.example.foodtruckclient.authentication.AuthenticationRepository;
import com.example.foodtruckclient.di.activity.ActivityScope;
import com.example.foodtruckclient.generic.activity.ActivityContract;

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
