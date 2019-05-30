package com.example.foodtruckclient.generic.mapmvp;

import androidx.annotation.Nullable;

import com.example.foodtruckclient.R;
import com.example.foodtruckclient.dialog.DialogManager;
import com.example.foodtruckclient.generic.mvp.BasePresenter;
import com.example.foodtruckclient.location.LocationManager;
import com.example.foodtruckclient.network.foodtruckapi.model.Coordinates;
import com.example.foodtruckclient.permission.PermissionConstants;
import com.example.foodtruckclient.permission.PermissionManager;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import timber.log.Timber;

public abstract class BaseMapPresenter<T extends BaseMapMVP.View, S extends BaseMapMVP.Model>
        extends BasePresenter<T, S> implements BaseMapMVP.Presenter<T> {

    protected LocationManager locationManager;

    public BaseMapPresenter(S model,
                            LocationManager locationManager,
                            PermissionManager permissionManager,
                            DialogManager dialogManager) {
        super(model);
        this.locationManager = locationManager;
        this.permissionManager = permissionManager;
        this.dialogManager = dialogManager;
    }

    @Override
    public void disposeMap() {
        locationManager.disposeMap();
    }

    @Override
    public OnMapReadyCallback getOnMapReadyCallback() {
        return locationManager;
    }

    @Override
    public void setOnMapClickListener(GoogleMap.OnMapClickListener onMapClickListener) {
        locationManager.setOnMapClickListener(onMapClickListener);
    }

    @Override
    public void setOnInfoWindowClickListener(GoogleMap.OnInfoWindowClickListener onInfoWindowClickListener) {
        locationManager.setOnInfoWindowClickListener(onInfoWindowClickListener);
    }

    @Override
    public void zoomOnCurrentDeviceLocation() {
        doOnPermissionGranted(
                PermissionConstants.PERMISSION_ACCESS_FINE_LOCATION,
                R.string.alert_dialog_location_no_permission_message,
                view.getPermissionRequestDelegate(), locationManager::zoomOnCurrentDeviceLocation
        );
    }

    @Override
    public void zoomOnLocation(double latitude, double longitude, boolean instant) {
        locationManager.zoomOnLocation(latitude, longitude, instant);
    }

    @Override
    public void addManualMarker(Coordinates coordinates) {
        MarkerOptions marker = new MarkerOptions()
                .position(new LatLng(coordinates.getLatitude(), coordinates.getLongitude()));
        locationManager.takeMarker(LocationManager.MANUAL_MARKER_ID, marker);
    }

    @Nullable
    @Override
    public Coordinates getManualMarkerCoordinates() {
        return locationManager.getManualMarkerCoordinates();
    }

    @Nullable
    @Override
    public String getFoodtruckIdByMarker(Marker marker) {
        return locationManager.getFoodtruckIdByMarker(marker);
    }

    @Override
    public void setGesturesEnabled(boolean enabled) {
        locationManager.setGesturesEnabled(enabled);
    }

    @Override
    public void setZoomButtonsEnabled(boolean enabled) {
        locationManager.setZoomButtonsEnabled(enabled);
    }
}
