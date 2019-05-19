package com.example.foodtruckclient.screens.foodtruckviewer;

import android.text.format.DateFormat;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.foodtruckclient.R;
import com.example.foodtruckclient.generic.date.DateConstants;
import com.example.foodtruckclient.network.foodtruckapi.model.Foodtruck;
import com.example.foodtruckclient.view.TagLayout;
import com.google.android.gms.maps.MapView;

import butterknife.BindView;
import butterknife.ButterKnife;
import timber.log.Timber;

public class FoodtruckViewerHeaderViewHolder extends RecyclerView.ViewHolder {

    @BindView(R.id.foodtruck_viewer_root_layout)
    ViewGroup rootLayout;

    @BindView(R.id.foodtruck_viewer_tag_layout)
    TagLayout tagLayout;

    @BindView(R.id.foodtruck_viewer_map_view)
    MapView mapView;

    @BindView(R.id.layout_foodtruck_owner_image_view)
    ImageView ownerImageView;

    @BindView(R.id.text_view_owner_username)
    TextView ownerUsernameTextView;

    @BindView(R.id.text_view_created_date)
    TextView createDateTextView;

    @BindView(R.id.text_view_last_update_date)
    TextView lastUpdateDateTextView;

    @BindView(R.id.text_view_rating_count)
    TextView ratingCountTextView;

    @BindView(R.id.average_rating_bar)
    RatingBar averageRatingBar;

    public FoodtruckViewerHeaderViewHolder(@NonNull View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
    }

    public void bind(@Nullable Foodtruck foodtruck, FoodtruckViewerContract listener) {
        Timber.d("bind(%s, %s)", foodtruck, listener);
        if (foodtruck == null) {
            rootLayout.setVisibility(View.INVISIBLE);
            return;
        }
        rootLayout.setVisibility(View.VISIBLE);
        tagLayout.setTags(foodtruck.getFoodtypes());
        Glide.with(ownerImageView)
                .load(foodtruck.getOwner().getThumbnailUrl())
                .centerCrop()
                .apply(RequestOptions.circleCropTransform())
                .placeholder(R.drawable.ic_account_circle_black_24dp)
                .into(ownerImageView);
        ownerUsernameTextView.setText(foodtruck.getOwner().getUsername());
        createDateTextView.setText(DateFormat.format(DateConstants.DATE_FORMAT, foodtruck.getCreated()));
        lastUpdateDateTextView.setText(DateFormat.format(DateConstants.DATE_FORMAT, foodtruck.getLastUpdate()));
        ratingCountTextView.setText(String.valueOf(foodtruck.getRatingCount()));
        averageRatingBar.setRating(foodtruck.getAverageRating());
        listener.takeMapView(mapView);
    }

    public void recycle() {
        Timber.d("recycle()");
        tagLayout.clearTags();
        Glide.with(ownerImageView).clear(ownerImageView);
        ownerUsernameTextView.setText(null);
        createDateTextView.setText(null);
        lastUpdateDateTextView.setText(null);
        ratingCountTextView.setText(null);
        averageRatingBar.setRating(0.0f);
    }
}
