package com.example.foodtruckclient.generic.activity;

import androidx.annotation.IdRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.FragmentManager;

import com.example.foodtruckclient.network.foodtruckapi.model.Account;

import java.util.Date;

public interface ActivityContract {

    void setActionBar(@NonNull Toolbar toolbar);

    /**
     * Override to specify the container id for Fragments
     */
    @IdRes int getFragmentContainerId();

    FragmentManager getFragmentManagerCompat();

    void setAuthenticatedAccount(@NonNull Account account);

    void clearAuthenticatedAccount();

    void setAuthenticatedAccountImage(@Nullable String imageUrl, Date lastUpdate);

    boolean isUserAuthenticated();

    @Nullable String getAuthenticatedUserId();

    void showSnackBar(String message);

    void popAllFragments();

    void invalidateDashboard();

    void validateDashboard();

    boolean isDashboardInvalidated();

    void showFoodtruckViewerScreen(String foodtruckId, String foodtruckName);

    void showLoginScreen();

    void showRegisterScreen();

    void showProfileScreen(String profileId, String profileName);
}
