package com.busytrack.foodtruckclient.screen.foodtruckeditor;

import android.content.Context;

import com.busytrack.foodtruckclient.generic.view.OnViewInflatedListener;
import com.busytrack.foodtruckclient.generic.viewpager.BasePagerAdapter;

public class FoodtruckEditorPagerAdapter extends BasePagerAdapter {

    public FoodtruckEditorPagerAdapter(Context context, OnViewInflatedListener onViewInflatedListener) {
        super(context, onViewInflatedListener);
    }

    @Override
    public int getCount() {
        return FoodtruckEditorPagerMapper.getCount();
    }

    @Override
    protected int getLayoutResId(int pos) {
        return FoodtruckEditorPagerMapper.getLayoutResId(pos);
    }
}
