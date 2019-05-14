package com.example.foodtruckclient.generic.mvp;

import androidx.annotation.NonNull;

import io.reactivex.disposables.CompositeDisposable;

public abstract class BasePresenter<T extends BaseMVP.View, S extends BaseMVP.Model>
        implements BaseMVP.Presenter<T> {

    protected CompositeDisposable compositeDisposable;
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
        compositeDisposable.clear();
        this.view = null;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        if (getPermissionManager() != null) {
            getPermissionManager().onRequestPermissionsResult(requestCode, permissions, grantResults);
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
}
