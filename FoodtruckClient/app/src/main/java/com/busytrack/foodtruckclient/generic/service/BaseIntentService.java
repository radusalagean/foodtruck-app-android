package com.busytrack.foodtruckclient.generic.service;

import android.app.IntentService;
import android.content.Context;
import android.content.Intent;

import androidx.annotation.UiThread;

import com.busytrack.foodtruckclient.application.FoodtruckApplication;
import com.busytrack.foodtruckclient.authentication.AuthenticationService;
import com.busytrack.foodtruckclient.di.service.ServiceComponent;
import com.busytrack.foodtruckclient.di.service.ServiceModule;

import timber.log.Timber;

public abstract class BaseIntentService extends IntentService {

    private String tag = getClass().getSimpleName();
    private boolean isComponentUsed = false;

    public BaseIntentService(String name) {
        super(name);
    }

    public static void startService(Context context, String intentAction) {
        context.startService(new Intent(intentAction, null, context, AuthenticationService.class));
    }

    @Override
    public void onCreate() {
        Timber.tag(tag).v("-S-> onCreate()");
        super.onCreate();
    }

    @Override
    public void onDestroy() {
        Timber.tag(tag).v("-S-> onDestroy()");
        super.onDestroy();
    }

    @UiThread
    protected ServiceComponent getControllerComponent() {
        if (isComponentUsed) {
            throw new IllegalStateException("You shouldn't use ServiceComponent more than once");
        }
        isComponentUsed = true;
        return ((FoodtruckApplication) getApplication())
                .getApplicationComponent()
                .newServiceComponent(new ServiceModule(this));
    }
}
