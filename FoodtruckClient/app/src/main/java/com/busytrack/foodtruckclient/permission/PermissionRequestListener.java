package com.busytrack.foodtruckclient.permission;

public interface PermissionRequestListener {

    void onPermissionRequestGranted();
    void onPermissionRequestDenied();
    void onPermissionRequestCanceled();
}
