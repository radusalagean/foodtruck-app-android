package com.example.foodtruckclient.view;

import android.content.Context;
import android.graphics.drawable.Animatable;
import android.graphics.drawable.Drawable;
import android.os.Parcel;
import android.os.Parcelable;
import android.util.AttributeSet;

import androidx.annotation.DrawableRes;
import androidx.appcompat.content.res.AppCompatResources;
import androidx.vectordrawable.graphics.drawable.Animatable2Compat;
import androidx.vectordrawable.graphics.drawable.AnimatedVectorDrawableCompat;

import com.example.foodtruckclient.generic.view.MorphableView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import timber.log.Timber;

import static com.google.common.base.Preconditions.checkNotNull;

public class MorphableFloatingActionButton extends FloatingActionButton implements MorphableView {

    private @DrawableRes int imageDrawableIdRes;

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
    public void morph(int transitionDrawableResId, int targetDrawableResId) {
        // Stop any ongoing animation
        Drawable currentDrawable = getDrawable();
        if (currentDrawable instanceof Animatable) {
            Animatable animatable = (Animatable) currentDrawable;
            if (animatable.isRunning()) {
                animatable.stop();
            }
        }
        // Load the passed drawables and run the morphing process
        Drawable transitionDrawable = AppCompatResources.getDrawable(getContext(), transitionDrawableResId);
        Drawable targetDrawable = AppCompatResources.getDrawable(getContext(), targetDrawableResId);
        checkNotNull(transitionDrawable);
        checkNotNull(targetDrawable);
        setImageDrawable(transitionDrawable);
        Animatable animatable = (Animatable) transitionDrawable;
        AnimatedVectorDrawableCompat.registerAnimationCallback(transitionDrawable, new Animatable2Compat.AnimationCallback() {
            @Override
            public void onAnimationEnd(Drawable drawable) {
                setImageResource(targetDrawableResId);
                AnimatedVectorDrawableCompat.unregisterAnimationCallback(drawable, this);
            }
        });
        animatable.start();
    }

    @Override
    public void setImageResource(int resId) {
        this.imageDrawableIdRes = resId;
        super.setImageResource(resId);
    }

    @Override
    protected Parcelable onSaveInstanceState() {
        Parcelable superState = super.onSaveInstanceState();
        SavedState savedState = new SavedState(superState);
        savedState.imageDrawableIdRes = imageDrawableIdRes;
        return savedState;
    }

    @Override
    protected void onRestoreInstanceState(Parcelable state) {
        SavedState savedState = (SavedState) state;
        super.onRestoreInstanceState(savedState.getSuperState());
        if (savedState.imageDrawableIdRes != 0) {
            setImageResource(savedState.imageDrawableIdRes);
        }
    }

    public static class SavedState extends BaseSavedState {

        @DrawableRes int imageDrawableIdRes;

        public SavedState(Parcel source) {
            super(source);
            imageDrawableIdRes = source.readInt();
        }

        public SavedState(Parcelable superState) {
            super(superState);
        }

        @Override
        public void writeToParcel(Parcel out, int flags) {
            super.writeToParcel(out, flags);
            out.writeInt(imageDrawableIdRes);
        }

        public static final Parcelable.Creator<SavedState> CREATOR
                = new Parcelable.Creator<SavedState>() {
            public SavedState createFromParcel(Parcel in) {
                return new SavedState(in);
            }

            public SavedState[] newArray(int size) {
                return new SavedState[size];
            }
        };

    }
}
