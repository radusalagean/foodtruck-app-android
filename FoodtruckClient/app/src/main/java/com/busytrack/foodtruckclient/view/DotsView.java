package com.busytrack.foodtruckclient.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.ColorInt;
import androidx.annotation.Nullable;
import androidx.core.content.res.ResourcesCompat;

import com.busytrack.foodtruckclient.R;

public class DotsView extends View {

    private static final int DEFAULT_COUNT = 1;
    private static final int DEFAULT_SELECTED_DOT_COLOR_RES = R.color.colorAccent;
    private static final int DEFAULT_UNSELECTED_DOT_COLOR_RES = R.color.colorWhite;
    private static final int DEFAULT_DOT_RADIUS_RES = R.dimen.dots_view_default_dot_radius;
    private static final int DEFAULT_DOT_SEPARATION_RES = R.dimen.dots_view_default_dot_separation;

    private int count;
    private @ColorInt int selectedDotColor;
    private @ColorInt int unselectedDotColor;
    private int dotRadius;
    private int dotSeparation;
    private Paint selectedDotPaint;
    private Paint unselectedDotPaint;
    private int currentDot;

    public DotsView(Context context) {
        super(context);
        init(null);
    }

    public DotsView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(attrs);
    }

    public DotsView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(attrs);
    }

    void init(@Nullable AttributeSet attrs) {
        count = DEFAULT_COUNT;
        selectedDotColor = ResourcesCompat
                .getColor(getResources(), DEFAULT_SELECTED_DOT_COLOR_RES, null);
        unselectedDotColor = ResourcesCompat
                .getColor(getResources(), DEFAULT_UNSELECTED_DOT_COLOR_RES, null);
        dotRadius = getResources().getDimensionPixelSize(DEFAULT_DOT_RADIUS_RES);
        dotSeparation = getResources().getDimensionPixelSize(DEFAULT_DOT_SEPARATION_RES);
        if (attrs != null) {
            TypedArray a = getContext().obtainStyledAttributes(
                    attrs, R.styleable.DotsView, 0, 0
            );
            count = a.getInt(R.styleable.DotsView_count, DEFAULT_COUNT);
            selectedDotColor = a.getColor(R.styleable.DotsView_selectedDotColor,
                    ResourcesCompat.getColor(getResources(), DEFAULT_SELECTED_DOT_COLOR_RES, null));
            unselectedDotColor = a.getColor(R.styleable.DotsView_unselectedDotColor,
                    ResourcesCompat.getColor(getResources(), DEFAULT_UNSELECTED_DOT_COLOR_RES, null));
            dotRadius = a.getDimensionPixelSize(R.styleable.DotsView_dotRadius,
                    getResources().getDimensionPixelSize(DEFAULT_DOT_RADIUS_RES));
            dotSeparation = a.getDimensionPixelSize(R.styleable.DotsView_dotSeparation,
                    getResources().getDimensionPixelSize(DEFAULT_DOT_SEPARATION_RES));
            a.recycle();
        }
        selectedDotPaint = getPaint(selectedDotColor);
        unselectedDotPaint = getPaint(unselectedDotColor);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        for (int i = 0; i < count; i++) {
            float[] coordinates = getCircleCoordinates(i);
            canvas.drawCircle(
                    coordinates[0],
                    coordinates[1],
                    dotRadius,
                    currentDot == i ? selectedDotPaint : unselectedDotPaint);
        }
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int width = getPaddingLeft() + getPaddingRight();
        for (int i = 0; i < count; i++) {
            width += 2 * dotRadius;
            if (i < count - 1) {
                width += dotSeparation;
            }
        }
        int height = getPaddingTop() + 2 * dotRadius + getPaddingBottom();
        setMeasuredDimension(
                resolveSize(width, widthMeasureSpec),
                resolveSize(height, heightMeasureSpec)
        );
    }

    public void selectDot(int dot) {
        if (dot < 0 || dot >= count) {
            throw new IllegalArgumentException("WTF m8?!");
        }
        currentDot = dot;
        invalidate();
    }

    private Paint getPaint(@ColorInt int color) {
        Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setColor(color);
        return paint;
    }

    private float[] getCircleCoordinates(int index) {
        float x = (float) dotRadius + index * (dotSeparation + 2 * dotRadius);
        float y = (float) dotRadius;
        return new float[] { x, y };
    }
}
