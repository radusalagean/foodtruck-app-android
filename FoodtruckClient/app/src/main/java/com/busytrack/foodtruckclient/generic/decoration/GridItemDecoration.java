package com.busytrack.foodtruckclient.generic.decoration;

import android.graphics.Rect;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class GridItemDecoration extends RecyclerView.ItemDecoration {

    private int offset;
    private int span;

    public GridItemDecoration(int offset, int span) {
        this.offset = offset;
        this.span = span;
    }

    @Override
    public void getItemOffsets(@NonNull Rect outRect, @NonNull View view,
                               @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
        if (parent.getAdapter() != null) {
            int count = parent.getAdapter().getItemCount();
            int columns = span;
            int rows = count / columns + (count % columns > 0 ? 1 : 0);
            int currentPosition = parent.getChildAdapterPosition(view);
            int currentRow = currentPosition / columns;
            int currentColumn = currentPosition % columns;
            outRect.left = offset;
            outRect.top = offset;
            if (currentColumn == columns - 1) {
                outRect.right = offset;
            }
            if (currentRow == rows - 1) {
                outRect.bottom = 8 * offset;
            }
        }
    }
}
