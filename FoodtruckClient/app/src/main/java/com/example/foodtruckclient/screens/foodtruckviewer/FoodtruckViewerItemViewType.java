package com.example.foodtruckclient.screens.foodtruckviewer;

import androidx.annotation.IntDef;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@IntDef({FoodtruckViewerItemViewType.HEADER, FoodtruckViewerItemViewType.REVIEW})
@Retention(RetentionPolicy.SOURCE)
public @interface FoodtruckViewerItemViewType {
    int HEADER = 0;
    int REVIEW = 1;
}
