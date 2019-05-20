package com.example.foodtruckclient.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.foodtruckclient.R;
import com.example.foodtruckclient.network.foodtruckapi.model.Account;
import com.google.android.material.navigation.NavigationView;

import butterknife.BindView;
import butterknife.ButterKnife;

public class AuthenticationNavigationView extends NavigationView {

    @BindView(R.id.nav_header_image_view)
    ImageView imageView;

    @BindView(R.id.nav_header_text_view_username)
    TextView usernameTextView;

    @BindView(R.id.nav_header_text_view_login_hint)
    TextView loginHintTextView;

    private MenuItem dashboardMenuItem;
    private MenuItem myProfileMenuItem;
    private MenuItem loginMenuItem;
    private MenuItem registerMenuItem;
    private MenuItem logoutMenuItem;

    public AuthenticationNavigationView(Context context) {
        super(context);
        init();
    }

    public AuthenticationNavigationView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public AuthenticationNavigationView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        ButterKnife.bind(this, getHeaderView(0));
        dashboardMenuItem = getMenu().findItem(R.id.nav_dashboard);
        myProfileMenuItem = getMenu().findItem(R.id.nav_my_profile);
        loginMenuItem = getMenu().findItem(R.id.nav_login);
        registerMenuItem = getMenu().findItem(R.id.nav_register);
        logoutMenuItem = getMenu().findItem(R.id.nav_logout);
        clearAuthenticatedAccount();
    }

    public void setAuthenticatedAccount(@Nullable Account account) {
        if (account == null) {
            clearAuthenticatedAccount();
            return;
        }
        Glide.with(imageView)
                .load(account.getThumbnailUrl())
                .circleCrop()
                .apply(RequestOptions.circleCropTransform())
                .placeholder(R.drawable.ic_account_circle_white_24dp)
                .into(imageView);
        usernameTextView.setText(account.getUsername());
        toggleUserVisibility(true);
    }

    public void clearAuthenticatedAccount() {
        Glide.with(imageView).clear(imageView);
        imageView.setImageDrawable(null);
        usernameTextView.setText(null);
        toggleUserVisibility(false);
    }

    private void toggleUserVisibility(boolean visible) {
        imageView.setVisibility(visible ? View.VISIBLE : View.GONE);
        usernameTextView.setVisibility(visible ? View.VISIBLE : View.GONE);
        loginHintTextView.setVisibility(!visible ? View.VISIBLE : View.GONE);
        myProfileMenuItem.setVisible(visible);
        loginMenuItem.setVisible(!visible);
        registerMenuItem.setVisible(!visible);
        logoutMenuItem.setVisible(visible);
    }
}
