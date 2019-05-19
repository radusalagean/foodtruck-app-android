package com.example.foodtruckclient.screens.login;

import android.content.Context;

import com.example.foodtruckclient.R;
import com.example.foodtruckclient.dialog.DialogManager;
import com.example.foodtruckclient.generic.mvp.BasePresenter;
import com.example.foodtruckclient.network.foodtruckapi.model.Account;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.observers.DisposableObserver;
import timber.log.Timber;

public class LoginPresenter extends BasePresenter<LoginMVP.View, LoginMVP.Model>
        implements LoginMVP.Presenter {

    private Context context;
    private DialogManager dialogManager;

    public LoginPresenter(LoginMVP.Model model,
                          Context context,
                          DialogManager dialogManager) {
        super(model);
        this.context = context;
        this.dialogManager = dialogManager;
    }

    @Override
    public void login(String username, String password) {
        Account account = new Account();
        account.setUsername(username);
        account.setPassword(password);
        setRefreshing(true);
        compositeDisposable.add(model.login(account)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableObserver<Account>() {
                    @Override
                    public void onNext(Account account) {
                        postOnView(() -> {
                            view.setAuthenticatedAccount(account);
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
}
