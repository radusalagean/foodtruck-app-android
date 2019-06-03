package com.busytrack.foodtruckclient.permission;

import android.content.Context;
import android.content.pm.PackageManager;

import androidx.annotation.NonNull;
import androidx.collection.ArrayMap;
import androidx.core.content.ContextCompat;

import java.util.Map;

public class PermissionManager {

    private Context context;

    /**
     * Key: Permission request code
     * Value: Callback
     */
    private Map<Integer, PermissionRequestListener> callbackMap;

    public PermissionManager(Context context) {
        this.context = context;
        callbackMap = new ArrayMap<>();
    }

    public boolean isPermissionGranted(String permission) {
        int res = ContextCompat.checkSelfPermission(context, permission);
        return res == PackageManager.PERMISSION_GRANTED;
    }

    public void requestPermission(String permission, PermissionRequestDelegate delegate,
                                  PermissionRequestListener callback) {
        int reqCode = PermissionConstants.getRequestCode(permission);
        callbackMap.put(reqCode, callback);
        delegate.requestPermission(permission, reqCode);
    }

    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        PermissionRequestListener callback = callbackMap.get(requestCode);
        if (callback != null) {
            if (grantResults.length == 0) {
                callback.onPermissionRequestCanceled();
            } else if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                callback.onPermissionRequestGranted();
            } else {
                callback.onPermissionRequestDenied();
            }
        }
        callbackMap.remove(requestCode);
    }
}
