package com.example.foodtruckclient.screens.dashboard;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.foodtruckclient.R;
import com.example.foodtruckclient.generic.list.foodtruck.FoodtruckViewHolder;
import com.example.foodtruckclient.generic.list.foodtruck.FoodtruckContract;
import com.example.foodtruckclient.network.foodtruckapi.model.Foodtruck;

import java.util.ArrayList;
import java.util.List;

public class DashboardListAdapter extends RecyclerView.Adapter<FoodtruckViewHolder> {

    private List<Foodtruck> foodtrucks;
    private FoodtruckContract listener;

    public DashboardListAdapter(FoodtruckContract listener) {
        this.listener = listener;
        foodtrucks = new ArrayList<>();
        setHasStableIds(true);
    }

    @NonNull
    @Override
    public FoodtruckViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_foodtruck, parent, false);
        return new FoodtruckViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull FoodtruckViewHolder holder, int position) {
        holder.bind(foodtrucks.get(position), listener);
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
        this.foodtrucks = foodtrucks;
        notifyDataSetChanged();
    }

    public void clearFoodtrucks() {
        foodtrucks.clear();
        notifyDataSetChanged();
    }
}
