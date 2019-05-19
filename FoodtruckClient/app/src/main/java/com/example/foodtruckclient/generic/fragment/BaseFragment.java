package com.example.foodtruckclient.generic.fragment;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.UiThread;
import androidx.fragment.app.Fragment;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.example.foodtruckclient.application.FoodtruckApplication;
import com.example.foodtruckclient.di.activity.ActivityComponent;
import com.example.foodtruckclient.di.activity.ActivityModule;
import com.example.foodtruckclient.generic.activity.ActivityContract;
import com.example.foodtruckclient.generic.mvp.BaseMVP;
import com.example.foodtruckclient.permission.PermissionRequestDelegate;

import java.util.UUID;

import timber.log.Timber;

public abstract class BaseFragment extends Fragment
        implements BaseMVP.View, PermissionRequestDelegate {

    protected static final String ARG_UUID = "UUID";

    private String tag = getClass().getSimpleName();
    private boolean isComponentUsed = false;
    private UUID uuid;

    @Override
    public void onAttach(Context context) {
        Timber.tag(tag).v("-F-> onAttach(%s)", context);
        super.onAttach(context);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        Timber.tag(tag).v("-F-> onCreate(%s)", savedInstanceState);
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            uuid = (UUID) getArguments().getSerializable(ARG_UUID);
            getPresenter().setUuid(uuid);
            Timber.d("Fragment's UUID: %s", uuid);
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        Timber.tag(tag).v("-F-> onCreateView(%s, %s, %s)", inflater, container,
                savedInstanceState);
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        Timber.tag(tag).v("-F-> onViewCreated(%s, %s)", view, savedInstanceState);
        initViews();
        registerListeners();
        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        Timber.tag(tag).v("-F-> onActivityCreated(%s)", savedInstanceState);
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public void onStart() {
        Timber.tag(tag).v("-F-> onStart()");
        super.onStart();
    }

    @Override
    public void onResume() {
        Timber.tag(tag).v("-F-> onResume()");
        super.onResume();
    }

    @Override
    public void onPause() {
        Timber.tag(tag).v("-F-> onPause()");
        super.onPause();
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        Timber.tag(tag).v("-F-> onSaveInstanceState(%s)", outState);
        super.onSaveInstanceState(outState);
    }

    @Override
    public void onStop() {
        Timber.tag(tag).v("-F-> onStop()");
        super.onStop();
    }

    @Override
    public void onDestroyView() {
        Timber.tag(tag).v("-F-> onDestroyView()");
        unregisterListeners();
        super.onDestroyView();
    }

    @Override
    public void onDestroy() {
        Timber.tag(tag).v("-F-> onDestroy()");
        super.onDestroy();
    }

    @Override
    public void onDetach() {
        Timber.tag(tag).v("-F-> onDetach()");
        super.onDetach();
    }

    @Override
    public void requestPermission(String permission, int requestCode) {
        requestPermissions(new String[] { permission }, requestCode);
    }

    @Override
    public void setRefreshingIndicator(boolean refreshing) {
        if (getSwipeRefreshLayout() != null) {
            getSwipeRefreshLayout().setRefreshing(refreshing);
        }
    }

    @Override
    public void toast(String message) {
        Toast.makeText(getContext(), message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showSnackBar(int stringResID) {
        if (getActivityContract() != null) {
            getActivityContract().showSnackBar(getString(stringResID));
        }
    }

    @Override
    public void showSnackBar(int stringResId, Object... formatArgs) {
        if (getActivityContract() != null) {
            getActivityContract().showSnackBar(getResources().getString(stringResId, formatArgs));
        }
    }

    @Override
    public PermissionRequestDelegate getPermissionRequestDelegate() {
        return this;
    }

    @Override
    public void onBackPressed() {
        getActivity().onBackPressed();
    }

    @UiThread
    protected ActivityComponent getControllerComponent() {
        if (isComponentUsed) {
            throw new IllegalStateException("You shouldn't use ActivityComponent more than once");
        }
        isComponentUsed = true;
        return ((FoodtruckApplication) getActivity().getApplication())
                .getApplicationComponent()
                .newActivityComponent(new ActivityModule(getActivity()));
    }

    @Nullable
    protected SwipeRefreshLayout getSwipeRefreshLayout() {
        return null;
    }

    @Nullable
    protected ActivityContract getActivityContract() {
        return null;
    }

    public UUID getUuid() {
        return uuid;
    }

    /**
     * Should be called only once, during instance creation.
     * Used for uniquely identifying the fragment's view model
     */
    public void generateUniqueId(@NonNull Bundle bundle) {
        if (uuid != null) {
            throw new UnsupportedOperationException("You shouldn't generate a new unique id for this fragment if one already exists!");
        }
        UUID uuid = UUID.randomUUID();
        bundle.putSerializable(ARG_UUID, uuid);
        this.uuid = uuid;
    }

    /**
     * Called during the {@link Fragment#onViewCreated(View, Bundle)} lifecycle method,
     * override to initialize views
     */
    protected abstract void initViews();

    /**
     * Called during the {@link Fragment#onViewCreated(View, Bundle)} lifecycle method,
     * override to register view listeners
     */
    protected abstract void registerListeners();

    /**
     * Called during the {@link Fragment#onDestroyView()} lifecycle method,
     * override to unregister view listeners
     */
    protected abstract void unregisterListeners();

    /**
     * Override to return the presenter
     */
    protected abstract BaseMVP.Presenter getPresenter();
}
