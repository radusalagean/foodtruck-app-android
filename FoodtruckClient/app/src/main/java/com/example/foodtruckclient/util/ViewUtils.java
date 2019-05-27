package com.example.foodtruckclient.util;

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
}
