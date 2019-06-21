package com.busytrack.foodtruckclient.generic.view;

import android.view.View;

/**
 * Pass inflated and destroyed views from a PagerAdapter
 */
public interface OnViewInflatedListener {

    void onViewInflated(View view);
    void onViewDestroyed(View view);
}
