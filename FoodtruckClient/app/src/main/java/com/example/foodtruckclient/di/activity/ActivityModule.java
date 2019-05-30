package com.example.foodtruckclient.di.activity;

import android.content.Context;

import androidx.fragment.app.FragmentActivity;

import com.example.foodtruckclient.dialog.DialogManager;
import com.example.foodtruckclient.generic.activity.ActivityContract;

import dagger.Module;
import dagger.Provides;

@Module
public class ActivityModule {

    private final FragmentActivity activity;

    public ActivityModule(FragmentActivity activity) {
        this.activity = activity;
    }

    @Provides
    @ActivityScope
    FragmentActivity provideActivity() {
        return activity;
    }

    @Provides
    @ActivityScope
    Context provideContext() {
        return activity;
    }

    @Provides
    @ActivityScope
    ActivityContract provideActivityContract() {
        return (ActivityContract) activity;
    }

    // Dialog

    @Provides
    @ActivityScope
    DialogManager provideDialogManager() {
        return new DialogManager(activity);
    }
}
