package com.busytrack.foodtruckclient.generic.contentinvalidation;

import androidx.annotation.IntDef;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * Defines the invalidation types that are being sent with an {@link InvalidationBundle} in order
 * to be processed for each screen.
 */
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
