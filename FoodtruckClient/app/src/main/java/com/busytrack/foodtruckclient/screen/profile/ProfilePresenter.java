package com.busytrack.foodtruckclient.screen.profile;

import android.content.Context;

import com.busytrack.foodtruckclient.R;
import com.busytrack.foodtruckclient.authentication.AuthenticationConstants;
import com.busytrack.foodtruckclient.authentication.AuthenticationService;
import com.busytrack.foodtruckclient.dialog.DialogManager;
import com.busytrack.foodtruckclient.generic.contentinvalidation.ContentType;
import com.busytrack.foodtruckclient.generic.contentinvalidation.InvalidationBundle;
import com.busytrack.foodtruckclient.generic.contentinvalidation.InvalidationEffect;
import com.busytrack.foodtruckclient.generic.contentinvalidation.InvalidationType;
import com.busytrack.foodtruckclient.generic.mvp.BasePresenter;
import com.busytrack.foodtruckclient.generic.observer.ReactiveObserver;
import com.busytrack.foodtruckclient.generic.viewmodel.ViewModelManager;
import com.busytrack.foodtruckclient.network.NetworkConstants;
import com.busytrack.foodtruckclient.network.foodtruckapi.model.Account;
import com.busytrack.foodtruckclient.network.foodtruckapi.model.Foodtruck;
import com.busytrack.foodtruckclient.network.foodtruckapi.model.Message;
import com.busytrack.foodtruckclient.permission.PermissionManager;
import com.busytrack.foodtruckclient.util.ImageUtils;

import java.io.File;
import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import okhttp3.MultipartBody;

public class ProfilePresenter extends BasePresenter<ProfileMVP.View, ProfileMVP.Model>
        implements ProfileMVP.Presenter {

    public ProfilePresenter(ProfileMVP.Model model,
                            PermissionManager permissionManager,
                            DialogManager dialogManager,
                            ViewModelManager viewModelManager,
                            Context context) {
        super(model);
        this.permissionManager = permissionManager;
        this.dialogManager = dialogManager;
        this.viewModelManager = viewModelManager;
        this.context = context;
    }

    @Override
    public void loadViewModel(String profileId) {
        setRefreshing(true);
        compositeDisposable.add(model.getViewModel(profileId)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new ReactiveObserver<ProfileViewModel>(this) {
                    @Override
                    public void onNext(ProfileViewModel viewModel) {
                        postOnView(() -> {
                            view.updateAccount(viewModel.getAccount());
                            view.updateFoodtrucks(viewModel.getFoodtrucks());
                        });
                    }
                }));
    }

    @Override
    public void removeProfilePicture() {
        setRefreshing(true);
        compositeDisposable.add(model.removeProfilePicture()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new ReactiveObserver<Message>(this) {
                    @Override
                    public void onNext(Message message) {
                        postOnView(() -> view.showSnackBar(R.string.message_profile_image_removed));
                    }

                    @Override
                    public void onComplete() {
                        super.onComplete();
                        reloadAccount(model.getCachedViewModel().getAccount().getId());
                        reloadFoodtrucks(model.getCachedViewModel().getAccount().getId());
                        viewModelManager.sendInvalidationBundle(new InvalidationBundle(
                                model.getCachedViewModel().getAccount().getId(),
                                ContentType.PROFILE,
                                InvalidationType.RELOAD
                        ), model.getUuid());
                        AuthenticationService.startService(context,
                                AuthenticationConstants.ACTION_SYNC_USER_INFO);
                    }
                }));
    }

    @Override
    public void reloadAccount(String profileId) {
        setRefreshing(true);
        compositeDisposable.add(model.getAccount(profileId)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new ReactiveObserver<Account>(this) {
                    @Override
                    public void onNext(Account account) {
                        postOnView(() -> view.updateAccount(account));
                    }
                }));
    }

    @Override
    public void reloadFoodtrucks(String profileId) {
        setRefreshing(true);
        compositeDisposable.add(model.getFoodtrucks(profileId)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new ReactiveObserver<List<Foodtruck>>(this) {
                    @Override
                    public void onNext(List<Foodtruck> foodtrucks) {
                        postOnView(() -> view.updateFoodtrucks(foodtrucks));
                    }
                }));
    }

    @Override
    public void uploadProfilePicture(File file) {
        if (!file.exists()) {
            return;
        }
        if (file.length() > NetworkConstants.MAX_FILE_SIZE) {
            postOnView(() -> view.showSnackBar(R.string.message_file_size_exceeded));
            return;
        }
        MultipartBody.Part part = ImageUtils.createPartFromImageFile(file);
        setRefreshing(true);
        compositeDisposable.add(model.uploadProfilePicture(part)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new ReactiveObserver<Message>(this) {
                    @Override
                    public void onNext(Message message) {
                        postOnView(() -> view.showSnackBar(R.string.message_profile_image_uploaded));
                    }

                    @Override
                    public void onComplete() {
                        super.onComplete();
                        reloadAccount(model.getCachedViewModel().getAccount().getId());
                        reloadFoodtrucks(model.getCachedViewModel().getAccount().getId());
                        viewModelManager.sendInvalidationBundle(new InvalidationBundle(
                                model.getCachedViewModel().getAccount().getId(),
                                ContentType.PROFILE,
                                InvalidationType.RELOAD
                        ), model.getUuid());
                        AuthenticationService.startService(context,
                                AuthenticationConstants.ACTION_SYNC_USER_INFO);
                    }
                }));
    }

    @Override
    public void handleInvalidationEffects() {
        if (model.getCachedViewModel() != null) {
            int invalidationEffects = model.getCachedViewModel().getInvalidationEffects();
            if ((invalidationEffects & InvalidationEffect.POP_FRAGMENT) != 0) {
                view.popFragment();
                return;
            }
            if ((invalidationEffects & InvalidationEffect.PROFILE_RELOAD) != 0) {
                reloadAccount(model.getCachedViewModel().getAccount().getId());
            }
            if ((invalidationEffects & InvalidationEffect.FOODTRUCK_RELOAD) != 0) {
                reloadFoodtrucks(model.getCachedViewModel().getAccount().getId());
            }
            model.getCachedViewModel().clearInvalidationEffects();
        }
    }

    @Override
    public boolean restoreDataFromCache() {
        if (model.getCachedViewModel() == null) {
            return false;
        }
        postOnView(() -> {
            view.updateAccount(model.getCachedViewModel().getAccount());
            view.updateFoodtrucks(model.getCachedViewModel().getFoodtrucks());
        });
        handleInvalidationEffects();
        return true;
    }
}
