package com.busytrack.foodtruckclient.generic.contentinvalidation;

import androidx.annotation.IntDef;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * Defines the types of content available
 */
@IntDef({
        ContentType.NONE,
        ContentType.FOODTRUCK,
        ContentType.REVIEW,
        ContentType.PROFILE
})
@Retention(RetentionPolicy.SOURCE)
public @interface ContentType {
    int NONE = 0;
    int FOODTRUCK = 1;
    int REVIEW = 2;
    int PROFILE = 3;
}
