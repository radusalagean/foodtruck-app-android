package com.example.foodtruckclient.screen.foodtruckviewer;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.example.foodtruckclient.R;
import com.example.foodtruckclient.network.foodtruckapi.model.Foodtruck;
import com.example.foodtruckclient.network.foodtruckapi.model.Review;

import java.util.ArrayList;
import java.util.List;

import timber.log.Timber;

public class FoodtruckViewerAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private static final int REVIEW_POSITION_OFFSET = 2;

    private Foodtruck foodtruck;
    private Review myReview;
    private List<Review> reviews;
    private FoodtruckViewerContract contract;

    public FoodtruckViewerAdapter(FoodtruckViewerContract contract) {
        this.contract = contract;
        reviews = new ArrayList<>();
        setHasStableIds(true);
    }

    private Review getReview(int adapterPosition) {
        return reviews.get(adapterPosition - REVIEW_POSITION_OFFSET);
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
        Timber.d("onBindViewHolder(%s, %d)", holder.getClass().getSimpleName(), position);
        if (holder instanceof FoodtruckViewerHeaderViewHolder) {
            ((FoodtruckViewerHeaderViewHolder) holder).bind(foodtruck, contract);
        } else if (holder instanceof FoodtruckViewerMyReviewViewHolder) {
            ((FoodtruckViewerMyReviewViewHolder) holder)
                    .bind(myReview, foodtruck, contract);
        } else if (holder instanceof FoodtruckViewerReviewViewHolder) {
            ((FoodtruckViewerReviewViewHolder) holder).bind(getReview(position), contract);
        }
    }

    @Override
    public void onViewDetachedFromWindow(@NonNull RecyclerView.ViewHolder holder) {
        Timber.d("onViewDetachedFromWindow(%s)", holder.getClass().getSimpleName());
        if (holder instanceof FoodtruckViewerMyReviewViewHolder) {
            ((FoodtruckViewerMyReviewViewHolder) holder).onViewDetachedFromWindow();
        }
    }

    @Override
    public void onViewRecycled(@NonNull RecyclerView.ViewHolder holder) {
        Timber.d("onViewRecycled(%s)", holder.getClass().getSimpleName());
        if (holder instanceof FoodtruckViewerHeaderViewHolder) {
            ((FoodtruckViewerHeaderViewHolder) holder).recycle();
        } else if (holder instanceof FoodtruckViewerMyReviewViewHolder) {
            ((FoodtruckViewerMyReviewViewHolder) holder).recycle();
        } else if (holder instanceof FoodtruckViewerReviewViewHolder){
            ((FoodtruckViewerReviewViewHolder) holder).recycle();
        }
    }

    @Override
    public int getItemCount() {
        return reviews.size() + REVIEW_POSITION_OFFSET;
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
            Review review = getReview(position);
            return review != null ? review.getId().hashCode() : View.NO_ID;
        }
    }

    public void setFoodtruck(Foodtruck foodtruck) {
        if (this.foodtruck == foodtruck) {
            return;
        }
        this.foodtruck = foodtruck;
        notifyDataSetChanged();
    }

    public void setMyReview(Review myReview) {
        if (this.myReview == myReview) {
            return;
        }
        this.myReview = myReview;
        notifyDataSetChanged();
    }

    public void setReviews(List<Review> reviews) {
        if (this.reviews == reviews) {
            return;
        }
        this.reviews = reviews;
        notifyDataSetChanged();
    }

    @Nullable
    public String getFoodtruckOwnerId() {
        return foodtruck != null ? foodtruck.getOwner().getId() : null;
    }
}
