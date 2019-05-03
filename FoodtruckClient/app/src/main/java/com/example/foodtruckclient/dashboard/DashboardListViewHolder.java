package com.example.foodtruckclient.dashboard;

import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.foodtruckclient.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class DashboardListViewHolder extends RecyclerView.ViewHolder {

    @BindView(R.id.image_view)
    ImageView imageView;

    @BindView(R.id.text_view_title)
    TextView titleTextView;

    @BindView(R.id.rating_bar)
    RatingBar ratingBar;

    @BindView(R.id.text_view_rating_count)
    TextView ratingCountTextView;

    @BindView(R.id.image_button_show_on_map)
    ImageButton showOnMapButton;

    public DashboardListViewHolder(@NonNull View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
    }

    public void bind(DashboardFoodtruckViewModel model, DashboardMVP.Presenter presenter) {
        Glide.with(imageView)
                .load(model.getThumbnailUrl())
                .centerCrop()
                .placeholder(R.drawable.ic_fastfood_24dp)
                .into(imageView);
        titleTextView.setText(model.getName());
        ratingBar.setRating(model.getAverageRating());
        final int ratingCountResId =
                model.getRatingCount() == 1 ? R.string.dashboard_list_item_rating_count_singular :
                        R.string.dashboard_list_item_rating_count;
        ratingCountTextView.setText(ratingCountTextView.getResources()
                .getString(ratingCountResId, model.getRatingCount()));
        showOnMapButton.setOnClickListener((view) -> {
            presenter.zoomOnLocation(model.getLatitude(), model.getLongitude());
        });
    }

    public void recycle() {
        Glide.with(imageView).clear(imageView);
        imageView.setImageDrawable(null);
        titleTextView.setText(null);
        ratingBar.setRating(0.0f);
        showOnMapButton.setOnClickListener(null);
    }
}
