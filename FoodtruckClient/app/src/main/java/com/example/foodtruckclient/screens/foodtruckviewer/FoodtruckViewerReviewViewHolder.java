package com.example.foodtruckclient.screens.foodtruckviewer;

import android.text.format.DateFormat;
import android.view.View;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.foodtruckclient.R;
import com.example.foodtruckclient.generic.date.DateConstants;
import com.example.foodtruckclient.network.foodtruckapi.model.Review;

import butterknife.BindView;
import butterknife.ButterKnife;
import timber.log.Timber;

public class FoodtruckViewerReviewViewHolder extends RecyclerView.ViewHolder {

    @BindView(R.id.review_image_view_profile_picture)
    ImageView profilePictureImageView;

    @BindView(R.id.review_text_view_author_username)
    TextView authorUsernameTextView;

    @BindView(R.id.review_text_view_last_update)
    TextView lastUpdateTextView;

    @BindView(R.id.review_rating_bar)
    RatingBar ratingBar;

    @BindView(R.id.review_text_view_title)
    TextView titleTextView;

    @BindView(R.id.review_text_view_content)
    TextView contentTextView;


    public FoodtruckViewerReviewViewHolder(@NonNull View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
    }

    public void bind(Review review) {
        Timber.d("bind(%s)", review);
        Glide.with(profilePictureImageView)
                .load(review.getAuthor().getThumbnailUrl())
                .circleCrop()
                .apply(RequestOptions.circleCropTransform())
                .placeholder(R.drawable.ic_account_circle_black_24dp)
                .into(profilePictureImageView);
        authorUsernameTextView.setText(review.getAuthor().getUsername());
        boolean edited = !review.getCreated().equals(review.getLastUpdate());
        String lastUpdateString = (String) DateFormat.format(DateConstants.DATE_FORMAT, review.getLastUpdate());
        String editedString = lastUpdateTextView.getResources().getString(R.string.foodtruck_review_edited);
        String text;
        if (edited) {
            text = lastUpdateTextView.getResources().getString(
                    R.string.foodtruck_review_date_created_edited_pattern, lastUpdateString, editedString
            );
            lastUpdateTextView.setText(text);
        } else {
            text = lastUpdateTextView.getResources().getString(
                    R.string.foodtruck_review_date_created_pattern, lastUpdateString
            );
            lastUpdateTextView.setText(text);
        }
        ratingBar.setRating(review.getRating());
        titleTextView.setText(review.getTitle());
        contentTextView.setText(review.getText());
    }

    public void recycle() {
        Timber.d("recycle()");
        Glide.with(profilePictureImageView).clear(profilePictureImageView);
        authorUsernameTextView.setText(null);
        lastUpdateTextView.setText(null);
        ratingBar.setRating(0.0f);
        titleTextView.setText(null);
        contentTextView.setText(null);
    }
}