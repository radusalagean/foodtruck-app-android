package com.example.foodtruckclient.view;

import android.content.Context;
import android.util.AttributeSet;

import com.example.foodtruckclient.R;
import com.google.android.flexbox.FlexWrap;
import com.google.android.flexbox.FlexboxLayout;

import java.util.List;

public class TagLayout extends FlexboxLayout {

    public TagLayout(Context context) {
        super(context);
        init();
    }

    public TagLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public TagLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        setDividerDrawable(getResources().getDrawable(R.drawable.shape_tag_layout_divider));
        setShowDivider(SHOW_DIVIDER_MIDDLE);
        setFlexWrap(FlexWrap.WRAP);
    }

    public void setTags(List<String> tags) {
        removeAllViews();
        for (String tag : tags) {
            addView(new TagView(getContext(), tag));
        }
    }

    public void clearTags() {
        removeAllViews();
    }
}
