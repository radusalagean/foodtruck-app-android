package com.busytrack.foodtruckclient.di.activity.dialog;

import android.content.Context;

import com.busytrack.foodtruckclient.di.activity.ActivityScope;
import com.busytrack.foodtruckclient.dialog.DialogManager;

import dagger.Module;
import dagger.Provides;

@Module
public class DialogModule {

    @Provides
    @ActivityScope
    DialogManager provideDialogManager(Context context) {
        return new DialogManager(context);
    }
}
