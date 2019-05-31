package com.example.foodtruckclient.screen.dashboard;

import androidx.annotation.DrawableRes;
import androidx.collection.ArrayMap;

import com.example.foodtruckclient.R;

import java.util.Collections;
import java.util.Map;

public class DashboardLayoutIconMapper {

    private static final Map<Integer, Integer> ICON_MAP;

    static {
        ArrayMap<Integer, Integer> map = new ArrayMap<>();
        map.put(DashboardLayoutType.GRID, R.drawable.ic_view_module_white_24dp);
        map.put(DashboardLayoutType.LIST, R.drawable.ic_view_list_white_24dp);
        ICON_MAP = Collections.unmodifiableMap(map);
    }

    public static @DrawableRes int getIconResId(int layoutType) {
        return ICON_MAP.get(layoutType);
    }
}
