package com.example.foodtruckclient.location;

import android.location.Location;

import androidx.annotation.Nullable;
import androidx.collection.ArrayMap;

import com.example.foodtruckclient.network.foodtruckapi.model.Coordinates;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

import java.util.LinkedList;
import java.util.Map;
import java.util.Queue;

import timber.log.Timber;

public class LocationManager implements OnMapReadyCallback {

    public static final String MANUAL_MARKER_ID = "manual_marker";
    private static final float DEFAULT_ZOOM = 18.0f;

    private FusedLocationProviderClient fusedLocationProviderClient;
    private GoogleMap googleMap;
    private Location lastKnownLocation;
    private ArrayMap<String, MarkerOptions> pendingMarkers;
    private ArrayMap<String, Marker> addedMarkers;
    private Queue<Runnable> pendingRunnables;

    public LocationManager(FusedLocationProviderClient fusedLocationProviderClient) {
        this.fusedLocationProviderClient = fusedLocationProviderClient;
        pendingMarkers = new ArrayMap<>();
        addedMarkers = new ArrayMap<>();
        pendingRunnables = new LinkedList<>();
    }

    public void disposeMap() {
        googleMap = null;
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        Timber.d("onMapReady(%s)", googleMap);
        this.googleMap = googleMap;
        configureMapUi();
        addPendingMarkersToMap();
        runPendingRunnables();
    }

    private void configureMapUi() {
        googleMap.getUiSettings().setMyLocationButtonEnabled(false);
        googleMap.getUiSettings().setMapToolbarEnabled(false);
    }

    private void handleRunnable(Runnable runnable) {
        if (googleMap == null) {
            pendingRunnables.offer(runnable);
        } else {
            runnable.run();
        }
    }

    public void setGesturesEnabled(boolean enabled) {
        Runnable runnable = () -> googleMap.getUiSettings().setAllGesturesEnabled(enabled);
        handleRunnable(runnable);
    }

    public void setZoomButtonsEnabled(boolean enabled) {
        Runnable runnable = () -> googleMap.getUiSettings().setZoomControlsEnabled(enabled);
        handleRunnable(runnable);
    }

    public void zoomOnLocation(double latitude, double longitude) {
        Runnable runnable = () -> googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(
                    new LatLng(latitude, longitude), DEFAULT_ZOOM));
        handleRunnable(runnable);
    }

    public void zoomOnCurrentDeviceLocation() {
        getDeviceLocationAsync((task) -> {
            Timber.d("getDeviceLocationAsync() onComplete(), successful: %s, result: %s",
                    task.isSuccessful(), task.getResult());
            if (task.isSuccessful() && task.getResult() != null) {
                lastKnownLocation = task.getResult();
                zoomOnLocation(lastKnownLocation.getLatitude(), lastKnownLocation.getLongitude());
            }
        });
    }

    public void getDeviceLocationAsync(OnCompleteListener<Location> onCompleteListener) {
        Runnable runnable = () -> {
            try {
                Timber.d("getDeviceLocationAsync()");
                Task<Location> lastLocationTask = fusedLocationProviderClient.getLastLocation();
                googleMap.setMyLocationEnabled(true);
                lastLocationTask.addOnCompleteListener(onCompleteListener);
            } catch (SecurityException se) {
                Timber.e(se);
            }
        };
        if (googleMap == null) {
            pendingRunnables.offer(runnable);
        } else {
            runnable.run();
        }
    }

    private void addPendingMarkersToMap() {
        Timber.d("Pending markers to add: %d", pendingMarkers.size());
        addMarkersToMap(pendingMarkers);
        pendingMarkers.clear();
    }

    private void runPendingRunnables() {
        Timber.d("Pending runnables to run: %d", pendingRunnables.size());
        while (!pendingRunnables.isEmpty()) {
            pendingRunnables.poll().run();
        }
    }

    public void takeMarker(String markerId, MarkerOptions marker) {
        if (googleMap == null) {
            pendingMarkers.put(markerId, marker);
        } else {
            addMarkerToMap(markerId, marker);
        }
    }

    public void takeMarkers(Map<String, MarkerOptions> markers) {
        if (googleMap == null) {
            pendingMarkers.putAll(markers);
        } else {
            addMarkersToMap(markers);
        }
    }

    private void addMarkerToMap(String markerId, MarkerOptions marker) {
        if (googleMap != null) {
            if (addedMarkers.get(markerId) != null) {
                clearMarker(markerId);
            }
            Marker m = googleMap.addMarker(marker);
            addedMarkers.put(markerId, m);
        }
    }

    @Nullable
    public Coordinates getManualMarkerCoordinates() {
        Marker marker = addedMarkers.get(MANUAL_MARKER_ID);
        Coordinates coordinates = null;
        if (marker != null) {
            coordinates = new Coordinates(marker.getPosition().latitude, marker.getPosition().longitude);
        }
        return coordinates;
    }

    private void addMarkersToMap(Map<String, MarkerOptions> markers) {
        if (googleMap != null) {
            for (String id : markers.keySet()) {
                addMarkerToMap(id, markers.get(id));
            }
        }
    }

    public void clearMarker(String id) {
        addedMarkers.get(id).remove();
    }

    public void clearAllMarkers() {
        for (Marker marker : addedMarkers.values()) {
            marker.remove();
        }
    }

    public void setOnMapClickListener(@Nullable GoogleMap.OnMapClickListener onMapClickListener) {
        Runnable runnable = () -> googleMap.setOnMapClickListener(onMapClickListener);
        if (googleMap == null) {
            pendingRunnables.offer(runnable);
        } else {
            runnable.run();
        }
    }
}
