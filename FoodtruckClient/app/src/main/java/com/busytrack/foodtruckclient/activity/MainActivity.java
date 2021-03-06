package com.busytrack.foodtruckclient.activity;

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

import com.busytrack.foodtruckclient.R;
import com.busytrack.foodtruckclient.authentication.AuthenticationBroadcastReceiver;
import com.busytrack.foodtruckclient.authentication.AuthenticationConstants;
import com.busytrack.foodtruckclient.authentication.AuthenticationRepository;
import com.busytrack.foodtruckclient.authentication.AuthenticationService;
import com.busytrack.foodtruckclient.generic.activity.BaseActivity;
import com.busytrack.foodtruckclient.generic.fragment.BaseFragment;
import com.busytrack.foodtruckclient.generic.viewmodel.ViewModelManager;
import com.busytrack.foodtruckclient.network.NetworkConstants;
import com.busytrack.foodtruckclient.network.foodtruckapi.model.Account;
import com.busytrack.foodtruckclient.network.foodtruckapi.model.Foodtruck;
import com.busytrack.foodtruckclient.screen.dashboard.DashboardFragment;
import com.busytrack.foodtruckclient.screen.foodtruckeditor.FoodtruckEditorFragment;
import com.busytrack.foodtruckclient.screen.foodtruckviewer.FoodtruckViewerFragment;
import com.busytrack.foodtruckclient.screen.login.LoginFragment;
import com.busytrack.foodtruckclient.screen.profile.ProfileFragment;
import com.busytrack.foodtruckclient.screen.register.RegisterFragment;
import com.busytrack.foodtruckclient.util.IntentUtils;
import com.busytrack.foodtruckclient.view.AuthenticationNavigationView;
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
        getActivityComponent().inject(this);
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
        viewModelManager.registerListener(getSupportFragmentManager());
    }

    @Override
    protected void onStop() {
        super.onStop();
        authenticationBroadcastReceiver.unregister(this);
        viewModelManager.unregisterListener(getSupportFragmentManager());
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
        getSupportFragmentManager()
                .popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
    }

    @Override
    public void showDashboardScreen() {
        String dashboardTag = viewModelManager.getDashboardUuidString();
        BaseFragment fragment;
        // if the fragment is already created, use that instance
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
        // set up the navigation drawer toggle
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
        // first, let the navigation drawer toggle handle the event
        if (actionBarDrawerToggle != null && actionBarDrawerToggle.onOptionsItemSelected(item)) {
            return true;
        }
        // if the selected item was not our nav drawer toggle, pass the event deeper
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
            case R.id.nav_project_page:
                IntentUtils.openLinkInBrowser(this, NetworkConstants.PROJECT_PAGE_URL);
                break;
            case R.id.nav_privacy_policy:
                IntentUtils.openLinkInBrowser(this, NetworkConstants.PRIVACY_POLICY_URL);
                break;
        }
        drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onBackPressed() {
        Fragment currentFragment = getSupportFragmentManager()
                .findFragmentById(getFragmentContainerId());
        // Order of handling the back press event:
        // 1. Close the navigation drawer if it's open
        // 2. If event was not handled in step 1, let the current fragment handle it
        // 3. If the event was not handled in step 2, pass the event deeper
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START);
        } else if (!(currentFragment instanceof BaseFragment) ||
                !((BaseFragment) currentFragment).onBackPressed()) {
            super.onBackPressed();
        }
    }
}
