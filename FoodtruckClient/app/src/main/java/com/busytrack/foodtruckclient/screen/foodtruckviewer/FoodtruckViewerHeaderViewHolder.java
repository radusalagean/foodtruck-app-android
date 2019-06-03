package com.busytrack.foodtruckclient.screen.foodtruckviewer;

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
import com.bumptech.glide.signature.ObjectKey;
import com.busytrack.foodtruckclient.R;
import com.busytrack.foodtruckclient.generic.date.DateConstants;
import com.busytrack.foodtruckclient.network.foodtruckapi.model.Foodtruck;
import com.busytrack.foodtruckclient.util.NumberUtils;
import com.busytrack.foodtruckclient.view.TagLayout;

import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import timber.log.Timber;

public class FoodtruckViewerHeaderViewHolder extends RecyclerView.ViewHolder {

    @BindView(R.id.foodtruck_viewer_root_layout)
    ViewGroup rootLayout;

    @BindView(R.id.foodtruck_viewer_tag_layout)
    TagLayout tagLayout;

    @BindView(R.id.layout_foodtruck_owner_image_view)
    ImageView ownerImageView;

    @BindView(R.id.text_view_owner_username)
    TextView ownerUsernameTextView;

    @BindView(R.id.text_view_created_date)
    TextView createDateTextView;

    @BindView(R.id.text_view_last_update_date)
    TextView lastUpdateDateTextView;

    @BindView(R.id.text_view_rating_average)
    TextView ratingAverageTextView;

    @BindView(R.id.rating_bar_average)
    RatingBar averageRatingBar;

    @BindView(R.id.text_view_rating_count)
    TextView ratingCountTextView;

    public FoodtruckViewerHeaderViewHolder(@NonNull View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
    }

    public void bind(@Nullable Foodtruck foodtruck, FoodtruckViewerContract contract) {
        Timber.d("bind(%s, %s)", foodtruck, contract);
        if (foodtruck == null) {
            rootLayout.setVisibility(View.INVISIBLE);
            return;
        }
        rootLayout.setVisibility(View.VISIBLE);
        tagLayout.setTags(foodtruck.getFoodtypes());
        Glide.with(ownerImageView)
                .load(foodtruck.getOwner().getThumbnailUrl())
                .circleCrop()
                .placeholder(R.drawable.ic_account_circle_black_24dp)
                .signature(new ObjectKey(foodtruck.getOwner().getImageSignature()))
                .into(ownerImageView);
        ownerImageView.setOnClickListener(v -> contract.onAccountSelected(foodtruck.getOwner()));
        ownerUsernameTextView.setText(foodtruck.getOwner().getUsername());
        ownerUsernameTextView.setOnClickListener(v -> contract.onAccountSelected(foodtruck.getOwner()));
        createDateTextView.setText(DateFormat.format(DateConstants.DATE_FORMAT, foodtruck.getCreated()));
        lastUpdateDateTextView.setText(DateFormat.format(DateConstants.DATE_FORMAT, foodtruck.getLastUpdate()));
        if (foodtruck.getAverageRating() > 0.0f) {
            ratingAverageTextView.setText(String
                    .format(Locale.US, NumberUtils.AVERAGE_RATING_FORMAT, foodtruck.getAverageRating()));
        } else {
            ratingAverageTextView.setText(ratingAverageTextView
                    .getResources().getString(R.string.foodtruck_rating_not_available));
        }
        averageRatingBar.setRating(foodtruck.getAverageRating());
        final int ratingCountResId =
                foodtruck.getRatingCount() == 1 ? R.string.rating_count_singular :
                        R.string.rating_count;
        ratingCountTextView.setText(ratingCountTextView.getResources()
                .getString(ratingCountResId, foodtruck.getRatingCount()));
    }

    public void recycle() {
        Timber.d("recycle()");
        tagLayout.clearTags();
        Glide.with(ownerImageView.getContext().getApplicationContext()).clear(ownerImageView);
        ownerImageView.setImageDrawable(null);
        ownerImageView.setOnClickListener(null);
        ownerUsernameTextView.setText(null);
        ownerUsernameTextView.setOnClickListener(null);
        createDateTextView.setText(null);
        lastUpdateDateTextView.setText(null);
        ratingAverageTextView.setText(null);
        averageRatingBar.setRating(0f);
        ratingCountTextView.setText(null);
    }
}
