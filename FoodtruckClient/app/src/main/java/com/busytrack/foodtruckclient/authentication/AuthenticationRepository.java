package com.busytrack.foodtruckclient.authentication;

import androidx.annotation.Nullable;

import com.busytrack.foodtruckclient.network.foodtruckapi.model.Account;
import com.busytrack.foodtruckclient.persistence.SharedPreferencesRepository;

public class AuthenticationRepository {

    private SharedPreferencesRepository sharedPreferencesRepository;
    private Account authenticatedAccount;

    public AuthenticationRepository(SharedPreferencesRepository sharedPreferencesRepository) {
        this.sharedPreferencesRepository = sharedPreferencesRepository;
    }

    @Nullable
    public Account getAuthenticatedAccount() {
        if (authenticatedAccount == null) {
            authenticatedAccount = sharedPreferencesRepository.getAuthenticatedAccount();
        }
        return authenticatedAccount;
    }

    public void setAuthenticatedAccount(Account authenticatedAccount) {
        clearAuthenticatedAccount();
        this.authenticatedAccount = authenticatedAccount;
        sharedPreferencesRepository.updateAuthenticatedAccount(authenticatedAccount);
    }

    public void clearAuthenticatedAccount() {
        authenticatedAccount = null;
        sharedPreferencesRepository.clearAuthenticatedAccount();
    }
}
