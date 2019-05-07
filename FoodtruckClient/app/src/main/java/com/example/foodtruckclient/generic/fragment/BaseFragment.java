package com.example.foodtruckclient.generic.fragment;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.UiThread;
import androidx.fragment.app.Fragment;

import com.example.foodtruckclient.application.FoodtruckApplication;
import com.example.foodtruckclient.di.activity.ActivityComponent;
import com.example.foodtruckclient.di.activity.ActivityModule;
import com.example.foodtruckclient.permission.PermissionRequestDelegate;

import java.util.UUID;

import timber.log.Timber;

public abstract class BaseFragment extends Fragment
        implements PermissionRequestDelegate {

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

    /**
     * Should be called only once, during instance creation.
     * Used for uniquely identifying the fragment's view model
     */
    public void generateUniqueId(@NonNull Bundle bundle) {
        if (uuid != null) {
            throw new UnsupportedOperationException("You shouldn't generate a new unique id for this fragment if one already exists!");
        }
        bundle.putSerializable(ARG_UUID, UUID.randomUUID());
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
}
