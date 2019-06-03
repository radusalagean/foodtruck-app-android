package com.busytrack.foodtruckclient.permission;

import androidx.collection.ArrayMap;

import java.util.Collections;
import java.util.Map;

public class PermissionConstants {

    public static final String PERMISSION_ACCESS_FINE_LOCATION =
            android.Manifest.permission.ACCESS_FINE_LOCATION;
    public static final String PERMISSION_READ_EXTERNAL_STORAGE =
            android.Manifest.permission.READ_EXTERNAL_STORAGE;
    public static final String PERMISSION_CAMERA =
            android.Manifest.permission.CAMERA;

    private static final int REQ_CODE_INVALID = -1;
    private static final int REQ_CODE_PERMISSION_ACCESS_FINE_LOCATION = 0;
    private static final int REQ_CODE_PERMISSION_READ_EXTERNAL_STORAGE = 1;
    private static final int REQ_CODE_PERMISSION_CAMERA = 2;

    /**
     * Key: Permission name
     * Value: Permission request code
     */
    private static final Map<String, Integer> PERMISSION_CODE_MAP;

    static {
        ArrayMap<String, Integer> map = new ArrayMap<>();
        map.put(PERMISSION_ACCESS_FINE_LOCATION, REQ_CODE_PERMISSION_ACCESS_FINE_LOCATION);
        map.put(PERMISSION_READ_EXTERNAL_STORAGE, REQ_CODE_PERMISSION_READ_EXTERNAL_STORAGE);
        map.put(PERMISSION_CAMERA, REQ_CODE_PERMISSION_CAMERA);
        PERMISSION_CODE_MAP = Collections.unmodifiableMap(map);
    }
    
    public static int getRequestCode(String permission) {
        final Integer integer = PERMISSION_CODE_MAP.get(permission);
        return integer != null ? integer : REQ_CODE_INVALID;
    }
}
