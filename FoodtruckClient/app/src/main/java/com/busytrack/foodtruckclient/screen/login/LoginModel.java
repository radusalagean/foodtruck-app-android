package com.busytrack.foodtruckclient.screen.login;

import com.busytrack.foodtruckclient.authentication.AuthenticationRepository;
import com.busytrack.foodtruckclient.generic.mvp.BaseModel;
import com.busytrack.foodtruckclient.generic.viewmodel.BaseViewModel;
import com.busytrack.foodtruckclient.generic.viewmodel.BaseViewModelRepository;
import com.busytrack.foodtruckclient.network.NetworkConstants;
import com.busytrack.foodtruckclient.network.NetworkRepository;
import com.busytrack.foodtruckclient.network.foodtruckapi.model.Account;

import io.reactivex.Observable;

public class LoginModel extends BaseModel<BaseViewModel, BaseViewModelRepository<BaseViewModel>>
        implements LoginMVP.Model {

    private AuthenticationRepository authenticationRepository;

    public LoginModel(NetworkRepository networkRepository,
                      AuthenticationRepository authenticationRepository) {
        super(networkRepository);
        this.authenticationRepository = authenticationRepository;
    }

    @Override
    public Observable<Account> login(Account account) {
        return networkRepository.login(account)
                .flatMap((loginResponse -> networkRepository.me(
                        NetworkConstants.getAuthorizationHeader(loginResponse.getToken())
                    )), Account::createFrom)
                .doOnNext(processedAccount -> authenticationRepository
                        .setAuthenticatedAccount(processedAccount));
    }
}
