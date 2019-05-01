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
import com.example.foodtruckclient.di.presentation.PresentationComponent;
import com.example.foodtruckclient.di.presentation.PresentationModule;

import timber.log.Timber;

public abstract class BaseFragment extends Fragment {

    private String tag = getClass().getSimpleName();
    private boolean isComponentUsed = false;

    @Override
    public void onAttach(Context context) {
        Timber.tag(tag).v("-F-> onAttach(%s)", context);
        super.onAttach(context);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        Timber.tag(tag).v("-F-> onCreate(%s)", savedInstanceState);
        super.onCreate(savedInstanceState);
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

    @UiThread
    protected PresentationComponent getControllerComponent() {
        if (isComponentUsed) {
            throw new IllegalStateException("You shouldn't use PresentationComponent more than once");
        }
        isComponentUsed = true;
        return ((FoodtruckApplication) getActivity().getApplication())
                .getApplicationComponent()
                .newPresentationComponent(new PresentationModule(getActivity()));
    }
}
