package com.example.foodtruckclient.screen.profile;

import com.example.foodtruckclient.generic.viewmodel.BaseViewModel;
import com.example.foodtruckclient.network.foodtruckapi.model.Account;
import com.example.foodtruckclient.network.foodtruckapi.model.Foodtruck;

import java.util.List;

public class ProfileViewModel extends BaseViewModel {

    private Account account;
    private List<Foodtruck> foodtrucks;

    private ProfileViewModel(Account account, List<Foodtruck> foodtrucks) {
        this.account = account;
        this.foodtrucks = foodtrucks;
    }

    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }

    public List<Foodtruck> getFoodtrucks() {
        return foodtrucks;
    }

    public void setFoodtrucks(List<Foodtruck> foodtrucks) {
        this.foodtrucks = foodtrucks;
    }

    public static ProfileViewModel createFrom(Account account,
                                               List<Foodtruck> foodtrucks) {
        return new ProfileViewModel(account, foodtrucks);
    }
}
