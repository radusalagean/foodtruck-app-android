package com.example.foodtruckclient.generic.mapmvp;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.foodtruckclient.generic.fragment.BaseFragment;
import com.example.foodtruckclient.location.DisposeMapCallback;
import com.example.foodtruckclient.location.MapViewManager;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;

public abstract class BaseMapFragment extends BaseFragment
        implements BaseMapMVP.View, DisposeMapCallback {

    protected MapViewManager mapViewManager;
    protected MapView mapView;

    /**
     * Call this in order to properly initialize the {@link MapViewManager}
     *
     * @param onMapReadyCallback callback used to set the {@link GoogleMap} instance
     */
    protected void initMapViewManager(OnMapReadyCallback onMapReadyCallback) {
        mapViewManager = new MapViewManager(onMapReadyCallback, this);
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
        super.onDestroy();
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        mapViewManager.onLowMemory();
    }

    @Override
    public void initMapViewManager() {
        initMapViewManager(
                ((BaseMapMVP.Presenter) getPresenter()).getOnMapReadyCallback()
        );
    }

    @Override
    public void disposeMap() {
        ((BaseMapMVP.Presenter) getPresenter()).disposeMap();
    }
}
