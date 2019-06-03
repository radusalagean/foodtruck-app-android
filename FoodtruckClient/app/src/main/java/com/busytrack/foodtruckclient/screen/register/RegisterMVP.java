package com.busytrack.foodtruckclient.screen.register;

import com.busytrack.foodtruckclient.generic.mvp.BaseMVP;
import com.busytrack.foodtruckclient.generic.viewmodel.BaseViewModel;
import com.busytrack.foodtruckclient.network.foodtruckapi.model.Account;
import com.busytrack.foodtruckclient.network.foodtruckapi.model.Message;

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
