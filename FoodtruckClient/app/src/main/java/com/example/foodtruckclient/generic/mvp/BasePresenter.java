package com.example.foodtruckclient.generic.mvp;

import android.content.Context;

import androidx.annotation.NonNull;

import com.example.foodtruckclient.R;
import com.example.foodtruckclient.dialog.DialogManager;
import com.example.foodtruckclient.generic.viewmodel.ViewModelManager;
import com.example.foodtruckclient.permission.PermissionManager;
import com.example.foodtruckclient.permission.PermissionRequestDelegate;
import com.example.foodtruckclient.permission.PermissionRequestListener;

import java.util.UUID;

import io.reactivex.disposables.CompositeDisposable;

public abstract class BasePresenter<T extends BaseMVP.View, S extends BaseMVP.Model>
        implements BaseMVP.Presenter<T> {

    protected Context context;
    protected CompositeDisposable compositeDisposable;
    protected PermissionManager permissionManager;
    protected DialogManager dialogManager;
    protected ViewModelManager viewModelManager;
    protected T view;
    protected S model;
    private boolean refreshing;

    protected BasePresenter(S model) {
        compositeDisposable = new CompositeDisposable();
        this.model = model;
    }

    @Override
    public void postOnView(Runnable runnable) {
        if (view != null) {
            runnable.run();
        }
    }

    @Override
    public void takeView(T view) {
        this.view = view;
    }

    @Override
    public void dropView() {
        this.view = null;
    }

    @Override
    public void clearCompositeDisposable() {
        compositeDisposable.clear();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        if (permissionManager != null) {
            permissionManager.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }
    }

    @Override
    public void setRefreshing(boolean refreshing) {
        this.refreshing = refreshing;
        postOnView(() -> view.setRefreshingIndicator(refreshing));
    }

    @Override
    public boolean isRefreshing() {
        return refreshing;
    }

    @Override
    public void setUuid(UUID uuid) {
        model.setUuid(uuid);
    }

    @Override
    public void doOnPermissionGranted(String permission, int permissionDeniedMessage,
                                      PermissionRequestDelegate delegate, Runnable payload) {
        if (permissionManager.isPermissionGranted(permission)) {
            payload.run();
        } else {
            permissionManager.requestPermission(permission, delegate,
                    new PermissionRequestListener() {
                @Override
                public void onPermissionRequestGranted() {
                    payload.run();
                }

                @Override
                public void onPermissionRequestDenied() {
                    dialogManager.showBasicAlertDialog(
                            R.string.alert_dialog_permission_denied_title,
                            permissionDeniedMessage
                    );
                }

                @Override
                public void onPermissionRequestCanceled() {
                    dialogManager.showBasicAlertDialog(
                            R.string.alert_dialog_permission_canceled_title,
                            permissionDeniedMessage
                    );
                }
            });
        }
    }

    @Override
    public void handleInvalidationEffects() {
        // default empty implementation
        // override for screen-specific behavior handling
    }
}
