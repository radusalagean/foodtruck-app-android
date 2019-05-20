package com.example.foodtruckclient.activity;

import android.os.Bundle;
import android.view.MenuItem;

import androidx.annotation.IdRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.widget.Toolbar;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.foodtruckclient.R;
import com.example.foodtruckclient.authentication.AuthenticationRepository;
import com.example.foodtruckclient.generic.viewmodel.ViewModelManager;
import com.example.foodtruckclient.network.foodtruckapi.model.Account;
import com.example.foodtruckclient.screens.dashboard.DashboardFragment;
import com.example.foodtruckclient.generic.fragment.BaseFragment;
import com.example.foodtruckclient.generic.activity.BaseActivity;
import com.example.foodtruckclient.screens.login.LoginFragment;
import com.example.foodtruckclient.screens.register.RegisterFragment;
import com.example.foodtruckclient.view.AuthenticationNavigationView;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.snackbar.Snackbar;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends BaseActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private static final String BUNDLE_DASHBOARD_INVALIDATED = "dashboard_invalidated";

    @BindView(R.id.drawer_layout)
    DrawerLayout drawerLayout;

    @BindView(R.id.nav_view)
    AuthenticationNavigationView authenticationNavigationView;

    @BindView(R.id.coordinator_layout)
    CoordinatorLayout coordinatorLayout;

    @Inject
    AuthenticationRepository authenticationRepository;

    @Inject
    ViewModelManager viewModelManager;

    private ActionBarDrawerToggle actionBarDrawerToggle;
    private boolean dashboardInvalidated;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        getControllerComponent().inject(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        if (savedInstanceState != null) {
            dashboardInvalidated = savedInstanceState.getBoolean(BUNDLE_DASHBOARD_INVALIDATED, false);
        }
        authenticationNavigationView.setNavigationItemSelectedListener(this);
        addDefaultFragmentIfNecessary();
    }

    @Override
    protected void onStart() {
        super.onStart();
        authenticationNavigationView.setAuthenticatedAccount(authenticationRepository.getAuthenticatedAccount());
        viewModelManager.registerListener(getFragmentManagerCompat());
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putBoolean(BUNDLE_DASHBOARD_INVALIDATED, dashboardInvalidated);
    }

    @Override
    protected void onStop() {
        super.onStop();
        viewModelManager.unregisterListener(getFragmentManagerCompat());
    }

    @NonNull
    @Override
    protected BaseFragment getDefaultFragment() {
        return DashboardFragment.newInstance();
    }

    @IdRes
    @Override
    public int getFragmentContainerId() {
        return R.id.fragment_container;
    }

    @Override
    public void setAuthenticatedAccount(@NonNull Account account) {
        authenticationNavigationView.setAuthenticatedAccount(account);
        showSnackBar(getResources().getString(R.string.logged_in_message, account.getUsername()));
        popAllFragments();
    }

    @Override
    public void clearAuthenticatedAccount() {
        authenticationNavigationView.clearAuthenticatedAccount();
        showSnackBar(getResources().getString(R.string.logged_out_message));
        popAllFragments();
    }

    @Override
    public boolean isUserAuthenticated() {
        return authenticationRepository.getAuthenticatedAccount() != null;
    }

    @Override
    @Nullable
    public String getAuthenticatedUserId() {
        if (authenticationRepository.getAuthenticatedAccount() != null) {
            return authenticationRepository.getAuthenticatedAccount().getId();
        }
        return null;
    }

    @Override
    public void showSnackBar(String message) {
        Snackbar.make(coordinatorLayout, message, Snackbar.LENGTH_LONG).show();
    }

    @Override
    public void popAllFragments() {
        getFragmentManagerCompat()
                .popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
    }

    @Override
    public void invalidateDashboard() {
        dashboardInvalidated = true;
    }

    @Override
    public void validateDashboard() {
        dashboardInvalidated = false;
    }

    @Override
    public boolean isDashboardInvalidated() {
        return dashboardInvalidated;
    }

    @Override
    public void showRegisterScreen() {
        RegisterFragment fragment = RegisterFragment.newInstance();
        getFragmentManagerCompat().beginTransaction()
                .replace(getFragmentContainerId(), fragment, fragment.getUuid().toString())
                .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                .addToBackStack(null)
                .commit();
    }

    @Override
    public void setActionBar(@NonNull Toolbar toolbar) {
        if (actionBarDrawerToggle != null) {
            drawerLayout.removeDrawerListener(actionBarDrawerToggle);
        }
        actionBarDrawerToggle = new ActionBarDrawerToggle(
                this, drawerLayout, toolbar,
                R.string.navigation_drawer_open,
                R.string.navigation_drawer_close
        );
        drawerLayout.addDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.syncState();
        super.setActionBar(toolbar);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (actionBarDrawerToggle != null && actionBarDrawerToggle.onOptionsItemSelected(item)) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        int id = menuItem.getItemId();
        switch (id) {
            case R.id.nav_dashboard:

                break;
            case R.id.nav_my_profile:

                break;
            case R.id.nav_login:
                LoginFragment loginFragment = LoginFragment.newInstance();
                getFragmentManagerCompat().beginTransaction()
                        .replace(getFragmentContainerId(), loginFragment, loginFragment.getUuid().toString())
                        .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                        .addToBackStack(null)
                        .commit();
                break;
            case R.id.nav_register:
                showRegisterScreen();
                break;
            case R.id.nav_logout:
                authenticationRepository.clearAuthenticatedAccount();
                clearAuthenticatedAccount();
                break;
        }
        drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }
}
