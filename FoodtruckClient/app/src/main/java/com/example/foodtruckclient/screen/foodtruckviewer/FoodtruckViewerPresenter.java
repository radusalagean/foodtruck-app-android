package com.example.foodtruckclient.screen.foodtruckviewer;

import androidx.annotation.NonNull;

import com.example.foodtruckclient.R;
import com.example.foodtruckclient.dialog.DialogManager;
import com.example.foodtruckclient.generic.mapmvp.BaseMapPresenter;
import com.example.foodtruckclient.location.LocationManager;
import com.example.foodtruckclient.network.foodtruckapi.model.Coordinates;
import com.example.foodtruckclient.network.foodtruckapi.model.Message;
import com.example.foodtruckclient.network.foodtruckapi.model.Review;
import com.example.foodtruckclient.permission.PermissionManager;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.observers.DisposableObserver;
import timber.log.Timber;

public class FoodtruckViewerPresenter extends BaseMapPresenter<FoodtruckViewerMVP.View, FoodtruckViewerMVP.Model>
        implements FoodtruckViewerMVP.Presenter {

    public FoodtruckViewerPresenter(FoodtruckViewerMVP.Model model,
                                    LocationManager locationManager,
                                    PermissionManager permissionManager,
                                    DialogManager dialogManager) {
        super(model, locationManager, permissionManager, dialogManager);
    }

    @Override
    public void loadViewModel(String foodtruckId, boolean refresh) {
        setRefreshing(true);
        Observable<FoodtruckViewerViewModel> observable = refresh ?
                model.getFreshViewModel(foodtruckId) : model.getViewModel(foodtruckId);
        compositeDisposable.add(observable
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableObserver<FoodtruckViewerViewModel>() {
                    @Override
                    public void onNext(FoodtruckViewerViewModel foodtruckViewerViewModel) {
                        processData(foodtruckViewerViewModel);
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
    public void reloadData(String foodtruckId) {
        loadViewModel(foodtruckId, true);
    }

    @Override
    public void submitReview(String foodtruckId, String title, String content, float rating) {
        Review review = new Review();
        review.setTitle(title);
        review.setText(content);
        review.setRating(rating);
        compositeDisposable.add(model.submitReview(foodtruckId, review)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableObserver<Message>() {
                    @Override
                    public void onNext(Message message) {
                        postOnView(() ->
                                view.showSnackBar(R.string.foodtruck_my_review_submitted_message));
                    }

                    @Override
                    public void onError(Throwable e) {
                        Timber.e(e);
                        postOnView(() -> view.toast(e.getMessage()));
                        setRefreshing(false);
                        view.triggerDataRefresh();
                    }

                    @Override
                    public void onComplete() {
                        setRefreshing(false);
                        view.triggerDataRefresh();
                    }
                }));
    }

    @Override
    public void updateReview(String reviewId, String title, String content, float rating) {
        Review review = new Review();
        review.setTitle(title);
        review.setText(content);
        review.setRating(rating);
        compositeDisposable.add(model.updateReview(reviewId, review)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableObserver<Message>() {
                    @Override
                    public void onNext(Message message) {
                        postOnView(() ->
                                view.showSnackBar(R.string.foodtruck_my_review_updated_message));
                    }

                    @Override
                    public void onError(Throwable e) {
                        Timber.e(e);
                        postOnView(() -> view.toast(e.getMessage()));
                        setRefreshing(false);
                        view.triggerDataRefresh();
                    }

                    @Override
                    public void onComplete() {
                        setRefreshing(false);
                        view.triggerDataRefresh();
                    }
                }));
    }

    @Override
    public void removeReview(String reviewId) {
        setRefreshing(true);
        compositeDisposable.add(model.removeReview(reviewId)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableObserver<Message>() {
                    @Override
                    public void onNext(Message message) {
                        postOnView(() ->
                                view.showSnackBar(R.string.foodtruck_my_review_removed_message));
                    }

                    @Override
                    public void onError(Throwable e) {
                        Timber.e(e);
                        postOnView(() -> view.toast(e.getMessage()));
                        setRefreshing(false);
                        view.triggerDataRefresh();
                    }

                    @Override
                    public void onComplete() {
                        setRefreshing(false);
                        view.triggerDataRefresh();
                    }
                }));
    }

    @Override
    public void removeFoodtruck(String foodtruckId) {
        setRefreshing(true);
        compositeDisposable.add(model.removeFoodtruck(foodtruckId)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableObserver<Message>() {
                    @Override
                    public void onNext(Message message) {
                        postOnView(() -> view.showSnackBar(R.string.foodtruck_removed_message));
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
                        postOnView(() -> view.onBackPressed());
                    }
                }));
    }

    private void processData(@NonNull FoodtruckViewerViewModel viewModel) {
        postOnView(() -> {
            view.updateFoodtruck(viewModel.getFoodtruck());
            view.updateReviews(viewModel.getReviews());
            view.updateMyReview(viewModel.getMyReview());
        });
        Coordinates coordinates = viewModel.getFoodtruck().getCoordinates();
        MarkerOptions marker = new MarkerOptions()
                .position(new LatLng(coordinates.getLatitude(), coordinates.getLongitude()));
        locationManager.takeMarker(viewModel.getFoodtruck().getId(), marker);
        zoomOnLocation(coordinates.getLatitude(), coordinates.getLongitude());
    }
}
