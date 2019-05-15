package com.example.foodtruckclient.persistence;

import android.content.SharedPreferences;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.foodtruckclient.network.foodtruckapi.model.Account;

public class SharedPreferencesRepository {

    private SharedPreferences sharedPreferences;

    public SharedPreferencesRepository(SharedPreferences sharedPreferences) {
        this.sharedPreferences = sharedPreferences;
    }

    public void updateAuthenticatedAccount(@NonNull Account account) {
        sharedPreferences.edit()
                .putString(SharedPreferencesConstants.PREF_ACCOUNT_ID, account.getId())
                .putString(SharedPreferencesConstants.PREF_ACCOUNT_USERNAME, account.getUsername())
                .putString(SharedPreferencesConstants.PREF_ACCOUNT_IMAGE, account.getImage())
                .putString(SharedPreferencesConstants.PREF_ACCOUNT_TOKEN, account.getToken())
                .apply();
    }

    public void clearAuthenticatedAccount() {
        sharedPreferences.edit()
                .remove(SharedPreferencesConstants.PREF_ACCOUNT_ID)
                .remove(SharedPreferencesConstants.PREF_ACCOUNT_USERNAME)
                .remove(SharedPreferencesConstants.PREF_ACCOUNT_IMAGE)
                .remove(SharedPreferencesConstants.PREF_ACCOUNT_TOKEN)
                .apply();
    }

    @Nullable
    public Account getAuthenticatedAccount() {
        String id = sharedPreferences.getString(SharedPreferencesConstants.PREF_ACCOUNT_ID, null);
        if (id == null) {
            return null;
        }
        Account account = new Account();
        account.setId(id);
        account.setUsername(sharedPreferences.getString(SharedPreferencesConstants.PREF_ACCOUNT_USERNAME, null));
        account.setImage(sharedPreferences.getString(SharedPreferencesConstants.PREF_ACCOUNT_IMAGE, null));
        account.setToken(sharedPreferences.getString(SharedPreferencesConstants.PREF_ACCOUNT_TOKEN, null));
        return account;
    }
}
