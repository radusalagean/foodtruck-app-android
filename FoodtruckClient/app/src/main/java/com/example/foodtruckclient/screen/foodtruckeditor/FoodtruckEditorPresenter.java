package com.example.foodtruckclient.screen.foodtruckeditor;

import com.example.foodtruckclient.R;
import com.example.foodtruckclient.dialog.DialogManager;
import com.example.foodtruckclient.generic.contentinvalidation.ContentType;
import com.example.foodtruckclient.generic.contentinvalidation.InvalidationBundle;
import com.example.foodtruckclient.generic.contentinvalidation.InvalidationEffect;
import com.example.foodtruckclient.generic.contentinvalidation.InvalidationType;
import com.example.foodtruckclient.generic.image.ImageBundle;
import com.example.foodtruckclient.generic.mapmvp.BaseMapPresenter;
import com.example.foodtruckclient.generic.viewmodel.ViewModelManager;
import com.example.foodtruckclient.location.LocationManager;
import com.example.foodtruckclient.network.foodtruckapi.model.Coordinates;
import com.example.foodtruckclient.network.foodtruckapi.model.Foodtruck;
import com.example.foodtruckclient.permission.PermissionManager;
import com.example.foodtruckclient.util.ImageUtils;
import com.example.foodtruckclient.util.StringUtils;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import io.reactivex.Completable;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.observers.DisposableCompletableObserver;
import okhttp3.MultipartBody;
import timber.log.Timber;

public class FoodtruckEditorPresenter extends BaseMapPresenter<FoodtruckEditorMVP.View, FoodtruckEditorMVP.Model>
        implements FoodtruckEditorMVP.Presenter {

    public FoodtruckEditorPresenter(FoodtruckEditorMVP.Model model,
                                    LocationManager locationManager,
                                    PermissionManager permissionManager,
                                    DialogManager dialogManager,
                                    ViewModelManager viewModelManager) {
        super(model, locationManager, permissionManager, dialogManager);
        this.viewModelManager = viewModelManager;
    }

    @Override
    public boolean isInEditMode() {
        return model.getOriginalFoodtruck() != null;
    }

    @Override
    public Foodtruck getFoodtruck() {
        return model.getOriginalFoodtruck();
    }

    @Override
    public void setFoodtruck(Foodtruck foodtruck) {
        model.setOriginalFoodtruck(foodtruck);
    }

    @Override
    public String getTypedName() {
        return model.getViewModel().getTypedName();
    }

    @Override
    public void setTypedName(String typedName) {
        model.getViewModel().setTypedName(typedName);
    }

    @Override
    public String getTypedFoodtype() {
        return model.getViewModel().getTypedFoodtype();
    }

    @Override
    public void setTypedFoodtype(String typedFoodtype) {
        model.getViewModel().setTypedFoodtype(typedFoodtype);
    }

    @Override
    public List<String> getAddedFoodtypes() {
        return model.getViewModel().getAddedFoodtypes();
    }

    @Override
    public void setAddedFoodtypes(List<String> addedFoodtypes) {
        model.getViewModel().setAddedFoodtypes(addedFoodtypes);
    }

    @Override
    public Coordinates getCoordinates() {
        return model.getViewModel().getCoordinates();
    }

    @Override
    public void setCoordinates(Coordinates coordinates) {
        model.getViewModel().setCoordinates(coordinates);
    }

    @Override
    public ImageBundle getImageBundle() {
        return model.getViewModel().getImageBundle();
    }

    @Override
    public void setImageBundle(ImageBundle imageBundle) {
        model.getViewModel().setImageBundle(imageBundle);
    }

    @Override
    public File getImageFile() {
        return model.getViewModel().getImageFile();
    }

    @Override
    public void setImageFile(File file) {
        model.getViewModel().setImageFile(file);
    }

    @Override
    public void onSaveButtonClicked() {
        Foodtruck foodtruck = new Foodtruck();
        foodtruck.setName(model.getViewModel().getTypedName());
        foodtruck.setFoodtypes(model.getViewModel().getAddedFoodtypes());
        foodtruck.setCoordinates(model.getViewModel().getCoordinates());
        boolean updateFoodtruck = model.getOriginalFoodtruck() != null;
        boolean imageAvailableForUpload = model.getViewModel().getImageFile() != null;
        boolean removeImage = updateFoodtruck &&
                model.getOriginalFoodtruck().getImage() != null &&
                model.getViewModel().getImageBundle() == null &&
                model.getViewModel().getImageFile() == null;
        List<Completable> completables = new ArrayList<>();
        if (updateFoodtruck) {
            // Update Foodtruck
            String foodtruckId = model.getOriginalFoodtruck().getId();
            completables.add(model.updateFoodtruck(foodtruckId, foodtruck).ignoreElements());
            if (removeImage) {
                completables.add(model.deleteFoodtruckImage(foodtruckId).ignoreElements());
            } else if (imageAvailableForUpload) {
                MultipartBody.Part part = ImageUtils.createPartFromImageFile(
                        model.getViewModel().getImageFile()
                );
                completables.add(model.uploadFoodtruckImage(foodtruckId, part).ignoreElements());
            }
        } else {
            // Add Foodtruck
            Observable<Foodtruck> addFoodtruckObservable = model.addFoodtruck(foodtruck);
            if (imageAvailableForUpload) {
                completables.add(addFoodtruckObservable.flatMap(addedFoodtruck -> {
                    MultipartBody.Part part = ImageUtils.createPartFromImageFile(
                            model.getViewModel().getImageFile()
                    );
                    return model.uploadFoodtruckImage(addedFoodtruck.getId(), part);
                }).ignoreElements());
            } else {
                completables.add(addFoodtruckObservable.ignoreElements());
            }
        }
        Timber.d("Sending foodtruck to server [update: %b, imageUpload: %b, imageRemove: %b]",
                updateFoodtruck, imageAvailableForUpload, removeImage);
        setRefreshing(true);
        compositeDisposable.add(Completable.concat(completables)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableCompletableObserver() {
                    @Override
                    public void onComplete() {
                        setRefreshing(false);
                        viewModelManager.sendInvalidationBundle(new InvalidationBundle(
                                updateFoodtruck ? getFoodtruck().getId() : StringUtils.emptyString(),
                                ContentType.FOODTRUCK,
                                InvalidationType.RELOAD
                        ), model.getUuid());
                        postOnView(() -> {
                            view.showSnackBar(
                                    updateFoodtruck ?
                                            R.string.foodtruck_editor_foodtruck_edited_message :
                                            R.string.foodtruck_editor_foodtruck_added_message
                            );
                            view.popFragment();
                        });
                    }

                    @Override
                    public void onError(Throwable e) {
                        Timber.e(e);
                        postOnView(() -> view.toast(e.getMessage()));
                        setRefreshing(false);
                    }
                })
        );
    }

    @Override
    public void handleInvalidationEffects() {
        if (model.getCachedViewModel() != null) {
            int invalidationEffects = model.getCachedViewModel().getInvalidationEffects();
            if ((invalidationEffects & InvalidationEffect.POP_FRAGMENT) != 0) {
                view.popFragment();
            }
            model.getCachedViewModel().clearInvalidationEffects();
        }
    }

    @Override
    public boolean restoreDataFromCache() {
        // nothing to do here
        return false;
    }
}
