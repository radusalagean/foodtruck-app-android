package com.example.foodtruckclient.generic.mvp;

import androidx.annotation.NonNull;

import com.example.foodtruckclient.generic.viewmodel.BaseViewModel;
import com.example.foodtruckclient.permission.PermissionManager;
import com.example.foodtruckclient.permission.PermissionRequestDelegate;

import java.util.UUID;

public interface BaseMVP {

    interface Model<T extends BaseViewModel> {
        void setUuid(UUID uuid);
        T getCachedViewModel();
    }

    interface View {
        void setRefreshingIndicator(boolean refreshing);
        void toast(String message);
        void showSnackBar(int stringResId);
        void showSnackBar(int stringResId, Object... formatArgs);
        PermissionRequestDelegate getPermissionRequestDelegate();
        void onBackPressed();
    }

    interface Presenter<T extends View> {
        /**
         * Run a specific runnable, if the view is not null
         */
        void postOnView(Runnable runnable);

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

        /**
         * Set the refreshing flag and indicator
         */
        void setRefreshing(boolean refreshing);

        /**
         * Returns true if the data is refreshing, false otherwise
         */
        boolean isRefreshing();

        /**
         * Set the UUID of the fragment. (necessary for uniquely identifying the view model
         * in the view model repository)
         */
        void setUuid(UUID uuid);
    }
}
