package com.example.foodtruckclient.screens.login;

import com.example.foodtruckclient.authentication.AuthenticationRepository;
import com.example.foodtruckclient.generic.mvp.BaseModel;
import com.example.foodtruckclient.generic.viewmodel.BaseViewModel;
import com.example.foodtruckclient.generic.viewmodel.BaseViewModelRepository;
import com.example.foodtruckclient.network.NetworkConstants;
import com.example.foodtruckclient.network.NetworkRepository;
import com.example.foodtruckclient.network.foodtruckapi.model.Account;

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
