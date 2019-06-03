package com.busytrack.foodtruckclient.screen.login;

import com.busytrack.foodtruckclient.dialog.DialogManager;
import com.busytrack.foodtruckclient.generic.mvp.BasePresenter;
import com.busytrack.foodtruckclient.network.foodtruckapi.model.Account;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.observers.DisposableObserver;
import timber.log.Timber;

public class LoginPresenter extends BasePresenter<LoginMVP.View, LoginMVP.Model>
        implements LoginMVP.Presenter {

    public LoginPresenter(LoginMVP.Model model,
                          DialogManager dialogManager) {
        super(model);
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
                        postOnView(() ->
                            view.setAuthenticatedAccount(account));
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

    @Override
    public boolean restoreDataFromCache() {
        // nothing to do here
        return false;
    }
}
