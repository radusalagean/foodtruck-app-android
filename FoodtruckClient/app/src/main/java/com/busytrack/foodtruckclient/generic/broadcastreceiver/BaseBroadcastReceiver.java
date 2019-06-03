package com.busytrack.foodtruckclient.generic.broadcastreceiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;

import androidx.annotation.CallSuper;

import timber.log.Timber;

public abstract class BaseBroadcastReceiver extends BroadcastReceiver {

    @Override
    @CallSuper
    public void onReceive(Context context, Intent intent) {
        Timber.d("onReceive(%s)", intent);
    }

    protected abstract IntentFilter getIntentFilter();

    public void register(Context context) {
        context.registerReceiver(this, getIntentFilter());
    }

    public void unregister(Context context) {
        context.unregisterReceiver(this);
    }
}
