package com.example.foodtruckclient.screens.login;

import androidx.annotation.NonNull;

import com.example.foodtruckclient.generic.mvp.BaseMVP;
import com.example.foodtruckclient.generic.viewmodel.BaseViewModel;
import com.example.foodtruckclient.network.foodtruckapi.model.Account;

import io.reactivex.Observable;

public interface LoginMVP {

    interface Model extends BaseMVP.Model<BaseViewModel> {
        Observable<Account> login(Account account);
    }

    interface View extends BaseMVP.View {
        void setAuthenticatedAccount(@NonNull Account account);
    }

    interface Presenter extends BaseMVP.Presenter<View> {
        void login(String username, String password);
    }
}
