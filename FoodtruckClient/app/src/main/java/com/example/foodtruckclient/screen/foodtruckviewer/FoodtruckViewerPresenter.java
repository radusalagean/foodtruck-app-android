package com.example.foodtruckclient.screen.foodtruckviewer;

import androidx.annotation.NonNull;

import com.example.foodtruckclient.R;
import com.example.foodtruckclient.dialog.DialogManager;
import com.example.foodtruckclient.generic.contentinvalidation.ContentType;
import com.example.foodtruckclient.generic.contentinvalidation.InvalidationBundle;
import com.example.foodtruckclient.generic.contentinvalidation.InvalidationEffect;
import com.example.foodtruckclient.generic.contentinvalidation.InvalidationType;
import com.example.foodtruckclient.generic.mapmvp.BaseMapPresenter;
import com.example.foodtruckclient.generic.viewmodel.ViewModelManager;
import com.example.foodtruckclient.location.LocationManager;
import com.example.foodtruckclient.network.foodtruckapi.model.Coordinates;
import com.example.foodtruckclient.network.foodtruckapi.model.Foodtruck;
import com.example.foodtruckclient.network.foodtruckapi.model.Message;
import com.example.foodtruckclient.network.foodtruckapi.model.Review;
import com.example.foodtruckclient.permission.PermissionManager;
import com.example.foodtruckclient.util.NumberUtils;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.observers.DisposableObserver;
import timber.log.Timber;

public class FoodtruckViewerPresenter extends BaseMapPresenter<FoodtruckViewerMVP.View, FoodtruckViewerMVP.Model>
        implements FoodtruckViewerMVP.Presenter {

    public FoodtruckViewerPresenter(FoodtruckViewerMVP.Model model,
                                    LocationManager locationManager,
                                    PermissionManager permissionManager,
                                    DialogManager dialogManager,
                                    ViewModelManager viewModelManager) {
        super(model, locationManager, permissionManager, dialogManager);
        this.viewModelManager = viewModelManager;
    }

    @Override
    public void loadViewModel(String foodtruckId) {
        setRefreshing(true);
        compositeDisposable.add(model.getViewModel(foodtruckId)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableObserver<FoodtruckViewerViewModel>() {
                    @Override
                    public void onNext(FoodtruckViewerViewModel foodtruckViewerViewModel) {
                        processFoodtruck(foodtruckViewerViewModel.getFoodtruck(), false);
                        postOnView(() -> {
                            view.updateMyReview(foodtruckViewerViewModel.getMyReview());
                            view.updateReviews(foodtruckViewerViewModel.getReviews());
                        });
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
    public void reloadFoodtruck(String foodtruckId) {
        setRefreshing(true);
        compositeDisposable.add(model.getFoodtruck(foodtruckId)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableObserver<Foodtruck>() {
                    @Override
                    public void onNext(Foodtruck foodtruck) {
                        processFoodtruck(foodtruck, false);
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
    public void reloadAllReviews(String foodtruckId) {
        setRefreshing(true);
        compositeDisposable.add(model.getAllReviews(foodtruckId)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableObserver<List<Review>>() {
                    @Override
                    public void onNext(List<Review> reviews) {
                        postOnView(() -> view.updateReviews(reviews));
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
    public void reloadMyReview(String foodtruckId) {
        setRefreshing(false);
        compositeDisposable.add(model.getMyReview(foodtruckId)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableObserver<Review>() {
                    @Override
                    public void onNext(Review review) {
                        postOnView(() -> view.updateMyReview(review));
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
                    }

                    @Override
                    public void onComplete() {
                        setRefreshing(false);
                        loadViewModel(foodtruckId);
                        viewModelManager.sendInvalidationBundle(new InvalidationBundle(
                                foodtruckId,
                                ContentType.REVIEW,
                                InvalidationType.RELOAD
                        ), model.getUuid());
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
                    }

                    @Override
                    public void onComplete() {
                        setRefreshing(false);
                        loadViewModel(model.getCachedViewModel().getFoodtruck().getId());
                        viewModelManager.sendInvalidationBundle(new InvalidationBundle(
                                model.getCachedViewModel().getFoodtruck().getId(),
                                ContentType.REVIEW,
                                InvalidationType.RELOAD
                        ), model.getUuid());
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
                    }

                    @Override
                    public void onComplete() {
                        setRefreshing(false);
                        loadViewModel(model.getCachedViewModel().getFoodtruck().getId());
                        viewModelManager.sendInvalidationBundle(new InvalidationBundle(
                                model.getCachedViewModel().getFoodtruck().getId(),
                                ContentType.REVIEW,
                                InvalidationType.RELOAD
                        ), model.getUuid());
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
                        postOnView(() -> view.popFragment());
                        viewModelManager.sendInvalidationBundle(new InvalidationBundle(
                                foodtruckId,
                                ContentType.FOODTRUCK,
                                InvalidationType.REMOVE
                        ), model.getUuid());
                    }
                }));
    }

    private void processFoodtruck(@NonNull Foodtruck foodtruck, boolean restoredFromCache) {
        postOnView(() ->
            view.updateFoodtruck(foodtruck));
        Coordinates coordinates = foodtruck.getCoordinates();
        Coordinates oldCoordinates = locationManager
                .getMarkerCoordinates(foodtruck.getId());
        // Check if old coordinates are different the new ones
        if (!coordinates.equals(oldCoordinates)) {
            MarkerOptions marker = new MarkerOptions()
                    .position(new LatLng(coordinates.getLatitude(), coordinates.getLongitude()));
            locationManager.takeMarker(foodtruck.getId(), marker);
            zoomOnLocation(coordinates.getLatitude(), coordinates.getLongitude(), restoredFromCache);
        }
    }

    @Override
    public void handleInvalidationEffects() {
        if (model.getCachedViewModel() != null) {
            int invalidationEffects = model.getCachedViewModel().getInvalidationEffects();
            Timber.d("handleInvalidationEffects: %s",
                    NumberUtils.convertIntToBinaryString(invalidationEffects));
            if ((invalidationEffects & InvalidationEffect.POP_FRAGMENT) != 0) {
                view.popFragment();
                return;
            }
            if ((invalidationEffects & InvalidationEffect.FOODTRUCK_RELOAD) != 0) {
                reloadFoodtruck(model.getCachedViewModel().getFoodtruck().getId());
            }
            if ((invalidationEffects & InvalidationEffect.REVIEW_RELOAD) != 0) {
                loadViewModel(model.getCachedViewModel().getFoodtruck().getId());
            }
            model.getCachedViewModel().clearInvalidationEffects();
        }
    }

    @Override
    public boolean restoreDataFromCache() {
        if (model.getCachedViewModel() == null) {
            return false;
        }
        processFoodtruck(model.getCachedViewModel().getFoodtruck(), true);
        postOnView(() -> {
            view.updateReviews(model.getCachedViewModel().getReviews());
            view.updateMyReview(model.getCachedViewModel().getMyReview());
        });
        handleInvalidationEffects();
        return true;
    }
}
