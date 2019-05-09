package com.example.foodtruckclient.generic.mvp;

import androidx.annotation.NonNull;

import com.example.foodtruckclient.generic.viewmodel.BaseViewModel;
import com.example.foodtruckclient.permission.PermissionManager;

public interface BaseMVP {

    interface Model<T extends BaseViewModel> {
        T getCachedViewModel();
    }

    interface View {
        void toast(String message);
    }

    interface Presenter<T extends View> {
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

        /**
         * Override this method in the screen-specific presenter implementation to provide an
         * instance of {@link PermissionManager}, if available
         */
        default PermissionManager getPermissionManager() {
            return null;
        }
    }
}
