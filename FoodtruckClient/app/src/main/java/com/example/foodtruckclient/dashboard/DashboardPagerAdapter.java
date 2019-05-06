package com.example.foodtruckclient.dashboard;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.collection.ArrayMap;
import androidx.viewpager.widget.PagerAdapter;

import com.example.foodtruckclient.generic.view.OnViewInflatedListener;

import java.util.Map;

public class DashboardPagerAdapter extends PagerAdapter {

    private Context context;
    private OnViewInflatedListener onViewInflatedListener;
    private Map<Integer, ViewGroup> layoutMap;

    public DashboardPagerAdapter(Context context, OnViewInflatedListener onViewInflatedListener) {
        this.context = context;
        this.onViewInflatedListener = onViewInflatedListener;
        layoutMap = new ArrayMap<>();
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        ViewGroup layout = layoutMap.get(position);
        if (layout == null) {
            LayoutInflater inflater = LayoutInflater.from(context);
            layout = (ViewGroup) inflater.inflate(DashboardPagerMapper.getLayoutResId(position), container, false);
            onViewInflatedListener.onViewInflated(layout);
            layoutMap.put(position, layout);
        }
        container.addView(layout);
        return layout;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeViewInLayout((View) object);
        layoutMap.remove(position);
    }

    @Override
    public int getCount() {
        return DashboardPagerMapper.getCount();
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view == object;
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return context.getString(DashboardPagerMapper.getTitleStringResId(position));
    }
}
