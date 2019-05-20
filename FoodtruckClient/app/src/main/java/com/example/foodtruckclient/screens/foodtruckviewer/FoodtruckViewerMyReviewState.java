package com.example.foodtruckclient.screens.foodtruckviewer;

import androidx.annotation.IntDef;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@IntDef({
        FoodtruckViewerMyReviewState.UNKNOWN,
        FoodtruckViewerMyReviewState.NOT_AUTHENTICATED,
        FoodtruckViewerMyReviewState.NO_REVIEW_SUBMITTED,
        FoodtruckViewerMyReviewState.REVIEW_SUBMITTED,
        FoodtruckViewerMyReviewState.EDIT_SUBMITTED_REVIEW,
        FoodtruckViewerMyReviewState.HIDDEN
})
@Retention(RetentionPolicy.SOURCE)
public @interface FoodtruckViewerMyReviewState {
    int UNKNOWN = -1;
    int NOT_AUTHENTICATED = 0;
    int NO_REVIEW_SUBMITTED = 1;
    int REVIEW_SUBMITTED = 2;
    int EDIT_SUBMITTED_REVIEW = 3;
    int HIDDEN = 4;
}
