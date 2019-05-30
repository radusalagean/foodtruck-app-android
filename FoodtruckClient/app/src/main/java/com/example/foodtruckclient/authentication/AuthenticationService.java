package com.example.foodtruckclient.authentication;

import android.content.Intent;

import androidx.annotation.Nullable;

import com.example.foodtruckclient.generic.service.BaseIntentService;
import com.example.foodtruckclient.network.NetworkRepository;
import com.example.foodtruckclient.network.foodtruckapi.model.Account;

import javax.inject.Inject;

import timber.log.Timber;

public class AuthenticationService extends BaseIntentService {

    @Inject
    AuthenticationRepository authenticationRepository;

    @Inject
    NetworkRepository networkRepository;

    public AuthenticationService() {
        super(AuthenticationService.class.getSimpleName());
    }

    @Override
    public void onCreate() {
        getControllerComponent().inject(this);
        super.onCreate();
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        if (intent == null) {
            return;
        }
        if (AuthenticationConstants.ACTION_SYNC_USER_INFO.equals(intent.getAction())) {
            syncUserInfo();
        }
    }

    private void syncUserInfo() {
        Timber.d("syncUserInfo()");
        if (authenticationRepository.getAuthenticatedAccount() == null) {
            Timber.d("No authenticated user found, returning from syncUserInfo()");
            return;
        }
        try {
            Account account = networkRepository.me(null).blockingSingle();
            account.setToken(authenticationRepository.getAuthenticatedAccount().getToken());
            authenticationRepository.setAuthenticatedAccount(account);
            Timber.d("syncUserInfo() successful, sending broadcast");
            sendBroadcast(new Intent(AuthenticationConstants.ACTION_USER_INFO_SYNC_SUCCESSFUL));
        } catch (Exception e) {
            Timber.e(e);
            sendBroadcast(new Intent(AuthenticationConstants.ACTION_USER_INFO_SYNC_FAILED));
        }
    }
}
