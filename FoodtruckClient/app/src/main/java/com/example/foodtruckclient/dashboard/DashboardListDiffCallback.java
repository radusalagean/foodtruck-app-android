package com.example.foodtruckclient.dashboard;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;

public class DashboardListDiffCallback extends DiffUtil.ItemCallback<DashboardFoodtruckViewModel> {

    @Override
    public boolean areItemsTheSame(@NonNull DashboardFoodtruckViewModel oldItem, @NonNull DashboardFoodtruckViewModel newItem) {
        return oldItem.getId().equals(newItem.getId());
    }

    @Override
    public boolean areContentsTheSame(@NonNull DashboardFoodtruckViewModel oldItem, @NonNull DashboardFoodtruckViewModel newItem) {
        return oldItem.equals(newItem);
    }
}
