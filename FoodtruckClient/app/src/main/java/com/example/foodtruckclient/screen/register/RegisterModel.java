package com.example.foodtruckclient.screen.register;

import com.example.foodtruckclient.generic.mvp.BaseModel;
import com.example.foodtruckclient.generic.viewmodel.BaseViewModel;
import com.example.foodtruckclient.generic.viewmodel.BaseViewModelRepository;
import com.example.foodtruckclient.network.NetworkRepository;
import com.example.foodtruckclient.network.foodtruckapi.model.Account;
import com.example.foodtruckclient.network.foodtruckapi.model.Message;

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
