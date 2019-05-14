package com.example.foodtruckclient.screens.foodtruckviewer;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.foodtruckclient.R;
import com.example.foodtruckclient.network.foodtruckapi.model.Foodtruck;
import com.example.foodtruckclient.network.foodtruckapi.model.Review;

import java.util.ArrayList;
import java.util.List;

public class FoodtruckViewerAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Foodtruck foodtruck;
    private List<Review> reviews;
    private FoodtruckViewerListener listener;

    public FoodtruckViewerAdapter(FoodtruckViewerListener listener) {
        this.listener = listener;
        reviews = new ArrayList<>();
        setHasStableIds(true);
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view;
        if (viewType == FoodtruckViewerItemViewType.HEADER) {
            view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.item_foodtruck_viewer_header, parent, false);
            return new FoodtruckViewerHeaderViewHolder(view);
        } else {
            view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.item_foodtruck_review , parent, false);
            return new FoodtruckViewerReviewViewHolder(view);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof FoodtruckViewerHeaderViewHolder) {
            ((FoodtruckViewerHeaderViewHolder) holder).bind(foodtruck, listener);
        } else {
            ((FoodtruckViewerReviewViewHolder) holder).bind(getReviewByAdapterPosition(position));
        }
    }

    @Override
    public void onViewRecycled(@NonNull RecyclerView.ViewHolder holder) {
        if (holder instanceof FoodtruckViewerHeaderViewHolder) {
            ((FoodtruckViewerHeaderViewHolder) holder).recycle();
        } else {
            ((FoodtruckViewerReviewViewHolder) holder).recycle();
        }
    }

    @Override
    public int getItemCount() {
        return reviews.size() + 1;
    }

    @Override
    public int getItemViewType(int position) {
        if (position == 0) {
            return FoodtruckViewerItemViewType.HEADER;
        }
        return FoodtruckViewerItemViewType.REVIEW;
    }

    @Override
    public long getItemId(int position) {
        if (getItemViewType(position) == FoodtruckViewerItemViewType.HEADER) {
            return foodtruck != null ? foodtruck.getId().hashCode() : View.NO_ID;
        } else {
            Review review = getReviewByAdapterPosition(position);
            return review != null ? review.getId().hashCode() : View.NO_ID;
        }
    }

    private Review getReviewByAdapterPosition(int position) {
        return reviews.get(position - 1);
    }

    public void setFoodtruck(Foodtruck foodtruck) {
        this.foodtruck = foodtruck;
        notifyDataSetChanged();
    }

    public void setReviews(List<Review> reviews) {
        this.reviews = reviews;
        notifyDataSetChanged();
    }
}
