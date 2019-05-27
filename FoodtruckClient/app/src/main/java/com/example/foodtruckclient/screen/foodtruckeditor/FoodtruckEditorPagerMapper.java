package com.example.foodtruckclient.screen.foodtruckeditor;

import androidx.annotation.LayoutRes;
import androidx.collection.ArrayMap;

import com.example.foodtruckclient.R;

import java.util.Collections;
import java.util.Map;

public class FoodtruckEditorPagerMapper {

    public static final int POSITION_NAME = 0;
    public static final int POSITION_FOODTYPES = 1;
    public static final int POSITION_LOCATION = 2;
    public static final int POSITION_IMAGE = 3;

    private static final Map<Integer, Integer> LAYOUT_MAP;

    static {
        ArrayMap<Integer, Integer> map = new ArrayMap<>();
        map.put(POSITION_NAME, R.layout.layout_foodtruck_editor_name);
        map.put(POSITION_FOODTYPES, R.layout.layout_foodtruck_editor_foodtypes);
        map.put(POSITION_LOCATION, R.layout.layout_foodtruck_editor_location);
        map.put(POSITION_IMAGE, R.layout.layout_foodtruck_editor_image);
        LAYOUT_MAP = Collections.unmodifiableMap(map);
    }

    public static @LayoutRes int getLayoutResId(int pos) {
        return LAYOUT_MAP.get(pos);
    }

    public static int getCount() {
        return LAYOUT_MAP.size();
    }
}
