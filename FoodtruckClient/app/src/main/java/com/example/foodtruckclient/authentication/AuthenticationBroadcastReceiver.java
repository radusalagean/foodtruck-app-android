package com.example.foodtruckclient.authentication;

import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;

import com.example.foodtruckclient.R;
import com.example.foodtruckclient.generic.activity.ActivityContract;
import com.example.foodtruckclient.generic.broadcastreceiver.BaseBroadcastReceiver;

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
