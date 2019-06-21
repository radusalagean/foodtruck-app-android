package com.busytrack.foodtruckclient.generic.decoration;

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
        outRect.top = offset;
        outRect.right = offset;
        if (parent.getAdapter() != null &&
                parent.getChildAdapterPosition(view) == parent.getAdapter().getItemCount() - 1)  {
            // if the item is the last one, add 8 times the offset to the bottom
            outRect.bottom = 8 * offset;
        }
    }
}
