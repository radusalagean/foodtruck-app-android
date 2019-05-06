package com.example.foodtruckclient.generic.fragment;

import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.foodtruckclient.location.MapViewManager;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;

public abstract class BaseMapFragment extends BaseFragment {

    protected MapViewManager mapViewManager;

    /**
     * Call this in order to properly initialize the {@link MapViewManager}
     *
     * @param onMapReadyCallback callback used to set the {@link GoogleMap} instance
     */
    protected void initMapViewManager(OnMapReadyCallback onMapReadyCallback) {
        mapViewManager = new MapViewManager(onMapReadyCallback);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initMapViewManager();
        mapViewManager.onCreate(savedInstanceState);
    }

    @Override
    public void onStart() {
        super.onStart();
        mapViewManager.onStart();
    }

    @Override
    public void onResume() {
        super.onResume();
        mapViewManager.onResume();
    }

    @Override
    public void onPause() {
        mapViewManager.onPause();
        super.onPause();
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        mapViewManager.onSaveInstanceState(outState);
    }

    @Override
    public void onStop() {
        mapViewManager.onStop();
        super.onStop();
    }

    @Override
    public void onDestroy() {
        mapViewManager.onDestroy();
        disposeMap();
        super.onDestroy();
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        mapViewManager.onLowMemory();
    }

    protected abstract void initMapViewManager();

    protected abstract void disposeMap();
}
