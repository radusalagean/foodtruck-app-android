package com.example.foodtruckclient.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.Gravity;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.core.content.res.ResourcesCompat;

import com.example.foodtruckclient.R;

public class TagView extends AppCompatTextView {

    public TagView(Context context) {
        super(context);
        init();
    }

    public TagView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public TagView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    public TagView(Context context, String tag) {
        this(context);
        setText(tag);
    }

    private void init() {
        setBackground(ResourcesCompat
                .getDrawable(getResources(), R.drawable.shape_tag_view_background, null));
        setTextColor(ResourcesCompat.getColor(getResources(), R.color.colorWhite, null));
        int padding = getResources().getDimensionPixelSize(R.dimen.tag_view_padding);
        setPadding(padding, padding, padding, padding);
        setGravity(Gravity.CENTER);
    }
}
