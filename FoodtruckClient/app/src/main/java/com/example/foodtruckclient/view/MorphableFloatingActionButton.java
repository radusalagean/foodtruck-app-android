package com.example.foodtruckclient.view;

import android.content.Context;
import android.graphics.drawable.Animatable;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;

import androidx.annotation.DrawableRes;

import com.example.foodtruckclient.generic.view.MorphableView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class MorphableFloatingActionButton extends FloatingActionButton implements MorphableView {

    public MorphableFloatingActionButton(Context context) {
        super(context);
    }

    public MorphableFloatingActionButton(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public MorphableFloatingActionButton(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public void setAvd(@DrawableRes int avdResId) {
        setImageResource(avdResId);
    }

    @Override
    public void morph() {
        Drawable currentDrawable = getDrawable();
        if (currentDrawable instanceof Animatable) {
            Animatable animatable = (Animatable) currentDrawable;
            if (animatable.isRunning()) {
                animatable.stop();
            }
            animatable.start();
        }
    }
}
