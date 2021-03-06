package com.busytrack.foodtruckclient.generic.observer;

import androidx.annotation.CallSuper;

import io.reactivex.observers.DisposableCompletableObserver;

/**
 * A general completable observer used to handle specific events through a listener
 */
public class ReactiveCompletableObserver extends DisposableCompletableObserver {

    private ReactiveListener listener;

    public ReactiveCompletableObserver(ReactiveListener listener) {
        this.listener = listener;
    }

    @Override
    @CallSuper
    public void onError(Throwable e) {
        listener.onError(e);
    }

    @Override
    @CallSuper
    public void onComplete() {
        listener.onComplete();
    }
}
