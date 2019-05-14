package com.example.foodtruckclient.screens.dashboard;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.foodtruckclient.R;
import com.example.foodtruckclient.network.foodtruckapi.model.Foodtruck;

import java.util.ArrayList;
import java.util.List;

public class DashboardListAdapter extends RecyclerView.Adapter<DashboardListViewHolder> {

    private List<Foodtruck> foodtrucks;
    private DashboardListListener listener;

    public DashboardListAdapter(DashboardListListener listener) {
        this.listener = listener;
        foodtrucks = new ArrayList<>();
        setHasStableIds(true);
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
        holder.bind(foodtrucks.get(position), listener);
    }

    @Override
    public void onViewRecycled(@NonNull DashboardListViewHolder holder) {
        holder.recycle();
    }
    @Override
    public long getItemId(int position) {
        return foodtrucks.get(position).getId().hashCode();
    }

    @Override
    public int getItemCount() {
        return foodtrucks.size();
    }

    public void setFoodtrucks(List<Foodtruck> foodtrucks) {
        this.foodtrucks = foodtrucks;
        notifyDataSetChanged();
    }

    public void clearFoodtrucks() {
        foodtrucks.clear();
        notifyDataSetChanged();
    }
}
