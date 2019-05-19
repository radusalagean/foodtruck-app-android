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
    private Review myReview;
    private List<Review> reviews;
    private FoodtruckViewerContract contract;

    public FoodtruckViewerAdapter(FoodtruckViewerContract contract) {
        this.contract = contract;
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
        } else if (viewType == FoodtruckViewerItemViewType.MY_REVIEW) {
            view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.item_foodtruck_my_review, parent, false);
            return new FoodtruckViewerMyReviewViewHolder(view);
        } else {
            view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.item_foodtruck_review , parent, false);
            return new FoodtruckViewerReviewViewHolder(view);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof FoodtruckViewerHeaderViewHolder) {
            ((FoodtruckViewerHeaderViewHolder) holder).bind(foodtruck, contract);
        } else if (holder instanceof FoodtruckViewerMyReviewViewHolder) {
            ((FoodtruckViewerMyReviewViewHolder) holder).bind(myReview, contract);
        } else {
            ((FoodtruckViewerReviewViewHolder) holder).bind(getReviewByAdapterPosition(position));
        }
    }

    @Override
    public void onViewRecycled(@NonNull RecyclerView.ViewHolder holder) {
        if (holder instanceof FoodtruckViewerHeaderViewHolder) {
            ((FoodtruckViewerHeaderViewHolder) holder).recycle();
        } else if (holder instanceof FoodtruckViewerMyReviewViewHolder) {
            ((FoodtruckViewerMyReviewViewHolder) holder).recycle();
        } else {
            ((FoodtruckViewerReviewViewHolder) holder).recycle();
        }
    }

    @Override
    public int getItemCount() {
        return reviews.size() + 2;
    }

    @Override
    public int getItemViewType(int position) {
        if (position == 0) {
            return FoodtruckViewerItemViewType.HEADER;
        } else if (position == 1) {
            return FoodtruckViewerItemViewType.MY_REVIEW;
        }
        return FoodtruckViewerItemViewType.REVIEW;
    }

    @Override
    public long getItemId(int position) {
        if (getItemViewType(position) == FoodtruckViewerItemViewType.HEADER) {
            return foodtruck != null ? foodtruck.getId().hashCode() : View.NO_ID;
        } else if (getItemViewType(position) == FoodtruckViewerItemViewType.MY_REVIEW) {
            return myReview != null && myReview.getId() != null ?
                    foodtruck.getId().hashCode() >> 1 : View.NO_ID;
        } else {
            Review review = getReviewByAdapterPosition(position);
            return review != null ? review.getId().hashCode() : View.NO_ID;
        }
    }

    private Review getReviewByAdapterPosition(int position) {
        return reviews.get(position - 2);
    }

    public void setFoodtruck(Foodtruck foodtruck) {
        this.foodtruck = foodtruck;
        notifyDataSetChanged();
    }

    public void setMyReview(Review myReview) {
        this.myReview = myReview;
        notifyDataSetChanged();
    }

    public void setReviews(List<Review> reviews) {
        this.reviews = reviews;
        notifyDataSetChanged();
    }
}
