package com.busytrack.foodtruckclient.generic.mvp;

import androidx.annotation.NonNull;
import androidx.annotation.StringRes;
import androidx.fragment.app.Fragment;

import com.busytrack.foodtruckclient.generic.viewmodel.BaseViewModel;
import com.busytrack.foodtruckclient.permission.PermissionRequestDelegate;

import java.util.UUID;

public interface BaseMVP {

    interface Model<T extends BaseViewModel> {
        void setUuid(UUID uuid);
        UUID getUuid();
        T getCachedViewModel();
    }

    interface View {
        void setRefreshingIndicator(boolean refreshing);
        void toast(String message);
        void showSnackBar(int stringResId);
        void showSnackBar(int stringResId, Object... formatArgs);
        PermissionRequestDelegate getPermissionRequestDelegate();
        void popFragment();
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
         * Should be called in the {@link Fragment#onStop()} callback, in order to dispose
         * all RxJava disposables
         */
        void clearCompositeDisposable();

        /**
         * Callback method to pass the result of the permission request to the appropriate handler
         */
        void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                        @NonNull int[] grantResults);

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

        /**
         * Execute a payload upon granting the required permission
         */
        void doOnPermissionGranted(String permission,
                                   @StringRes int permissionDeniedMessage,
                                   PermissionRequestDelegate delegate,
                                   Runnable payload);

        /**
         * Override to implement the handling logic of view model invalidation effects
         */
        void handleInvalidationEffects();

        /**
         * Attempt to restore cached view model data to the view
         *
         * @return true if cached data is available, false otherwise
         */
        boolean restoreDataFromCache();
    }
}
