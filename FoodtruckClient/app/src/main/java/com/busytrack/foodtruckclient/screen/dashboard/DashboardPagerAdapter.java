package com.busytrack.foodtruckclient.screen.dashboard;

import android.content.Context;

import androidx.annotation.Nullable;

import com.busytrack.foodtruckclient.generic.view.OnViewInflatedListener;
import com.busytrack.foodtruckclient.generic.viewpager.BasePagerAdapter;

public class DashboardPagerAdapter extends BasePagerAdapter {

    public DashboardPagerAdapter(Context context, OnViewInflatedListener onViewInflatedListener) {
        super(context, onViewInflatedListener);
    }

    @Override
    public int getCount() {
        return DashboardPagerMapper.getCount();
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return context.getString(DashboardPagerMapper.getTitleStringResId(position));
    }

    @Override
    protected int getLayoutResId(int pos) {
        return DashboardPagerMapper.getLayoutResId(pos);
    }
}
