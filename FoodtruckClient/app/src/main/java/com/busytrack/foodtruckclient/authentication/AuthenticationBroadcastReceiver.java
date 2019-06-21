package com.busytrack.foodtruckclient.authentication;

import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;

import com.busytrack.foodtruckclient.R;
import com.busytrack.foodtruckclient.generic.activity.ActivityContract;
import com.busytrack.foodtruckclient.generic.broadcastreceiver.BaseBroadcastReceiver;

/**
 * Receive account sync events executed by {@link AuthenticationService}
 */
public class AuthenticationBroadcastReceiver extends BaseBroadcastReceiver {

    private AuthenticationRepository authenticationRepository;
    private ActivityContract activityContract;

    public AuthenticationBroadcastReceiver(AuthenticationRepository authenticationRepository,
                                           ActivityContract activityContract) {
        this.authenticationRepository = authenticationRepository;
        this.activityContract = activityContract;
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        super.onReceive(context, intent);
        if (AuthenticationConstants.ACTION_USER_INFO_SYNC_SUCCESSFUL.equals(intent.getAction())) {
            activityContract.setAuthenticatedAccount(
                    authenticationRepository.getAuthenticatedAccount(), false
            );
        } else if (AuthenticationConstants.ACTION_USER_INFO_SYNC_FAILED.equals(intent.getAction())) {
            activityContract.showSnackBar(context.getString(R.string.user_info_sync_failed_message));
        }
    }

    @Override
    protected IntentFilter getIntentFilter() {
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(AuthenticationConstants.ACTION_USER_INFO_SYNC_SUCCESSFUL);
        intentFilter.addAction(AuthenticationConstants.ACTION_USER_INFO_SYNC_FAILED);
        return intentFilter;
    }
}
