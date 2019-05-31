package com.example.foodtruckclient.screen.dashboard;

import androidx.annotation.IntDef;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@IntDef({
        DashboardLayoutType.GRID,
        DashboardLayoutType.LIST
})
@Retention(RetentionPolicy.SOURCE)
public @interface DashboardLayoutType {
    int GRID = 0;
    int LIST = 1;
}
