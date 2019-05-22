package com.example.foodtruckclient.screens.profile;

import androidx.annotation.Nullable;

import com.example.foodtruckclient.generic.mvp.BaseMVP;
import com.example.foodtruckclient.network.foodtruckapi.model.Account;
import com.example.foodtruckclient.network.foodtruckapi.model.Foodtruck;
import com.example.foodtruckclient.network.foodtruckapi.model.Message;

import java.io.File;
import java.util.Date;
import java.util.List;

import io.reactivex.Observable;
import okhttp3.MultipartBody;

public interface ProfileMVP {

    interface Model extends BaseMVP.Model<ProfileViewModel> {
        Observable<ProfileViewModel> getViewModel(String profileId);
        Observable<ProfileViewModel> getFreshViewModel(String profileId);
        Observable<Account> getAccount(String profileId);
        Observable<List<Foodtruck>> getFoodtrucks(String profileId);
        Observable<Message> removeProfilePicture();
        Observable<Message> uploadProfilePicture(MultipartBody.Part image);
    }

    interface View extends BaseMVP.View {
        void updateAccount(Account account);
        void updateFoodtrucks(List<Foodtruck> foodtrucks);
        void triggerDataRefresh();
        void triggerAccountRefresh();
        void setAuthenticatedAccountImage(@Nullable String imageUrl, Date lastUpdate);
    }

    interface Presenter extends BaseMVP.Presenter<View> {
        void loadViewModel(String profileId, boolean refresh);
        void reloadData(String profileId);
        void removeProfilePicture();
        void reloadAccount(String profileId);
        void uploadProfilePicture(File file, boolean fromCamera);
    }
}
