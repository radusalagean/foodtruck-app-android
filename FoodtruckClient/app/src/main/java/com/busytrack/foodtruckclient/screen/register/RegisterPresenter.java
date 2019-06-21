package com.busytrack.foodtruckclient.screen.register;

import com.busytrack.foodtruckclient.R;
import com.busytrack.foodtruckclient.generic.mvp.BasePresenter;
import com.busytrack.foodtruckclient.generic.observer.ReactiveObserver;
import com.busytrack.foodtruckclient.network.foodtruckapi.model.Account;
import com.busytrack.foodtruckclient.network.foodtruckapi.model.Message;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.observers.DisposableCompletableObserver;
import timber.log.Timber;

public class RegisterPresenter extends BasePresenter<RegisterMVP.View, RegisterMVP.Model>
        implements RegisterMVP.Presenter {

    public RegisterPresenter(RegisterMVP.Model model) {
        super(model);
    }

    @Override
    public void register(String userName, String password) {
        Account account = new Account();
        account.setUsername(userName);
        account.setPassword(password);
        setRefreshing(true);
        compositeDisposable.add(model.register(account)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new ReactiveObserver<Message>(this) {
                    @Override
                    public void onNext(Message message) {
                        // nothing
                    }

                    @Override
                    public void onComplete() {
                        super.onComplete();
                        postOnView(() -> {
                            view.showSnackBar(R.string.registered_message, userName);
                            view.popFragment();
                        });
                    }
                }));
    }

    @Override
    public void checkUsernameAvailability(String username) {
        compositeDisposable.add(model.checkUsernameAvailability(username)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableCompletableObserver() {
                    @Override
                    public void onComplete() {
                        postOnView(() -> view.setUsernameAvailability(true));
                    }

                    @Override
                    public void onError(Throwable e) {
                        Timber.e(e);
                        postOnView(() -> view.setUsernameAvailability(false));
                    }
                }));
    }

    @Override
    public boolean restoreDataFromCache() {
        // nothing to do here
        return false;
    }
}
