package com.busytrack.foodtruckclient.screen.profile;

import com.busytrack.foodtruckclient.generic.contentinvalidation.ContentType;
import com.busytrack.foodtruckclient.generic.contentinvalidation.InvalidationBundle;
import com.busytrack.foodtruckclient.generic.contentinvalidation.InvalidationEffect;
import com.busytrack.foodtruckclient.generic.contentinvalidation.InvalidationType;
import com.busytrack.foodtruckclient.generic.viewmodel.BaseViewModel;
import com.busytrack.foodtruckclient.network.foodtruckapi.model.Account;
import com.busytrack.foodtruckclient.network.foodtruckapi.model.Foodtruck;
import com.google.common.base.Strings;

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

    @Override
    public void processInvalidationBundle(InvalidationBundle invalidationBundle) {
        // Check if the fragment needs to be popped or the profile needs to be reloaded
        if (invalidationBundle.getContentId().equals(account.getId()) &&
                invalidationBundle.getContentType() == ContentType.PROFILE) {
            if (invalidationBundle.getInvalidationType() == InvalidationType.REMOVE) {
                invalidationEffects |= InvalidationEffect.POP_FRAGMENT;
            } else {
                invalidationEffects |= InvalidationEffect.PROFILE_RELOAD;
            }
        }
        // Check if the foodtrucks need to be reloaded
        if ((invalidationBundle.getContentType() &
                (ContentType.FOODTRUCK | ContentType.REVIEW)) != 0) {
            if (Strings.isNullOrEmpty(invalidationBundle.getContentId())) {
                invalidationEffects |= InvalidationEffect.FOODTRUCK_RELOAD;
            } else {
                for (Foodtruck foodtruck : foodtrucks) {
                    if (invalidationBundle.getContentId().equals(foodtruck.getId())) {
                        invalidationEffects |= InvalidationEffect.FOODTRUCK_RELOAD;
                        break;
                    }
                }
            }
        }
    }
}
