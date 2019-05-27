package com.example.foodtruckclient.screen.dashboard;

import androidx.annotation.LayoutRes;
import androidx.annotation.StringRes;

import com.example.foodtruckclient.R;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class DashboardPagerMapper {

    public static final int POSITION_LIST = 0;
    public static final int POSITION_MAP = 1;

    private static final List<DashboardPagerModel> PAGER_MODELS;

    static {
        List<DashboardPagerModel> list = new ArrayList<>();
        list.add(new DashboardPagerModel(R.layout.layout_dashboard_list, R.string.dashboard_pager_list));
        list.add(new DashboardPagerModel(R.layout.layout_dashboard_map, R.string.dashboard_pager_map));
        PAGER_MODELS = Collections.unmodifiableList(list);
    }

    public static @LayoutRes int getLayoutResId(int pos) {
        return PAGER_MODELS.get(pos).getLayoutResId();
    }

    public static @StringRes int getTitleStringResId(int pos) {
        return PAGER_MODELS.get(pos).getTitleStringResId();
    }

    public static int getCount() {
        return PAGER_MODELS.size();
    }
}
