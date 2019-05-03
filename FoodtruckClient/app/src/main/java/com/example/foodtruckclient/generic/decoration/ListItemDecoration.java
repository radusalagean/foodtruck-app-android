package com.example.foodtruckclient.generic.decoration;

import android.graphics.Rect;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class ListItemDecoration extends RecyclerView.ItemDecoration {

    private int offset;

    public ListItemDecoration(int offset) {
        this.offset = offset;
    }

    @Override
    public void getItemOffsets(@NonNull Rect outRect, @NonNull View view, @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
        outRect.left = offset;
        if (parent.getChildAdapterPosition(view) == 0) {
            // Apply the top offset only for the first item
            outRect.top = offset;
        }
        outRect.right = offset;
        outRect.bottom = offset;
    }
}