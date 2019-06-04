package com.busytrack.foodtruckclient.generic.view;

import android.view.View;

public interface OnViewInflatedListener {

    void onViewInflated(View view);
    void onViewDestroyed(View view);
}