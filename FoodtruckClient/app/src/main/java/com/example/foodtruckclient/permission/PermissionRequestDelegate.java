package com.example.foodtruckclient.permission;

public interface PermissionRequestDelegate {

    void requestPermission(String permission, int requestCode);
}
