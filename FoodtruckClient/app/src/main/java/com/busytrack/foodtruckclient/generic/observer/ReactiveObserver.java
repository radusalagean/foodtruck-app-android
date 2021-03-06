package com.busytrack.foodtruckclient.generic.observer;

import androidx.annotation.CallSuper;

import io.reactivex.observers.DisposableObserver;

/**
 * A general observer used to handle specific events through a listener
 */
public abstract class ReactiveObserver<T> extends DisposableObserver<T> {

    private ReactiveListener listener;

    public ReactiveObserver(ReactiveListener listener) {
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
