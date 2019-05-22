package com.example.foodtruckclient.screens.profile;

import androidx.annotation.IntDef;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@IntDef({
        ProfileItemViewType.HEADER,
        ProfileItemViewType.FOODTRUCK
})
@Retention(RetentionPolicy.SOURCE)
public @interface ProfileItemViewType {
    int HEADER = 0;
    int FOODTRUCK = 1;
}
