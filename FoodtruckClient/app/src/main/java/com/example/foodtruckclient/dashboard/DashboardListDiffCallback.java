package com.example.foodtruckclient.dashboard;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;

import com.example.foodtruckclient.network.foodtruckapi.model.Foodtruck;

public class DashboardListDiffCallback extends DiffUtil.ItemCallback<Foodtruck> {

    @Override
    public boolean areItemsTheSame(@NonNull Foodtruck oldItem, @NonNull Foodtruck newItem) {
        return oldItem.getId().equals(newItem.getId());
    }

    @Override
    public boolean areContentsTheSame(@NonNull Foodtruck oldItem, @NonNull Foodtruck newItem) {
        return oldItem.equals(newItem);
    }
}
