package com.example.foodtruckclient.generic.mvp;

import androidx.annotation.NonNull;

public interface BasePresenter<T extends BaseView> {

    /**
     * Binds presenter with a view when resumed. The Presenter will perform initialization here.
     *
     * @param view the view associated with this presenter
     */
    void takeView(T view);

    /**
     * Drops the reference to the view when destroyed
     */
    void dropView();

    /**
     * Callback method to pass the result of the permission request to the appropriate handler
     */
    void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                    @NonNull int[] grantResults);
}
