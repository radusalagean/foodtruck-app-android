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
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.example.foodtruckclient.R;
import com.example.foodtruckclient.authentication.AuthenticationBroadcastReceiver;
import com.example.foodtruckclient.authentication.AuthenticationConstants;
import com.example.foodtruckclient.authentication.AuthenticationRepository;
import com.example.foodtruckclient.authentication.AuthenticationService;
import com.example.foodtruckclient.generic.viewmodel.ViewModelManager;
import com.example.foodtruckclient.network.foodtruckapi.model.Account;
import com.example.foodtruckclient.network.foodtruckapi.model.Foodtruck;
import com.example.foodtruckclient.screen.dashboard.DashboardFragment;
import com.example.foodtruckclient.generic.fragment.BaseFragment;
import com.example.foodtruckclient.generic.activity.BaseActivity;
import com.example.foodtruckclient.screen.foodtruckeditor.FoodtruckEditorFragment;
import com.example.foodtruckclient.screen.foodtruckviewer.FoodtruckViewerFragment;
import com.example.foodtruckclient.screen.login.LoginFragment;
import com.example.foodtruckclient.screen.profile.ProfileFragment;
import com.example.foodtruckclient.screen.register.RegisterFragment;
import com.example.foodtruckclient.view.AuthenticationNavigationView;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.snackbar.Snackbar;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends BaseActivity
        implements NavigationView.OnNavigationItemSelectedListener {

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

    @Inject
    AuthenticationBroadcastReceiver authenticationBroadcastReceiver;

    private ActionBarDrawerToggle actionBarDrawerToggle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        getControllerComponent().inject(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        authenticationNavigationView.setNavigationItemSelectedListener(this);
        addDefaultFragmentIfNecessary();
    }

    @Override
    protected void onStart() {
        super.onStart();
        setAuthenticatedAccount(authenticationRepository.getAuthenticatedAccount(), false);
        authenticationBroadcastReceiver.register(this);
        AuthenticationService.startService(getApplicationContext(),
                AuthenticationConstants.ACTION_SYNC_USER_INFO);
        viewModelManager.registerListener(getFragmentManagerCompat());
    }

    @Override
    protected void onStop() {
        super.onStop();
        authenticationBroadcastReceiver.unregister(this);
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
    public void setAuthenticatedAccount(@Nullable Account account, boolean notifyUser) {
        authenticationNavigationView.setAuthenticatedAccount(account);
        if (account != null && notifyUser) {
            showSnackBar(getResources().getString(R.string.logged_in_message, account.getUsername()));
            popAllFragments();
        }
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
    public void showDashboardScreen() {
        String dashboardTag = viewModelManager.getDashboardUuidString();
        BaseFragment fragment;
        if (dashboardTag != null) {
            fragment = (DashboardFragment) getSupportFragmentManager()
                    .findFragmentByTag(dashboardTag);
        } else {
            fragment = DashboardFragment.newInstance();
        }
        showFragment(fragment, dashboardTag != null, null);
    }

    @Override
    public void showFoodtruckViewerScreen(String foodtruckId, String foodtruckName) {
        showFragment(FoodtruckViewerFragment.newInstance(foodtruckId, foodtruckName));
    }

    @Override
    public void showLoginScreen() {
        showFragment(LoginFragment.newInstance());
    }

    @Override
    public void showRegisterScreen() {
        showFragment(RegisterFragment.newInstance());
    }

    @Override
    public void showProfileScreen(String profileId, String profileName) {
        showFragment(ProfileFragment.newInstance(profileId, profileName));
    }

    @Override
    public void showFoodtruckEditorScreen() {
        showFragment(FoodtruckEditorFragment.newInstance());
    }

    @Override
    public void showFoodtruckEditorScreen(Foodtruck foodtruck) {
        showFragment(FoodtruckEditorFragment.newInstance(foodtruck));
    }

    @Override
    public void setToolbar(@NonNull Toolbar toolbar) {
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
        super.setToolbar(toolbar);
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
                showDashboardScreen();
                break;
            case R.id.nav_my_profile:
                String profileId = authenticationRepository.getAuthenticatedAccount().getId();
                String profileName = authenticationRepository.getAuthenticatedAccount().getUsername();
                showProfileScreen(profileId, profileName);
                break;
            case R.id.nav_login:
                showLoginScreen();
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
        Fragment currentFragment = getSupportFragmentManager()
                .findFragmentById(getFragmentContainerId());
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START);
        } else if (currentFragment instanceof BaseFragment &&
                ((BaseFragment) currentFragment).onBackPressed()) {
            return;
        } else {
            super.onBackPressed();
        }
    }
}
