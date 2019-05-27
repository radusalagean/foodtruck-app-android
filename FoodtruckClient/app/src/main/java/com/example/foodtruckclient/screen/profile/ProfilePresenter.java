package com.example.foodtruckclient.screen.profile;

import android.content.Context;

import com.example.foodtruckclient.R;
import com.example.foodtruckclient.dialog.DialogManager;
import com.example.foodtruckclient.generic.mvp.BasePresenter;
import com.example.foodtruckclient.network.NetworkConstants;
import com.example.foodtruckclient.network.foodtruckapi.model.Account;
import com.example.foodtruckclient.network.foodtruckapi.model.Message;
import com.example.foodtruckclient.permission.PermissionManager;
import com.example.foodtruckclient.util.ImageUtils;

import java.io.File;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.observers.DisposableObserver;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import timber.log.Timber;

public class ProfilePresenter extends BasePresenter<ProfileMVP.View, ProfileMVP.Model>
        implements ProfileMVP.Presenter {

    public ProfilePresenter(ProfileMVP.Model model,
                            PermissionManager permissionManager,
                            DialogManager dialogManager,
                            Context context) {
        super(model);
        this.permissionManager = permissionManager;
        this.dialogManager = dialogManager;
        this.context = context;
    }

    @Override
    public void loadViewModel(String profileId, boolean refresh) {
        setRefreshing(true);
        Observable<ProfileViewModel> observable = refresh ?
                model.getFreshViewModel(profileId) : model.getViewModel(profileId);
        compositeDisposable.add(observable
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
    public void reloadData(String profileId) {
        loadViewModel(profileId, true);
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
                        postOnView(() -> view.triggerAccountRefresh());
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
                        postOnView(() -> view.triggerAccountRefresh());
                    }
                }));
    }
}
