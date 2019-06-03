package com.busytrack.foodtruckclient.screen.dashboard;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.busytrack.foodtruckclient.R;
import com.busytrack.foodtruckclient.generic.list.foodtruck.FoodtruckContract;
import com.busytrack.foodtruckclient.generic.list.foodtruck.FoodtruckViewHolder;
import com.busytrack.foodtruckclient.network.foodtruckapi.model.Foodtruck;

import java.util.ArrayList;
import java.util.List;

public class DashboardListAdapter extends RecyclerView.Adapter<FoodtruckViewHolder> {

    private List<Foodtruck> foodtrucks;
    private FoodtruckContract listener;
    private @DashboardLayoutType int layoutType;

    public DashboardListAdapter(FoodtruckContract listener, int layoutType) {
        this.listener = listener;
        this.layoutType = layoutType;
        foodtrucks = new ArrayList<>();
        setHasStableIds(true);
    }

    public void setLayoutType(int layoutType) {
        if (this.layoutType != layoutType) {
            this.layoutType = layoutType;
            notifyDataSetChanged();
        }
    }

    @NonNull
    @Override
    public FoodtruckViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        int layoutId = layoutType == DashboardLayoutType.GRID ?
                R.layout.item_foodtruck_card_style : R.layout.item_foodtruck_list_style;
        View view = LayoutInflater.from(parent.getContext())
                .inflate(layoutId, parent, false);
        return new FoodtruckViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull FoodtruckViewHolder holder, int position) {
        holder.bind(foodtrucks.get(position), layoutType == DashboardLayoutType.GRID, listener);
    }

    @Override
    public void onViewRecycled(@NonNull FoodtruckViewHolder holder) {
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
        if (this.foodtrucks == foodtrucks) {
            return;
        }
        this.foodtrucks = foodtrucks;
        notifyDataSetChanged();
    }

    public void clearFoodtrucks() {
        foodtrucks.clear();
        notifyDataSetChanged();
    }
}
