package com.busytrack.foodtruckclient.permission;

public interface PermissionRequestDelegate {

    void requestPermission(String permission, int requestCode);
}
