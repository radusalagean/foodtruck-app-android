package com.busytrack.foodtruckclient.generic.list.foodtruck;

import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.signature.ObjectKey;
import com.busytrack.foodtruckclient.R;
import com.busytrack.foodtruckclient.network.foodtruckapi.model.Foodtruck;

import butterknife.BindView;
import butterknife.ButterKnife;

public class FoodtruckViewHolder extends RecyclerView.ViewHolder {

    @BindView(R.id.image_view)
    ImageView imageView;

    @BindView(R.id.text_view_title)
    TextView titleTextView;

    @BindView(R.id.rating_bar)
    RatingBar ratingBar;

    @BindView(R.id.text_view_rating_count)
    TextView ratingCountTextView;

    @BindView(R.id.image_view_owner)
    ImageView ownerImageView;

    @BindView(R.id.text_view_owner)
    TextView ownerTextView;

    @BindView(R.id.image_button_show_on_map)
    ImageButton showOnMapButton;

    public FoodtruckViewHolder(@NonNull View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
    }

    public void bind(Foodtruck foodtruck, boolean cardStyle, FoodtruckContract listener) {
        Glide.with(imageView)
                .load(cardStyle ? foodtruck.getImageUrl() : foodtruck.getThumbnailUrl())
                .centerCrop()
                .placeholder(R.drawable.ic_fastfood_24dp)
                .signature(new ObjectKey(cardStyle ?
                        foodtruck.getImageSignature() : foodtruck.getThumbnailSignature()))
                .into(imageView);
        titleTextView.setText(foodtruck.getName());
        ratingBar.setRating(foodtruck.getAverageRating());
        final int ratingCountResId =
                foodtruck.getRatingCount() == 1 ? R.string.rating_count_singular :
                        R.string.rating_count;
        ratingCountTextView.setText(ratingCountTextView.getResources()
                .getString(ratingCountResId, foodtruck.getRatingCount()));
        Glide.with(ownerImageView)
                .load(foodtruck.getOwner().getThumbnailUrl())
                .circleCrop()
                .placeholder(R.drawable.ic_account_circle_black_24dp)
                .signature(new ObjectKey(foodtruck.getOwner().getThumbnailSignature()))
                .into(ownerImageView);
        ownerTextView.setText(foodtruck.getOwner().getUsername());
        showOnMapButton.setOnClickListener((view) -> {
            listener.onFoodtruckLocationButtonClicked(foodtruck);
        });
        itemView.setOnClickListener((view) -> {
            listener.onFoodtruckSelected(foodtruck);
        });
    }

    public void recycle() {
        Glide.with(imageView.getContext().getApplicationContext()).clear(imageView);
        imageView.setImageDrawable(null);
        titleTextView.setText(null);
        ratingBar.setRating(0f);
        Glide.with(ownerImageView.getContext().getApplicationContext()).clear(ownerImageView);
        ownerImageView.setImageDrawable(null);
        ownerTextView.setText(null);
        showOnMapButton.setOnClickListener(null);
        itemView.setOnClickListener(null);
    }

    public void setLocationButtonVisible(boolean visible) {
        showOnMapButton.setVisibility(visible ? View.VISIBLE : View.GONE);
    }
}
