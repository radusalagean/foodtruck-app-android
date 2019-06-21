package com.busytrack.foodtruckclient.generic.viewmodel;

import com.busytrack.foodtruckclient.generic.contentinvalidation.InvalidationBundle;
import com.busytrack.foodtruckclient.generic.contentinvalidation.InvalidationEffect;

public abstract class BaseViewModel {

    /**
     * The invalidation effects accumulated while the fragment is in background.
     * Once the fragment is in the foreground again, the effects will be processed and then
     * will be cleared from this variable.
     */
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
