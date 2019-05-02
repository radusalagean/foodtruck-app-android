package com.example.foodtruckclient.di.presentation.permission;

import android.content.Context;

import com.example.foodtruckclient.permission.PermissionManager;

import dagger.Module;
import dagger.Provides;

@Module
public class PermissionModule {

    @Provides
    PermissionManager providePermissionManager(Context context) {
        return new PermissionManager(context);
    }
}
