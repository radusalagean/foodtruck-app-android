package com.example.foodtruckclient.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;

import com.example.foodtruckclient.R;
import com.google.android.flexbox.FlexWrap;
import com.google.android.flexbox.FlexboxLayout;

import java.util.ArrayList;
import java.util.List;

public class TagLayout extends FlexboxLayout {

    public static final int MAX_TAG_COUNT = 10;

    private boolean editable;
    private Listener listener;

    public TagLayout(Context context) {
        super(context);
        init(null);
    }

    public TagLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(attrs);
    }

    public TagLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(attrs);
    }

    private void init(@Nullable AttributeSet attrs) {
        setDividerDrawable(getResources().getDrawable(R.drawable.shape_tag_layout_divider));
        setShowDivider(SHOW_DIVIDER_MIDDLE);
        setFlexWrap(FlexWrap.WRAP);
        if (attrs != null) {
            TypedArray a = getContext().obtainStyledAttributes(
                    attrs, R.styleable.TagLayout, 0, 0
            );
            editable = a.getBoolean(R.styleable.TagLayout_editable, false);
            a.recycle();
        }
    }

    public Listener getListener() {
        return listener;
    }

    public void setListener(Listener listener) {
        this.listener = listener;
    }

    @Override
    public void addView(View child, int index, ViewGroup.LayoutParams params) {
        super.addView(child, index, params);
        if (editable) {
            child.setOnClickListener(this::removeView);
        }
        if (listener != null) {
            listener.onContentChanged();
        }
    }

    @Override
    public void removeView(View view) {
        view.setOnClickListener(null);
        super.removeView(view);
        if (listener != null) {
            listener.onContentChanged();
        }
    }

    public void setEditable(boolean editable) {
        this.editable = editable;
    }

    public void addTag(String tag) {
        addView(new TagView(getContext(), tag, editable));
    }

    public void setTags(@Nullable List<String> tags) {
        removeAllViews();
        if (tags == null) {
            return;
        }
        for (String tag : tags) {
            addView(new TagView(getContext(), tag, editable));
        }
    }

    public List<String> getTags() {
        List<String> tags = new ArrayList<>();
        for (int i = 0; i < getChildCount(); i++) {
            View view = getChildAt(i);
            if (view instanceof TagView) {
                tags.add(((TagView) view).getText().toString());
            }
        }
        return tags;
    }

    public void clearTags() {
        removeAllViews();
    }

    public int getCount() {
        return getChildCount();
    }

    public interface Listener {
        void onContentChanged();
    }
}
