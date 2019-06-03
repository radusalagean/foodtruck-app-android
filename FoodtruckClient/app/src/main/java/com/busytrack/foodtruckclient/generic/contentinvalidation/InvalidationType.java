package com.busytrack.foodtruckclient.generic.contentinvalidation;

import androidx.annotation.IntDef;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@IntDef({
        InvalidationType.NONE,
        InvalidationType.RELOAD,
        InvalidationType.REMOVE
})
@Retention(RetentionPolicy.SOURCE)
public @interface InvalidationType {
    int NONE = 0;
    int RELOAD = 1;
    int REMOVE = 2;
}
