package com.example.foodtruckclient.generic.view;

import androidx.annotation.DrawableRes;

public interface MorphableView {

    /**
     * Set the {@link android.graphics.drawable.AnimatedVectorDrawable} resource id on the view
     *
     * @param avdResId resource id
     */
    void setAvd(@DrawableRes int avdResId);

    /**
     * Run the morph animation
     */
    void morph();
}
