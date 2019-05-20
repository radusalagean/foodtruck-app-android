package com.example.foodtruckclient.screens.register;

import com.example.foodtruckclient.generic.mvp.BaseMVP;
import com.example.foodtruckclient.generic.viewmodel.BaseViewModel;
import com.example.foodtruckclient.network.foodtruckapi.model.Account;
import com.example.foodtruckclient.network.foodtruckapi.model.Message;

import io.reactivex.Completable;
import io.reactivex.Observable;

public interface RegisterMVP {

    interface Model extends BaseMVP.Model<BaseViewModel> {
        Observable<Message> register(Account account);
        Completable checkUsernameAvailability(String username);
    }

    interface View extends BaseMVP.View {
        void setUsernameAvailability(boolean available);
    }

    interface Presenter extends BaseMVP.Presenter<View> {
        void register(String userName, String password);
        void checkUsernameAvailability(String username);
    }
}
