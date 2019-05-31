package com.example.foodtruckclient.screen.profile;

import android.content.Context;

import com.example.foodtruckclient.R;
import com.example.foodtruckclient.authentication.AuthenticationConstants;
import com.example.foodtruckclient.authentication.AuthenticationService;
import com.example.foodtruckclient.dialog.DialogManager;
import com.example.foodtruckclient.generic.contentinvalidation.ContentType;
import com.example.foodtruckclient.generic.contentinvalidation.InvalidationBundle;
import com.example.foodtruckclient.generic.contentinvalidation.InvalidationEffect;
import com.example.foodtruckclient.generic.contentinvalidation.InvalidationType;
import com.example.foodtruckclient.generic.mvp.BasePresenter;
import com.example.foodtruckclient.generic.viewmodel.ViewModelManager;
import com.example.foodtruckclient.network.NetworkConstants;
import com.example.foodtruckclient.network.foodtruckapi.model.Account;
import com.example.foodtruckclient.network.foodtruckapi.model.Foodtruck;
import com.example.foodtruckclient.network.foodtruckapi.model.Message;
import com.example.foodtruckclient.permission.PermissionManager;
import com.example.foodtruckclient.util.ImageUtils;

import java.io.File;
import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.observers.DisposableObserver;
import okhttp3.MultipartBody;
import timber.log.Timber;

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
                .subscribeWith(new DisposableObserver<ProfileViewModel>() {
                    @Override
                    public void onNext(ProfileViewModel viewModel) {
                        postOnView(() -> {
                            view.updateAccount(viewModel.getAccount());
                            view.updateFoodtrucks(viewModel.getFoodtrucks());
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
    public void removeProfilePicture() {
        setRefreshing(true);
        compositeDisposable.add(model.removeProfilePicture()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableObserver<Message>() {
                    @Override
                    public void onNext(Message message) {
                        postOnView(() -> view.showSnackBar(R.string.message_profile_image_removed));
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
                .subscribeWith(new DisposableObserver<Account>() {
                    @Override
                    public void onNext(Account account) {
                        postOnView(() -> view.updateAccount(account));
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
    public void reloadFoodtrucks(String profileId) {
        setRefreshing(true);
        compositeDisposable.add(model.getFoodtrucks(profileId)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableObserver<List<Foodtruck>>() {
                    @Override
                    public void onNext(List<Foodtruck> foodtrucks) {
                        postOnView(() -> view.updateFoodtrucks(foodtrucks));
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
                .subscribeWith(new DisposableObserver<Message>() {
                    @Override
                    public void onNext(Message message) {
                        postOnView(() -> view.showSnackBar(R.string.message_profile_image_uploaded));
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
