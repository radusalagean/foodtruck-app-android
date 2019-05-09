package com.example.foodtruckclient.screens.dashboard;

import androidx.annotation.NonNull;
import androidx.collection.ArrayMap;
import androidx.fragment.app.FragmentTransaction;

import com.example.foodtruckclient.R;
import com.example.foodtruckclient.generic.mvp.BasePresenter;
import com.example.foodtruckclient.dialog.DialogManager;
import com.example.foodtruckclient.screens.foodtruckviewer.FoodtruckViewerFragment;
import com.example.foodtruckclient.generic.activity.ActivityContract;
import com.example.foodtruckclient.location.LocationManager;
import com.example.foodtruckclient.network.foodtruckapi.model.Foodtruck;
import com.example.foodtruckclient.permission.PermissionConstants;
import com.example.foodtruckclient.permission.PermissionManager;
import com.example.foodtruckclient.permission.PermissionRequestListener;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.observers.DisposableObserver;
import timber.log.Timber;

public class DashboardPresenter extends BasePresenter<DashboardMVP.View, DashboardMVP.Model>
        implements DashboardMVP.Presenter {

    private LocationManager locationManager;
    private PermissionManager permissionManager;
    private DialogManager dialogManager;
    private ActivityContract activityContract;

    public DashboardPresenter(DashboardMVP.Model model,
                              LocationManager locationManager,
                              PermissionManager permissionManager,
                              DialogManager dialogManager,
                              ActivityContract activityContract) {
        super(model);
        this.locationManager = locationManager;
        this.permissionManager = permissionManager;
        this.dialogManager = dialogManager;
        this.activityContract = activityContract;
    }


    @Override
    public void loadViewModel() {
        compositeDisposable.add(model.getViewModel()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableObserver<DashboardViewModel>() {
                    @Override
                    public void onNext(DashboardViewModel dashboardViewModel) {
                        processFoodtrucks(dashboardViewModel.getFoodtrucks());
                    }

                    @Override
                    public void onError(Throwable e) {
                        Timber.e(e);
                        view.toast(e.getMessage());
                    }

                    @Override
                    public void onComplete() {}
                }));
    }

    @Override
    public void reloadFoodtrucks() {
        clearFoodtrucks();
        compositeDisposable.add(model.getFoodtrucks()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableObserver<List<Foodtruck>>() {
                    @Override
                    public void onNext(List<Foodtruck> foodtrucks) {
                        processFoodtrucks(foodtrucks);
                    }

                    @Override
                    public void onError(Throwable e) {
                        Timber.e(e);
                        view.toast(e.getMessage());
                    }

                    @Override
                    public void onComplete() {}
                }));
    }

    private void clearFoodtrucks() {
        locationManager.clearAllMarkers();
        if (view != null) {
            view.clearFoodtrucks();
        }
    }

    private void processFoodtrucks(@NonNull List<Foodtruck> foodtrucks) {
        // Add markers on the map
        ArrayMap<String, MarkerOptions> markers = new ArrayMap<>();
        for (Foodtruck foodtruck : foodtrucks) {
            MarkerOptions marker = new MarkerOptions()
                    .position(new LatLng(foodtruck.getCoordinates().getLatitude(),
                            foodtruck.getCoordinates().getLongitude()))
                    .title(foodtruck.getName());
            markers.put(foodtruck.getId(), marker);
        }
        locationManager.takeMarkers(markers);

        // Send foodtrucks to view
        if (view != null) {
            view.updateFoodtrucks(model.getCachedViewModel().getFoodtrucks());
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
    public void viewFoodtruck(String id, String name) {
        FoodtruckViewerFragment fragment = FoodtruckViewerFragment.newInstance(id, name);
        activityContract.getFragmentManagerCompat().beginTransaction()
                .replace(activityContract.getFragmentContainerId(), fragment)
                .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                .addToBackStack(null)
                .commit();
    }

    @Override
    public PermissionManager getPermissionManager() {
        return permissionManager;
    }
}
