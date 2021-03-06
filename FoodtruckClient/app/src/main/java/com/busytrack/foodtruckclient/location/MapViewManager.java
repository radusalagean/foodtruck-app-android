package com.busytrack.foodtruckclient.location;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.busytrack.foodtruckclient.util.NumberUtils;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;

import timber.log.Timber;

/**
 * A class that manages syncing the lifecycle of a {@link com.google.android.gms.maps.MapView},
 * reacting to the lifecycle of its parent (Activity / Fragment)
 */
public class MapViewManager {

    private static final int FLAG_CREATED = 1; // 001
    private static final int FLAG_STARTED = 2; // 010
    private static final int FLAG_RESUMED = 4; // 100

    private MapView mapView;
    private Bundle mapViewBundle;
    private int state;
    private OnMapReadyCallback onMapReadyCallback;
    private DisposeMapCallback disposeMapCallback;

    public MapViewManager(@NonNull OnMapReadyCallback onMapReadyCallback,
                          @NonNull DisposeMapCallback disposeMapCallback) {
        this.onMapReadyCallback = onMapReadyCallback;
        this.disposeMapCallback = disposeMapCallback;
    }

    public void takeMapView(MapView mapView) {
        Timber.d("takeMapView(%s)", mapView);
        if (this.mapView != mapView) {
            this.mapView = mapView;
            syncLifecycle();
        }
    }

    public MapView getMapView() {
        return mapView;
    }

    public void dropMapView(MapView mapView) {
        Timber.d("dropMapView(%s)", mapView);
        if (this.mapView != null && this.mapView == mapView) {
            disposeMapView();
            disposeMapCallback.disposeMap();
            this.mapView = null;
            mapViewBundle = null;
        }
    }

    /**
     * Method that syncs the lifecycle of the {@link #mapView} with to the current state of the manager
     */
    private void syncLifecycle() {
        Timber.d("Syncing mapView... (state = %s)", getStateAsBinaryString());
        if (isStateMet(FLAG_CREATED)) {
            Timber.d("Syncing mapView... (calling onCreate())");
            createMap(mapViewBundle);
        }
        if (isStateMet(FLAG_STARTED)) {
            Timber.d("Syncing mapView... (calling onStart())");
            mapView.onStart();
        }
        if (isStateMet(FLAG_RESUMED)) {
            Timber.d("Syncing mapView... (calling onResume())");
            mapView.onResume();
        }
    }

    /**
     * Method that disposes the {@link #mapView} gracefully, calling the appropriate lifecycle methods
     */
    private void disposeMapView() {
        Timber.d("Disposing mapView... (state = %s)", getStateAsBinaryString());
        if (isStateMet(FLAG_RESUMED)) {
            Timber.d("Disposing mapView... (calling onPause())");
            mapView.onPause();
        }
        if (isStateMet(FLAG_STARTED)) {
            Timber.d("Disposing mapView... (calling onStop())");
            mapView.onStop();
        }
        if (isStateMet(FLAG_CREATED)) {
            Timber.d("Disposing mapView... (calling onDestroy())");
            mapView.onDestroy();
        }
    }

    private boolean isStateMet(int flag) {
        return (state & flag) == flag;
    }

    private void createMap(@Nullable Bundle mapViewBundle) {
        mapView.onCreate(mapViewBundle);
        this.mapViewBundle = null;
        mapView.getMapAsync(onMapReadyCallback);
    }

    public void onCreate(@Nullable Bundle mapViewBundle) {
        if (mapView != null) {
            Timber.d("Calling onCreate(%s) on mapView...", mapViewBundle);
            createMap(mapViewBundle);
        } else {
            this.mapViewBundle = mapViewBundle;
        }
        state |= FLAG_CREATED;
    }

    public void onStart() {
        if (mapView != null) {
            Timber.d("Calling onStart() on mapView...");
            mapView.onStart();
        }
        state |= FLAG_STARTED;
    }

    public void onResume() {
        if (mapView != null) {
            Timber.d("Calling onResume() on mapView...");
            mapView.onResume();
        }
        state |= FLAG_RESUMED;
    }

    public void onPause() {
        state &= ~FLAG_RESUMED;
        if (mapView != null) {
            Timber.d("Calling onPause() on mapView...");
            mapView.onPause();
        }
    }

    public void onSaveInstanceState(@Nullable Bundle outState) {
        if (mapView != null) {
            Timber.d("Calling onSaveInstanceState(%s) on mapView...", outState);
            mapView.onSaveInstanceState(outState);
        }
    }

    public void onStop() {
        state &= ~FLAG_STARTED;
        if (mapView != null) {
            Timber.d("Calling onStop() on mapView...");
            mapView.onStop();
        }
    }

    public void onDestroy() {
        state &= ~FLAG_CREATED;
        if (mapView != null) {
            Timber.d("Calling onDestroy() on mapView...");
            mapView.onDestroy();
        }
    }

    public void onLowMemory() {
        if (mapView != null) {
            Timber.d("Calling onLowMemory() on mapView...");
            mapView.onLowMemory();
        }
    }

    private String getStateAsBinaryString() {
        return NumberUtils.convertIntToBinaryString(state, 3);
    }
}
