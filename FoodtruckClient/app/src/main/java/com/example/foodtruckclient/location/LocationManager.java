package com.example.foodtruckclient.location;

import android.location.Location;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

import timber.log.Timber;

public class LocationManager implements OnMapReadyCallback {

    private FusedLocationProviderClient fusedLocationProviderClient;
    private GoogleMap googleMap;
    private Location lastKnownLocation;

    public LocationManager(FusedLocationProviderClient fusedLocationProviderClient) {
        this.fusedLocationProviderClient = fusedLocationProviderClient;
    }

    public void disposeMap() {
        googleMap = null;
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        Timber.d("onMapReady(%s)", googleMap);
        this.googleMap = googleMap;
    }

    public void zoomOnCurrentDeviceLocation() {
        getDeviceLocationAsync((task) -> {
            Timber.d("getDeviceLocationAsync() onComplete(), successful: %s", task.isSuccessful());
            if (task.isSuccessful()) {
                lastKnownLocation = task.getResult();
                googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(
                        new LatLng(lastKnownLocation.getLatitude(),
                                lastKnownLocation.getLongitude()),
                        18.0f));


            }
        });
    }

    public void getDeviceLocationAsync(OnCompleteListener<Location> onCompleteListener) {
        try {
            Timber.d("getDeviceLocationAsync()");
            Task<Location> lastLocationTask = fusedLocationProviderClient.getLastLocation();
            googleMap.setMyLocationEnabled(true);
            googleMap.getUiSettings().setMyLocationButtonEnabled(false);
            lastLocationTask.addOnCompleteListener(onCompleteListener);
        } catch (SecurityException se) {
            Timber.e(se);
        }
    }
}
