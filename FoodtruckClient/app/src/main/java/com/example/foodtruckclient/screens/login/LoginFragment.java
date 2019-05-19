package com.example.foodtruckclient.screens.login;

import android.content.Context;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.example.foodtruckclient.R;
import com.example.foodtruckclient.generic.activity.ActivityContract;
import com.example.foodtruckclient.generic.fragment.BaseFragment;
import com.example.foodtruckclient.generic.mvp.BaseMVP;
import com.example.foodtruckclient.network.foodtruckapi.model.Account;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import timber.log.Timber;

public class LoginFragment extends BaseFragment implements LoginMVP.View {

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @BindView(R.id.login_swipe_refresh_layout)
    SwipeRefreshLayout swipeRefreshLayout;

    @BindView(R.id.login_edit_text_username)
    EditText usernameEditText;

    @BindView(R.id.login_edit_text_password)
    EditText passwordEditText;

    @BindView(R.id.login_button_login)
    Button loginButton;

    @BindView(R.id.login_button_register_link)
    Button registerLinkButton;

    @Inject
    LoginMVP.Presenter presenter;

    @Inject
    ActivityContract activityContract;

    private TextWatcher onTextChangedListener = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            refreshLoginButtonEnabledState();
        }

        @Override
        public void afterTextChanged(Editable s) {}
    };

    public LoginFragment() {}

    public static LoginFragment newInstance() {
        LoginFragment fragment = new LoginFragment();
        Bundle args = new Bundle();
        fragment.generateUniqueId(args);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onAttach(Context context) {
        getControllerComponent().inject(this);
        super.onAttach(context);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_login, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        presenter.takeView(this);
    }

    @Override
    public void onStop() {
        presenter.dropView();
        super.onStop();
    }

    @Override
    protected void initViews() {
        toolbar.setTitle(getString(R.string.login));
        activityContract.setActionBar(toolbar);
    }

    @Override
    protected void registerListeners() {
        usernameEditText.addTextChangedListener(onTextChangedListener);
        passwordEditText.addTextChangedListener(onTextChangedListener);
        passwordEditText.setOnEditorActionListener(((v, actionId, event) -> {
            Timber.d("actionId: %d", actionId);
            if (actionId == EditorInfo.IME_ACTION_DONE && loginButton.isEnabled()) {
                loginButton.performClick();
                return true;
            }
            return false;
        }));
        loginButton.setOnClickListener(v -> presenter.login(
                usernameEditText.getText().toString(), passwordEditText.getText().toString()
        ));
    }

    @Override
    protected void unregisterListeners() {
        usernameEditText.removeTextChangedListener(onTextChangedListener);
        passwordEditText.removeTextChangedListener(onTextChangedListener);
        passwordEditText.setOnEditorActionListener(null);
        loginButton.setOnClickListener(null);
    }

    @Override
    protected BaseMVP.Presenter getPresenter() {
        return presenter;
    }

    @Nullable
    @Override
    protected SwipeRefreshLayout getSwipeRefreshLayout() {
        return swipeRefreshLayout;
    }

    private void refreshLoginButtonEnabledState() {
        loginButton.setEnabled(
                usernameEditText.getText().length() > 0 &&
                passwordEditText.getText().length() > 0
        );
    }

    @Override
    public void setRefreshingIndicator(boolean refreshing) {
        super.setRefreshingIndicator(refreshing);
        usernameEditText.setEnabled(!refreshing);
        passwordEditText.setEnabled(!refreshing);
        registerLinkButton.setEnabled(!refreshing);
        if (refreshing) {
            loginButton.setEnabled(false);
        } else {
            refreshLoginButtonEnabledState();
        }
    }

    @Override
    public void setAuthenticatedAccount(@NonNull Account account) {
        activityContract.setAuthenticatedAccount(account);
    }
}
