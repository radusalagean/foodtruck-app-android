package com.example.foodtruckclient.dashboard;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.ListAdapter;

import com.example.foodtruckclient.R;

public class DashboardListAdapter extends ListAdapter<DashboardFoodtruckViewModel, DashboardListViewHolder> {

    private DashboardMVP.Presenter presenter;

    public DashboardListAdapter(DashboardMVP.Presenter presenter) {
        super(new DashboardListDiffCallback());
        this.presenter = presenter;
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
        holder.bind(getItem(position), presenter);
    }

    @Override
    public void onViewRecycled(@NonNull DashboardListViewHolder holder) {
        holder.recycle();
    }
}
