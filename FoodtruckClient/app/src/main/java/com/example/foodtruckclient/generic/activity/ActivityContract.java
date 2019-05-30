package com.example.foodtruckclient.generic.activity;

import androidx.annotation.IdRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.FragmentManager;

import com.example.foodtruckclient.network.foodtruckapi.model.Account;
import com.example.foodtruckclient.network.foodtruckapi.model.Foodtruck;

public interface ActivityContract {

    @Nullable ActionBar getToolbar();

    void setToolbar(@NonNull Toolbar toolbar);

    /**
     * Override to specify the container id for Fragments
     */
    @IdRes int getFragmentContainerId();

    FragmentManager getFragmentManagerCompat();

    void setAuthenticatedAccount(@NonNull Account account, boolean notifyUser);

    void clearAuthenticatedAccount();

    boolean isUserAuthenticated();

    @Nullable String getAuthenticatedUserId();

    void showSnackBar(String message);

    void popAllFragments();

    void showDashboardScreen();

    void showFoodtruckViewerScreen(String foodtruckId, String foodtruckName);

    void showLoginScreen();

    void showRegisterScreen();

    void showProfileScreen(String profileId, String profileName);

    void showFoodtruckEditorScreen();

    void showFoodtruckEditorScreen(Foodtruck foodtruck);
}
