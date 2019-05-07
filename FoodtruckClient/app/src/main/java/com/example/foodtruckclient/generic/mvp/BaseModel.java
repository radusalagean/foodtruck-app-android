package com.example.foodtruckclient.generic.mvp;

import io.reactivex.Observable;

public interface BaseModel<T> {

    /**
     * Gets the view model of type {@link T}
     */
    Observable<T> getViewModel();

    T getCachedViewModel();
}
