package com.busytrack.foodtruckclient.generic.viewmodel;

import com.busytrack.foodtruckclient.generic.contentinvalidation.InvalidationBundle;
import com.busytrack.foodtruckclient.generic.contentinvalidation.InvalidationEffect;

public abstract class BaseViewModel {

    protected int invalidationEffects;

    public int getInvalidationEffects() {
        return invalidationEffects;
    }

    public void clearInvalidationEffects() {
        this.invalidationEffects = InvalidationEffect.NONE;
    }

    /**
     * Override to implement the handling logic of every {@link InvalidationBundle} received
     */
    protected abstract void processInvalidationBundle(InvalidationBundle invalidationBundle);
}
