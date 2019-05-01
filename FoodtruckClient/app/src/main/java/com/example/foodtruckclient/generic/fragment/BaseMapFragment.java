package com.example.foodtruckclient.generic.fragment;

import android.os.Bundle;
import android.view.View;

import androidx.annotation.CallSuper;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.foodtruckclient.util.MapViewManager;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;

import timber.log.Timber;

public abstract class BaseMapFragment extends BaseFragment implements OnMapReadyCallback {

    protected MapViewManager mapViewManager = new MapViewManager(this);
    protected GoogleMap googleMap;

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        mapViewManager.onCreate(savedInstanceState);
        super.onViewCreated(view, savedInstanceState);
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
        googleMap = null;
        super.onDestroy();
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        mapViewManager.onLowMemory();
    }

    @Override
    @CallSuper
    public void onMapReady(GoogleMap googleMap) {
        Timber.d("onMapReady(%s)", googleMap);
        this.googleMap = googleMap;
        // TODO add markers and whatnot
    }
}
