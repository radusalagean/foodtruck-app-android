package com.example.foodtruckclient.screens.profile;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.foodtruckclient.R;
import com.example.foodtruckclient.generic.list.foodtruck.FoodtruckContract;
import com.example.foodtruckclient.generic.list.foodtruck.FoodtruckViewHolder;
import com.example.foodtruckclient.network.foodtruckapi.model.Account;
import com.example.foodtruckclient.network.foodtruckapi.model.Foodtruck;

import java.util.ArrayList;
import java.util.List;

public class ProfileAdapter extends RecyclerView.Adapter {

    private static final int FOODTRUCK_POSITION_OFFSET = 1;

    private Account account;
    private List<Foodtruck> foodtrucks;
    private FoodtruckContract contract;

    public ProfileAdapter(FoodtruckContract contract) {
        this.contract = contract;
        foodtrucks = new ArrayList<>();
        setHasStableIds(true);
    }

    private Foodtruck getFoodtruck(int adapterPosition) {
        return foodtrucks.get(adapterPosition - FOODTRUCK_POSITION_OFFSET);
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view;
        if (viewType == ProfileItemViewType.HEADER) {
            view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.item_profile_header, parent, false);
            return new ProfileHeaderViewHolder(view);
        } else {
            view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.item_foodtruck, parent, false);
            FoodtruckViewHolder viewHolder = new FoodtruckViewHolder(view);
            viewHolder.setLocationButtonVisible(false);
            return viewHolder;
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof ProfileHeaderViewHolder) {
            ((ProfileHeaderViewHolder) holder).bind(account);
        } else if (holder instanceof FoodtruckViewHolder) {
            ((FoodtruckViewHolder) holder).bind(getFoodtruck(position), contract);
        }
    }

    @Override
    public void onViewRecycled(@NonNull RecyclerView.ViewHolder holder) {
        if (holder instanceof ProfileHeaderViewHolder) {
            ((ProfileHeaderViewHolder) holder).recycle();
        } else if (holder instanceof FoodtruckViewHolder) {
            ((FoodtruckViewHolder) holder).recycle();
        }
    }

    @Override
    public int getItemCount() {
        return foodtrucks.size() + FOODTRUCK_POSITION_OFFSET;
    }

    @Override
    public int getItemViewType(int position) {
        if (position == 0) {
            return ProfileItemViewType.HEADER;
        }
        return ProfileItemViewType.FOODTRUCK;
    }

    @Override
    public long getItemId(int position) {
        if (getItemViewType(position) == ProfileItemViewType.HEADER) {
            return account != null ? account.getId().hashCode() : View.NO_ID;
        } else {
            Foodtruck foodtruck = getFoodtruck(position);
            return foodtruck != null ? foodtruck.getId().hashCode() : View.NO_ID;
        }
    }

    public void setAccount(Account account) {
        this.account = account;
        notifyDataSetChanged();
    }

    public void setFoodtrucks(List<Foodtruck> foodtrucks) {
        this.foodtrucks = foodtrucks;
        notifyDataSetChanged();
    }

    public boolean hasProfilePicture() {
        return account != null && account.getImage() != null;
    }
}
