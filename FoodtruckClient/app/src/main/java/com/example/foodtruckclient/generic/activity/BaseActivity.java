package com.example.foodtruckclient.generic.activity;

import android.os.Bundle;

import androidx.annotation.IdRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.UiThread;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.example.foodtruckclient.application.FoodtruckApplication;
import com.example.foodtruckclient.di.presentation.PresentationComponent;
import com.example.foodtruckclient.di.presentation.PresentationModule;
import com.example.foodtruckclient.generic.fragment.BaseFragment;
import com.example.foodtruckclient.util.ActivityUtils;

import timber.log.Timber;

public abstract class BaseActivity extends AppCompatActivity {

    private String tag = getClass().getSimpleName();
    private boolean isComponentUsed = false;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        Timber.tag(tag).v("-A-> onCreate(%s)", savedInstanceState);
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void onStart() {
        Timber.tag(tag).v("-A-> onStart()");
        super.onStart();
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        Timber.tag(tag).v("-A-> onRestoreInstanceState(%s)", savedInstanceState);
        super.onRestoreInstanceState(savedInstanceState);
    }

    @Override
    protected void onResume() {
        Timber.tag(tag).v("-A-> onResume()");
        super.onResume();
    }

    @Override
    protected void onPause() {
        Timber.tag(tag).v("-A-> onPause()");
        super.onPause();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        Timber.tag(tag).v("-A-> onSaveInstanceState(%s)", outState);
        super.onSaveInstanceState(outState);
    }


    @Override
    protected void onStop() {
        Timber.tag(tag).v("-A-> onStop()");
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        Timber.tag(tag).v("-A-> onDestroy()");
        super.onDestroy();
    }

    /**
     * Adds a default fragment if no fragment is present for the specified container
     * @param containerId
     */
    protected void addDefaultFragmentIfNecessary(@IdRes int containerId) {
        Fragment currentFragment = getSupportFragmentManager().findFragmentById(containerId);
        if (currentFragment == null) {
            BaseFragment defaultFragment = getDefaultFragment();
            Timber.d("No fragment was previously attached for container id %d, attaching %s as starting point", containerId, defaultFragment);
            ActivityUtils.addFragmentToActivity(getSupportFragmentManager(), defaultFragment, containerId);
        }
    }

    /**
     * Override to specify the default fragment to be added with the {@link #addDefaultFragmentIfNecessary(int)} method
     */
    protected abstract @NonNull BaseFragment getDefaultFragment();

    @UiThread
    protected PresentationComponent getControllerComponent() {
        if (isComponentUsed) {
            throw new IllegalStateException("You shouldn't use PresentationComponent more than once");
        }
        isComponentUsed = true;
        return ((FoodtruckApplication) getApplication())
                .getApplicationComponent()
                .newPresentationComponent(new PresentationModule(this));
    }
}
