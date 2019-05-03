package com.example.foodtruckclient.dashboard;

import androidx.annotation.NonNull;

import com.example.foodtruckclient.R;
import com.example.foodtruckclient.dialog.DialogManager;
import com.example.foodtruckclient.location.LocationManager;
import com.example.foodtruckclient.permission.PermissionConstants;
import com.example.foodtruckclient.permission.PermissionManager;
import com.example.foodtruckclient.permission.PermissionRequestListener;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.observers.DisposableObserver;
import timber.log.Timber;

public class DashboardPresenter implements DashboardMVP.Presenter {

    private DashboardMVP.Model model;
    private LocationManager locationManager;
    private PermissionManager permissionManager;
    private DialogManager dialogManager;
    private DashboardMVP.View view;
    private CompositeDisposable compositeDisposable;


    public DashboardPresenter(DashboardMVP.Model model,
                              LocationManager locationManager,
                              PermissionManager permissionManager,
                              DialogManager dialogManager) {
        Timber.d("Constructor");
        this.model = model;
        this.locationManager = locationManager;
        this.permissionManager = permissionManager;
        this.dialogManager = dialogManager;
        compositeDisposable = new CompositeDisposable();
    }


    @Override
    public void loadFoodtrucks() {
        compositeDisposable.add(model.getResults()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableObserver<List<DashboardFoodtruckViewModel>>() {
                    @Override
                    public void onNext(List<DashboardFoodtruckViewModel> dashboardFoodtruckViewModels) {
                        processFoodtrucks(dashboardFoodtruckViewModels);
                    }

                    @Override
                    public void onError(Throwable e) {
                        Timber.e(e);
                    }

                    @Override
                    public void onComplete() {}
                }));
    }

    private void processFoodtrucks(List<DashboardFoodtruckViewModel> foodtrucks) {
        // Add markers on the map
        List<MarkerOptions> markers = new ArrayList<>();
        for (DashboardFoodtruckViewModel model : foodtrucks) {
            MarkerOptions marker = new MarkerOptions()
                    .position(new LatLng(model.getLatitude(), model.getLongitude()))
                    .title(model.getName());
            markers.add(marker);
        }
        locationManager.takeMarkers(markers);

        // Send foodtrucks to view
        if (view != null) {
            view.updateFoodtrucks(foodtrucks);
        }
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
    public void zoomOnCurrentDeviceLocation() {
        if (permissionManager.isPermissionGranted(PermissionConstants.PERMISSION_ACCESS_FINE_LOCATION)) {
            locationManager.zoomOnCurrentDeviceLocation();
        } else {
            permissionManager.requestPermission(PermissionConstants.PERMISSION_ACCESS_FINE_LOCATION,
                    view.getPermissionRequestDelegate(),
                    new PermissionRequestListener() {
                        @Override
                        public void onPermissionRequestGranted() {
                            Timber.d("zoomOnCurrentDeviceLocation() permission request granted");
                            zoomOnCurrentDeviceLocation();
                        }

                        @Override
                        public void onPermissionRequestDenied() {
                            Timber.d("zoomOnCurrentDeviceLocation() permission request denied");
                            dialogManager.showBasicAlertDialog(
                                            R.string.alert_dialog_location_permission_denied_title,
                                            R.string.alert_dialog_location_no_permission_message
                            );
                        }

                        @Override
                        public void onPermissionRequestCanceled() {
                            Timber.d("zoomOnCurrentDeviceLocation() permission request canceled");
                            dialogManager.showBasicAlertDialog(
                                    R.string.alert_dialog_location_permission_canceled_title,
                                    R.string.alert_dialog_location_no_permission_message
                            );
                        }
                    });
        }
    }

    @Override
    public void zoomOnLocation(double latitude, double longitude) {
        view.switchToMapTab();
        locationManager.zoomOnLocation(latitude, longitude);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        permissionManager.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    @Override
    public void takeView(DashboardMVP.View view) {
        this.view = view;
    }

    @Override
    public void dropView() {
        compositeDisposable.dispose();
        this.view = null;
    }
}
