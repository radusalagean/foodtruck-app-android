package com.example.foodtruckclient.generic.viewpager;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;

import com.example.foodtruckclient.generic.view.OnViewInflatedListener;

import timber.log.Timber;

public abstract class BasePagerAdapter extends PagerAdapter {

    protected Context context;
    protected OnViewInflatedListener onViewInflatedListener;

    public BasePagerAdapter(Context context, OnViewInflatedListener onViewInflatedListener) {
        this.context = context;
        this.onViewInflatedListener = onViewInflatedListener;
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        Timber.d("instantiateItem %d", position);
        LayoutInflater inflater = LayoutInflater.from(context);
        ViewGroup layout = (ViewGroup) inflater.inflate(
                getLayoutResId(position),
                container,
                false
        );
        onViewInflatedListener.onViewInflated(layout);
        container.addView(layout);
        return layout;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        Timber.d("destroyItem %d", position);
        View view = (View) object;
        onViewInflatedListener.onViewDestroyed(view);
        container.removeViewInLayout(view);
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view == object;
    }

    protected abstract int getLayoutResId(int pos);
}
