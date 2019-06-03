package com.busytrack.foodtruckclient.screen.dashboard;

import androidx.annotation.NonNull;
import androidx.collection.ArrayMap;

import com.busytrack.foodtruckclient.dialog.DialogManager;
import com.busytrack.foodtruckclient.generic.contentinvalidation.InvalidationEffect;
import com.busytrack.foodtruckclient.generic.mapmvp.BaseMapPresenter;
import com.busytrack.foodtruckclient.location.LocationManager;
import com.busytrack.foodtruckclient.network.foodtruckapi.model.Foodtruck;
import com.busytrack.foodtruckclient.permission.PermissionManager;
import com.busytrack.foodtruckclient.util.NumberUtils;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.observers.DisposableObserver;
import timber.log.Timber;

public class DashboardPresenter extends BaseMapPresenter<DashboardMVP.View, DashboardMVP.Model>
        implements DashboardMVP.Presenter {

    public DashboardPresenter(DashboardMVP.Model model,
                              LocationManager locationManager,
                              PermissionManager permissionManager,
                              DialogManager dialogManager) {
        super(model, locationManager, permissionManager, dialogManager);
    }


    @Override
    public void loadViewModel() {
        setRefreshing(true);
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
                        postOnView(() -> view.toast(e.getMessage()));
                        setRefreshing(false);
                    }

                    @Override
                    public void onComplete() {
                        setRefreshing(false);
                    }
                }));
    }

    @Override
    public void reloadFoodtrucks() {
        setRefreshing(true);
        if (model.getCachedViewModel() == null) {
            loadViewModel();
            return;
        }
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
                        postOnView(() -> view.toast(e.getMessage()));
                        setRefreshing(false);
                    }

                    @Override
                    public void onComplete() {
                        setRefreshing(false);
                    }
                }));
    }

    private void processFoodtrucks(@NonNull List<Foodtruck> foodtrucks) {
        // Add markers on the map
        locationManager.clearAllMarkers();
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
        postOnView(() -> view.updateFoodtrucks(model.getCachedViewModel().getFoodtrucks()));
    }

    @Override
    public void zoomOnLocation(double latitude, double longitude, boolean instant) {
        super.zoomOnLocation(latitude, longitude, instant);
        postOnView(() -> view.switchToMapTab());
    }

    @Override
    public void handleInvalidationEffects() {
        if (model.getCachedViewModel() != null) {
            int invalidationEffects = model.getCachedViewModel().getInvalidationEffects();
            Timber.d("handleInvalidationEffects: %s",
                    NumberUtils.convertIntToBinaryString(invalidationEffects, 4));
            if ((invalidationEffects & InvalidationEffect.FOODTRUCK_RELOAD) != 0) {
                reloadFoodtrucks();
            }
            model.getCachedViewModel().clearInvalidationEffects();
        }
    }

    @Override
    public boolean restoreDataFromCache() {
        if (model.getCachedViewModel() == null) {
            return false;
        }
        processFoodtrucks(model.getCachedViewModel().getFoodtrucks());
        handleInvalidationEffects();
        return true;
    }
}
