package com.example.foodtruckclient.location;

import android.location.Location;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

import java.util.ArrayList;
import java.util.List;

import timber.log.Timber;

public class LocationManager implements OnMapReadyCallback {

    private static final float DEFAULT_ZOOM = 18.0f;

    private FusedLocationProviderClient fusedLocationProviderClient;
    private GoogleMap googleMap;
    private Location lastKnownLocation;
    private List<MarkerOptions> pendingMarkers;

    public LocationManager(FusedLocationProviderClient fusedLocationProviderClient) {
        this.fusedLocationProviderClient = fusedLocationProviderClient;
        pendingMarkers = new ArrayList<>();
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
    }

    private void configureMapUi() {
        googleMap.getUiSettings().setMyLocationButtonEnabled(false);
        googleMap.getUiSettings().setMapToolbarEnabled(false);
    }

    public void zoomOnLocation(double latitude, double longitude) {
        googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(
                new LatLng(latitude, longitude), DEFAULT_ZOOM
        ));
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
        try {
            Timber.d("getDeviceLocationAsync()");
            Task<Location> lastLocationTask = fusedLocationProviderClient.getLastLocation();
            googleMap.setMyLocationEnabled(true);
            lastLocationTask.addOnCompleteListener(onCompleteListener);
        } catch (SecurityException se) {
            Timber.e(se);
        }
    }

    private void addPendingMarkersToMap() {
        Timber.d("Pending markers to add: %d", pendingMarkers.size());
        addMarkersToMap(pendingMarkers);
        pendingMarkers.clear();
    }

    public void takeMarkers(List<MarkerOptions> markers) {
        if (googleMap == null) {
            pendingMarkers.addAll(markers);
        } else {
            addMarkersToMap(markers);
        }
    }

    private void addMarkersToMap(List<MarkerOptions> markers) {
        if (googleMap != null) {
            for (MarkerOptions marker : markers) {
                googleMap.addMarker(marker);
            }
        }
    }
}
