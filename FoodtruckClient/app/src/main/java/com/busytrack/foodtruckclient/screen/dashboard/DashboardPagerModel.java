package com.busytrack.foodtruckclient.screen.dashboard;

import androidx.annotation.LayoutRes;
import androidx.annotation.StringRes;

public class DashboardPagerModel {

    private @LayoutRes int layoutResId;
    private @StringRes int titleStringResId;

    public DashboardPagerModel(int layoutResId, int titleStringResId) {
        this.layoutResId = layoutResId;
        this.titleStringResId = titleStringResId;
    }

    public int getLayoutResId() {
        return layoutResId;
    }

    public int getTitleStringResId() {
        return titleStringResId;
    }
}
