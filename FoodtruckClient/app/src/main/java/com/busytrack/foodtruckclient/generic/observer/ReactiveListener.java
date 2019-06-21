package com.busytrack.foodtruckclient.generic.observer;

public interface ReactiveListener {

    void onError(Throwable e);
    void onComplete();
}
