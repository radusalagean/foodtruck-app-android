package com.busytrack.foodtruckclient.di.activity.location;

import android.content.Context;

import com.busytrack.foodtruckclient.location.LocationManager;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;

import dagger.Module;
import dagger.Provides;

@Module
public class LocationModule {

    @Provides
    LocationManager provideLocationManager(FusedLocationProviderClient fusedLocationProviderClient) {
        return new LocationManager(fusedLocationProviderClient);
    }

    @Provides
    FusedLocationProviderClient provideFusedLocationProviderClient(Context context) {
        return LocationServices.getFusedLocationProviderClient(context);
    }
}
