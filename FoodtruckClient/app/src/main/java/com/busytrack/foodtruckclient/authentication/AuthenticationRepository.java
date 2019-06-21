package com.busytrack.foodtruckclient.authentication;

import androidx.annotation.Nullable;

import com.busytrack.foodtruckclient.network.foodtruckapi.model.Account;
import com.busytrack.foodtruckclient.persistence.SharedPreferencesRepository;

/**
 * Provides access to the account of the currently authenticated user.<br>
 * Initially, it loads the account from persistent storage (Shared Preferences) and caches it in
 * memory for quicker access during the app's lifetime.
 */
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
