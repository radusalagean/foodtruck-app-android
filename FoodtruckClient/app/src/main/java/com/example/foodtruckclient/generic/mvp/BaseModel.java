package com.example.foodtruckclient.generic.mvp;

import androidx.annotation.Nullable;

import com.example.foodtruckclient.generic.viewmodel.BaseViewModel;
import com.example.foodtruckclient.generic.viewmodel.BaseViewModelRepository;
import com.example.foodtruckclient.network.NetworkRepository;

public abstract class BaseModel<T extends BaseViewModel, S extends BaseViewModelRepository<T>>
        implements BaseMVP.Model<T> {

    protected NetworkRepository networkRepository;
    protected S viewModelRepository;

    public BaseModel(NetworkRepository networkRepository) {
        this.networkRepository = networkRepository;
    }

    public BaseModel(NetworkRepository networkRepository, S viewModelRepository) {
        this.networkRepository = networkRepository;
        this.viewModelRepository = viewModelRepository;
    }

    @Override
    @Nullable
    public T getCachedViewModel() {
        return viewModelRepository != null ? viewModelRepository.getViewModel() : null;
    }
}
