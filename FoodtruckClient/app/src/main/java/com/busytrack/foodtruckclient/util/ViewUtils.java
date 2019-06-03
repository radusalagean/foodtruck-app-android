package com.busytrack.foodtruckclient.util;

import android.content.Context;
import android.util.DisplayMetrics;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class ViewUtils {

    /**
     * Set text to an {@link EditText} and move the cursor at the end
     *
     * @param editText target
     * @param text text to be set on the target
     */
    public static void setText(@NonNull EditText editText, @Nullable String text) {
        editText.setText(text);
        editText.setSelection(editText.length());
    }

    /**
     * Compute the number of columns that a grid layout can take up, based on the current
     * display width and the width of an item
     *
     * @param context Context
     * @param totalItemWidth the width an item can take up, including extra padding
     * @return the number of columns that can be displayed in the grid layout
     */
    public static int computeGridLayoutSpan(Context context, int totalItemWidth) {
        DisplayMetrics displayMetrics = context.getResources().getDisplayMetrics();
        return displayMetrics.widthPixels / totalItemWidth;
    }
}
