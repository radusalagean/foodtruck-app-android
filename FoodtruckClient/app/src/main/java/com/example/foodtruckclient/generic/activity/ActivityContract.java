package com.example.foodtruckclient.generic.activity;

import androidx.annotation.IdRes;
import androidx.annotation.NonNull;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.FragmentManager;

import com.example.foodtruckclient.network.foodtruckapi.model.Account;

import javax.annotation.Nullable;

public interface ActivityContract {

    void setActionBar(@NonNull Toolbar toolbar);

    /**
     * Override to specify the container id for Fragments
     */
    @IdRes int getFragmentContainerId();

    FragmentManager getFragmentManagerCompat();

    void setAuthenticatedAccount(@NonNull Account account);

    void clearAuthenticatedAccount();

    boolean isUserAuthenticated();

    @Nullable String getAuthenticatedUserId();

    void showSnackBar(String message);

    void popAllFragments();

    void invalidateDashboard();

    void validateDashboard();

    boolean isDashboardInvalidated();
}
