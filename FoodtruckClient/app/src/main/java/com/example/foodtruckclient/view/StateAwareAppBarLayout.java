package com.example.foodtruckclient.view;

import android.content.Context;
import android.util.AttributeSet;

import com.google.android.material.appbar.AppBarLayout;

public class StateAwareAppBarLayout extends AppBarLayout {

    public static final int STATE_IDLE = 0;
    public static final int STATE_EXPANDED = 1;
    public static final int STATE_COLLAPSED = 2;

    private int currentState = STATE_IDLE;
    private OnStateChangedListener onStateChangedListener;

    private OnOffsetChangedListener onOffsetChangedListener = new OnOffsetChangedListener() {
        @Override
        public void onOffsetChanged(AppBarLayout appBarLayout, int i) {
            if (i == 0) {
                if (currentState != STATE_EXPANDED) {
                    onStateChanged(STATE_EXPANDED);
                }
                currentState = STATE_EXPANDED;
            } else if (Math.abs(i) >= appBarLayout.getTotalScrollRange()) {
                if (currentState != STATE_COLLAPSED) {
                    onStateChanged(STATE_COLLAPSED);
                }
                currentState = STATE_COLLAPSED;
            } else {
                if (currentState != STATE_IDLE) {
                    onStateChanged(STATE_IDLE);
                }
                currentState = STATE_IDLE;
            }
        }
    };

    public StateAwareAppBarLayout(Context context) {
        super(context);
        init();
    }

    public StateAwareAppBarLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init() {
        addOnOffsetChangedListener(onOffsetChangedListener);
    }

    public void setOnStateChangedListener(OnStateChangedListener onStateChangedListener) {
        this.onStateChangedListener = onStateChangedListener;
    }

    private void onStateChanged(int state) {
        if (onStateChangedListener != null) {
            onStateChangedListener.onStateChanged(state);
        }
    }

    public interface OnStateChangedListener {
        void onStateChanged(int state);
    }
}
