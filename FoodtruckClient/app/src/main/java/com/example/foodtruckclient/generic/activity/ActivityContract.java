package com.example.foodtruckclient.generic.activity;

import androidx.annotation.IdRes;
import androidx.annotation.NonNull;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.FragmentManager;

public interface ActivityContract {

    void setActionBar(@NonNull Toolbar toolbar);

    /**
     * Override to specify the container id for Fragments
     */
    @IdRes int getFragmentContainerId();

    FragmentManager getFragmentManagerCompat();
}
