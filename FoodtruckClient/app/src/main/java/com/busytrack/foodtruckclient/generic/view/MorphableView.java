package com.busytrack.foodtruckclient.generic.view;

import androidx.annotation.DrawableRes;

public interface MorphableView {

    /**
     * Run the morph animation
     *
     * @param transitionDrawableResId the AnimatedVectorDrawable that is used to create the morphing animation
     * @param targetDrawableResId the static Drawable that will be set once the animation has ended
     */
    void morph(@DrawableRes int transitionDrawableResId, @DrawableRes int targetDrawableResId);
}
