package com.busytrack.foodtruckclient.generic.activity;

import androidx.annotation.IdRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.widget.Toolbar;

import com.busytrack.foodtruckclient.network.foodtruckapi.model.Account;
import com.busytrack.foodtruckclient.network.foodtruckapi.model.Foodtruck;

/**
 * An interface that provides Activity-related functionality
 */
public interface ActivityContract {

    @Nullable ActionBar getToolbar();

    void setToolbar(@NonNull Toolbar toolbar);

    /**
     * Override to specify the container id for Fragments
     */
    @IdRes int getFragmentContainerId();

    /**
     * Set the account of the currently authenticated user
     *
     * @param account the account of the authenticated user
     * @param notifyUser true if a message should be displayed, false otherwise
     */
    void setAuthenticatedAccount(@NonNull Account account, boolean notifyUser);

    /**
     * Clear the authenticated account from RAM and persistent storage
     */
    void clearAuthenticatedAccount();

    /**
     * @return true if a user is currently logged in, false otherwise
     */
    boolean isUserAuthenticated();

    @Nullable String getAuthenticatedUserId();

    /**
     * Show a temporary message on the screen
     */
    void showSnackBar(String message);

    /**
     * Clear all fragments from the fragment back stack, up to the default fragment, added
     * automatically on app start
     */
    void popAllFragments();

    void showDashboardScreen();

    void showFoodtruckViewerScreen(String foodtruckId, String foodtruckName);

    void showLoginScreen();

    void showRegisterScreen();

    void showProfileScreen(String profileId, String profileName);

    /**
     * Used for adding a new food truck
     */
    void showFoodtruckEditorScreen();

    /**
     * Used for editing an existing food truck
     */
    void showFoodtruckEditorScreen(Foodtruck foodtruck);
}
