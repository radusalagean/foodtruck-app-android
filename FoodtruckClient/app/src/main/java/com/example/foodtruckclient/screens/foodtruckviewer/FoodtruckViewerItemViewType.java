package com.example.foodtruckclient.screens.foodtruckviewer;

import androidx.annotation.IntDef;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@IntDef({
        FoodtruckViewerItemViewType.HEADER,
        FoodtruckViewerItemViewType.MY_REVIEW,
        FoodtruckViewerItemViewType.REVIEW
})
@Retention(RetentionPolicy.SOURCE)
public @interface FoodtruckViewerItemViewType {
    int HEADER = 0;
    int MY_REVIEW = 1;
    int REVIEW = 2;
}
