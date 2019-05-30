package com.example.foodtruckclient.generic.mapmvp;

import androidx.annotation.Nullable;

import com.example.foodtruckclient.generic.mvp.BaseMVP;
import com.example.foodtruckclient.generic.viewmodel.BaseViewModel;
import com.example.foodtruckclient.network.foodtruckapi.model.Coordinates;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.Marker;

public interface BaseMapMVP {

    interface Model<T extends BaseViewModel> extends BaseMVP.Model<T> {

    }

    interface View extends BaseMVP.View {
        void initMapViewManager();
    }

    interface Presenter<T extends View> extends BaseMVP.Presenter<T> {
        void disposeMap();
        OnMapReadyCallback getOnMapReadyCallback();
        void setOnMapClickListener(GoogleMap.OnMapClickListener onMapClickListener);
        void setOnInfoWindowClickListener(GoogleMap.OnInfoWindowClickListener onInfoWindowClickListener);
        void zoomOnCurrentDeviceLocation();
        void zoomOnLocation(double latitude, double longitude, boolean instant);
        void addManualMarker(Coordinates coordinates);
        @Nullable Coordinates getManualMarkerCoordinates();
        @Nullable String getFoodtruckIdByMarker(Marker marker);
        void setGesturesEnabled(boolean enabled);
        void setZoomButtonsEnabled(boolean enabled);
    }
}
