package com.example.foodtruckclient.dashboard;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.ListAdapter;

import com.example.foodtruckclient.R;
import com.example.foodtruckclient.network.foodtruckapi.model.Foodtruck;

import java.util.ArrayList;

public class DashboardListAdapter extends ListAdapter<Foodtruck, DashboardListViewHolder> {

    private DashboardListListener listener;

    public DashboardListAdapter(DashboardListListener listener) {
        super(new DashboardListDiffCallback());
        this.listener = listener;
    }

    @NonNull
    @Override
    public DashboardListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_dashboard_list, parent, false);
        return new DashboardListViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull DashboardListViewHolder holder, int position) {
        holder.bind(getItem(position), listener);
    }

    @Override
    public void onViewRecycled(@NonNull DashboardListViewHolder holder) {
        holder.recycle();
    }

    public void clearList() {
        submitList(new ArrayList<>());
    }
}
