package com.example.foodtruckclient.screens.profile;

import com.example.foodtruckclient.generic.mvp.BaseModel;
import com.example.foodtruckclient.network.NetworkRepository;
import com.example.foodtruckclient.network.foodtruckapi.model.Account;
import com.example.foodtruckclient.network.foodtruckapi.model.Foodtruck;
import com.example.foodtruckclient.network.foodtruckapi.model.Message;

import java.util.List;

import io.reactivex.Observable;
import okhttp3.MultipartBody;

public class ProfileModel extends BaseModel<ProfileViewModel, ProfileViewModelRepository>
        implements ProfileMVP.Model {

    public ProfileModel(NetworkRepository networkRepository,
                        ProfileViewModelRepository viewModelRepository) {
        super(networkRepository, viewModelRepository);
    }


    @Override
    public Observable<ProfileViewModel> getViewModel(String profileId) {
        ProfileViewModel cachedViewModel = getCachedViewModel();
        if (cachedViewModel != null) {
            return Observable.just(cachedViewModel);
        }
        return getFreshViewModel(profileId);
    }

    @Override
    public Observable<ProfileViewModel> getFreshViewModel(String profileId) {
        return Observable.zip(networkRepository.getAccount(profileId),
                networkRepository.getFoodtrucks(profileId), ProfileViewModel::createFrom)
                .doOnNext(viewModel -> viewModelRepository.addViewModel(uuid, viewModel));
    }

    @Override
    public Observable<Account> getAccount(String profileId) {
        return networkRepository.getAccount(profileId)
                .doOnNext(account -> viewModelRepository.getViewModel(uuid).setAccount(account));
    }

    @Override
    public Observable<List<Foodtruck>> getFoodtrucks(String profileId) {
        return networkRepository.getFoodtrucks(profileId)
                .doOnNext(foodtrucks ->
                        viewModelRepository.getViewModel(uuid).setFoodtrucks(foodtrucks));
    }

    @Override
    public Observable<Message> removeProfilePicture() {
        return networkRepository.deleteAccountImage();
    }

    @Override
    public Observable<Message> uploadProfilePicture(MultipartBody.Part image) {
        return networkRepository.uploadAccountImage(image);
    }
}
