package com.busytrack.foodtruckclient.screen.register;

import com.busytrack.foodtruckclient.generic.mvp.BaseModel;
import com.busytrack.foodtruckclient.generic.viewmodel.BaseViewModel;
import com.busytrack.foodtruckclient.generic.viewmodel.BaseViewModelRepository;
import com.busytrack.foodtruckclient.network.NetworkRepository;
import com.busytrack.foodtruckclient.network.foodtruckapi.model.Account;
import com.busytrack.foodtruckclient.network.foodtruckapi.model.Message;

import io.reactivex.Completable;
import io.reactivex.Observable;

public class RegisterModel extends BaseModel<BaseViewModel, BaseViewModelRepository<BaseViewModel>>
        implements RegisterMVP.Model {

    public RegisterModel(NetworkRepository networkRepository) {
        super(networkRepository);
    }

    @Override
    public Observable<Message> register(Account account) {
        return networkRepository.register(account);
    }

    @Override
    public Completable checkUsernameAvailability(String username) {
        return networkRepository.checkUsernameAvailability(username);
    }
}
