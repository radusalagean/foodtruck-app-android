package com.example.foodtruckclient.screen.register;

import android.content.Context;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.res.ResourcesCompat;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.example.foodtruckclient.R;
import com.example.foodtruckclient.generic.activity.ActivityContract;
import com.example.foodtruckclient.generic.fragment.BaseFragment;
import com.example.foodtruckclient.generic.mvp.BaseMVP;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.BindViews;
import butterknife.ButterKnife;
import butterknife.ViewCollections;

public class RegisterFragment extends BaseFragment implements RegisterMVP.View {

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @BindView(R.id.register_swipe_refresh_layout)
    SwipeRefreshLayout swipeRefreshLayout;

    @BindView(R.id.register_text_view_username_availability)
    TextView usernameAvailabilityTextView;

    @BindView(R.id.register_edit_text_username)
    EditText usernameEditText;

    @BindView(R.id.register_edit_text_password)
    EditText passwordEditText;

    @BindView(R.id.register_edit_text_password_retype)
    EditText retypePasswordEditText;

    @BindViews({
            R.id.register_edit_text_username,
            R.id.register_edit_text_password,
            R.id.register_edit_text_password_retype
    })
    List<EditText> editTexts;

    @BindView(R.id.register_button_register)
    Button registerButton;

    @Inject
    RegisterMVP.Presenter presenter;

    @Inject
    ActivityContract activityContract;

    public RegisterFragment() {}

    public static RegisterFragment newInstance() {
        RegisterFragment fragment = new RegisterFragment();
        Bundle args = new Bundle();
        fragment.generateUniqueId(args);
        fragment.setArguments(args);
        return fragment;
    }

    private TextWatcher usernameTextWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            String username = s.toString().trim();
            clearUsernameAvailabilityText();
            if (username.length() > 0) {
                presenter.checkUsernameAvailability(username);
            }
        }

        @Override
        public void afterTextChanged(Editable s) {}
    };

    private TextWatcher onTextChangeListener = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            refreshRegisterButtonEnabledState();
        }

        @Override
        public void afterTextChanged(Editable s) {}
    };

    @Override
    public void onAttach(Context context) {
        getControllerComponent().inject(this);
        super.onAttach(context);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState); // called for logging purposes
        View view = inflater.inflate(R.layout.fragment_register, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    protected void initViews() {
        swipeRefreshLayout.setColorSchemeResources(R.color.colorAccent);
        toolbar.setTitle(getString(R.string.register));
        activityContract.setToolbar(toolbar);
    }

    @Override
    protected void disposeViews() {
        // nothing to do here
    }

    @Override
    protected void registerListeners() {
        usernameEditText.addTextChangedListener(usernameTextWatcher);
        ViewCollections.run(editTexts, ((view, index) ->
                view.addTextChangedListener(onTextChangeListener)));
        registerButton.setOnClickListener((v -> presenter.register(
                usernameEditText.getText().toString(),
                passwordEditText.getText().toString()
        )));
    }

    @Override
    protected void unregisterListeners() {
        usernameEditText.removeTextChangedListener(usernameTextWatcher);
        ViewCollections.run(editTexts, ((view, index) ->
                view.removeTextChangedListener(onTextChangeListener)));
        registerButton.setOnClickListener(null);
    }

    @Override
    protected void loadData() {
        // nothing to do here
    }

    @Override
    @SuppressWarnings("unchecked")
    protected <T extends BaseMVP.View> BaseMVP.Presenter<T> getPresenter() {
        return (BaseMVP.Presenter<T>) presenter;
    }

    @Nullable
    @Override
    protected SwipeRefreshLayout getSwipeRefreshLayout() {
        return swipeRefreshLayout;
    }

    @Nullable
    @Override
    protected ActivityContract getActivityContract() {
        return activityContract;
    }

    private void refreshRegisterButtonEnabledState() {
        String username = usernameEditText.getText().toString().trim();
        String password = passwordEditText.getText().toString();
        String retypedPassword = retypePasswordEditText.getText().toString();
        registerButton.setEnabled(
                username.length() > 0 &&
                password.length() > 0 &&
                password.equals(retypedPassword)
        );
    }

    @Override
    public void setRefreshingIndicator(boolean refreshing) {
        super.setRefreshingIndicator(refreshing);
        ViewCollections.run(editTexts, ((view, index) -> view.setEnabled(!refreshing)));
        if (refreshing) {
            registerButton.setEnabled(false);
        } else {
            refreshRegisterButtonEnabledState();
        }
    }

    @Override
    public void setUsernameAvailability(boolean available) {
        if (usernameEditText.getText().toString().trim().length() == 0) {
            return;
        }
        int stringRes = available ? R.string.username_available : R.string.username_unavailable;
        int colorRes = available ? R.color.colorGreen : R.color.colorRed;
        usernameAvailabilityTextView.setVisibility(View.VISIBLE);
        usernameAvailabilityTextView.setText(getResources().getString(stringRes));
        usernameAvailabilityTextView
                .setTextColor(ResourcesCompat.getColor(getResources(), colorRes, null));
    }

    private void clearUsernameAvailabilityText() {
        usernameAvailabilityTextView.setVisibility(View.INVISIBLE);
        usernameAvailabilityTextView.setText(null);
    }
}
