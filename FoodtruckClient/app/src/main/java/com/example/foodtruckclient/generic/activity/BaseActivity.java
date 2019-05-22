package com.example.foodtruckclient.generic.activity;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.UiThread;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.foodtruckclient.application.FoodtruckApplication;
import com.example.foodtruckclient.di.activity.ActivityComponent;
import com.example.foodtruckclient.di.activity.ActivityModule;
import com.example.foodtruckclient.generic.fragment.BaseFragment;

import timber.log.Timber;

public abstract class BaseActivity extends AppCompatActivity implements ActivityContract {

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

    @Override
    public void setActionBar(@NonNull Toolbar toolbar) {
        setSupportActionBar(toolbar);
    }

    @Override
    public FragmentManager getFragmentManagerCompat() {
        return getSupportFragmentManager();
    }

    /**
     * Adds a default fragment if no fragment is present for the specified container
     */
    protected void addDefaultFragmentIfNecessary() {
        Fragment currentFragment = getSupportFragmentManager().findFragmentById(getFragmentContainerId());
        if (currentFragment == null) {
            BaseFragment defaultFragment = getDefaultFragment();
            Timber.d("No fragment was previously attached, attaching %s as starting point", defaultFragment);
            showFragment(defaultFragment, false, null);
        }
    }

    /**
     * Override to specify the default fragment to be added with the {@link #addDefaultFragmentIfNecessary()} method
     */
    protected abstract @NonNull BaseFragment getDefaultFragment();

    @UiThread
    protected ActivityComponent getControllerComponent() {
        if (isComponentUsed) {
            throw new IllegalStateException("You shouldn't use ActivityComponent more than once");
        }
        isComponentUsed = true;
        return ((FoodtruckApplication) getApplication())
                .getApplicationComponent()
                .newActivityComponent(new ActivityModule(this));
    }

    protected <T extends BaseFragment> void showFragment(T fragment) {
        showFragment(fragment, true, null);
    }

    protected <T extends BaseFragment> void showFragment(T fragment,
                                                         boolean addToBackStack,
                                                         @Nullable String backStackStateName) {
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction()
                .replace(getFragmentContainerId(), fragment, fragment.getUuid().toString())
                .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
        if (addToBackStack) {
            ft.addToBackStack(backStackStateName);
        }
        ft.commit();
    }
}
