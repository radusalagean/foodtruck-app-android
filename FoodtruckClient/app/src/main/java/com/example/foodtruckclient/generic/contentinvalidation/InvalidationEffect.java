package com.example.foodtruckclient.generic.contentinvalidation;

import androidx.annotation.IntDef;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@IntDef({
        InvalidationEffect.NONE,
        InvalidationEffect.POP_FRAGMENT,
        InvalidationEffect.FOODTRUCK_RELOAD,
        InvalidationEffect.REVIEW_RELOAD,
        InvalidationEffect.PROFILE_RELOAD
})
@Retention(RetentionPolicy.SOURCE)
public @interface InvalidationEffect {
    int NONE = 0;               // 0000
    int POP_FRAGMENT = 1;       // 0001
    int FOODTRUCK_RELOAD = 2;   // 0010
    int REVIEW_RELOAD = 4;      // 0100
    int PROFILE_RELOAD = 8;     // 1000
}
