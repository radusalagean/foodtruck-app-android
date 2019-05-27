package com.example.foodtruckclient.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.Gravity;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.core.content.res.ResourcesCompat;

import com.example.foodtruckclient.R;

public class TagView extends AppCompatTextView {

    private boolean removable;

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

    public TagView(Context context, String tag, boolean removable) {
        super(context);
        this.removable = removable;
        init();
        setText(tag);
    }

    private void init() {
        setBackground(ResourcesCompat
                .getDrawable(getResources(), R.drawable.shape_tag_view_background, null));
        setTextColor(ResourcesCompat.getColor(getResources(), R.color.colorWhite, null));
        int padding = getResources().getDimensionPixelSize(R.dimen.tag_view_padding);
        setPadding(padding, padding, padding, padding);
        setGravity(Gravity.CENTER);
        if (removable) {
            setCompoundDrawablesWithIntrinsicBounds(
                    0, 0, R.drawable.ic_close_black_16dp, 0
            );
            setCompoundDrawablePadding(
                    getResources().getDimensionPixelSize(R.dimen.general_layout_margin)
            );
        }
    }
}
