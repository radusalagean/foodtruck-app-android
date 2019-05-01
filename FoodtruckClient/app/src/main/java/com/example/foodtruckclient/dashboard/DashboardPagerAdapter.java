package com.example.foodtruckclient.dashboard;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.viewpager.widget.PagerAdapter;

import com.example.foodtruckclient.generic.view.OnViewInflatedListener;

public class DashboardPagerAdapter extends PagerAdapter {

    private Context context;
    private OnViewInflatedListener onViewInflatedListener;

    public DashboardPagerAdapter(Context context, OnViewInflatedListener onViewInflatedListener) {
        this.context = context;
        this.onViewInflatedListener = onViewInflatedListener;
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        LayoutInflater inflater = LayoutInflater.from(context);
        ViewGroup layout = (ViewGroup) inflater.inflate(DashboardPagerMapper.getLayoutResId(position), container, false);
        container.addView(layout);
        onViewInflatedListener.onViewInflated(layout);
        return layout;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeViewInLayout((View) object);
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
