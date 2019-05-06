package com.example.foodtruckclient.di.activity.permission;

import android.content.Context;

import com.example.foodtruckclient.di.activity.ActivityScope;
import com.example.foodtruckclient.permission.PermissionManager;

import dagger.Module;
import dagger.Provides;

@Module
public class PermissionModule {

    @Provides
    @ActivityScope
    PermissionManager providePermissionManager(Context context) {
        return new PermissionManager(context);
    }
}
