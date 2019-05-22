package com.example.foodtruckclient.screens.profile;

import android.text.format.DateFormat;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.signature.ObjectKey;
import com.example.foodtruckclient.R;
import com.example.foodtruckclient.generic.date.DateConstants;
import com.example.foodtruckclient.network.foodtruckapi.model.Account;

import butterknife.BindView;
import butterknife.ButterKnife;
import timber.log.Timber;

public class ProfileHeaderViewHolder extends RecyclerView.ViewHolder {

    @BindView(R.id.profile_header_root_layout)
    ViewGroup rootLayout;

    @BindView(R.id.profile_image_view)
    ImageView imageView;

    @BindView(R.id.profile_text_view_username)
    TextView usernameTextView;

    @BindView(R.id.profile_text_view_joined_date)
    TextView joinedDateTextView;

    public ProfileHeaderViewHolder(@NonNull View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
    }

    public void bind(@Nullable Account account) {
        Timber.d("bind(%s)", account);
        if (account == null) {
            rootLayout.setVisibility(View.INVISIBLE);
            return;
        }
        rootLayout.setVisibility(View.VISIBLE);
        Glide.with(imageView)
                .load(account.getImageUrl())
                .apply(RequestOptions.circleCropTransform())
                .placeholder(R.drawable.ic_account_circle_black_24dp)
                .signature(new ObjectKey(account.getImageUrl() + "@" + account.getLastUpdate()))
                .into(imageView);
        usernameTextView.setText(account.getUsername());
        joinedDateTextView.setText(joinedDateTextView.getResources().getString(
                R.string.profile_joined_date,
                DateFormat.format(DateConstants.DATE_FORMAT, account.getJoined())
        ));
    }

    public void recycle() {
        Timber.d("recycle()");
        Glide.with(imageView).clear(imageView);
        imageView.setImageDrawable(null);
        usernameTextView.setText(null);
        joinedDateTextView.setText(null);
    }
}
