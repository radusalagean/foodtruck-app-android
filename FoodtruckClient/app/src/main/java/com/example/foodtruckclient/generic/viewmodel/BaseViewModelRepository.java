package com.example.foodtruckclient.generic.viewmodel;

import timber.log.Timber;

public abstract class BaseViewModelRepository<T extends BaseViewModel> {

    protected T viewModel;

    public T getViewModel() {
        return viewModel;
    }

    public void setViewModel(T viewModel) {
        Timber.w("setViewModel");
        this.viewModel = viewModel;
    }
}
