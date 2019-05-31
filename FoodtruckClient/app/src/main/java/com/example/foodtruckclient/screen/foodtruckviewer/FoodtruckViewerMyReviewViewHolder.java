package com.example.foodtruckclient.screen.foodtruckviewer;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.example.foodtruckclient.R;
import com.example.foodtruckclient.network.foodtruckapi.model.Foodtruck;
import com.example.foodtruckclient.network.foodtruckapi.model.Review;

import java.util.List;

import butterknife.BindView;
import butterknife.BindViews;
import butterknife.ButterKnife;
import butterknife.ViewCollections;

public class FoodtruckViewerMyReviewViewHolder extends RecyclerView.ViewHolder {

    @BindView(R.id.foodtruck_viewer_text_view_my_review_hint)
    TextView hintTextView;

    @BindView(R.id.foodtruck_viewer_constraint_layout_my_review)
    ConstraintLayout myReviewConstraintLayout;

    @BindView(R.id.foodtruck_viewer_edit_text_review_title)
    EditText reviewTitleEditText;

    @BindView(R.id.foodtruck_viewer_edit_text_review_content)
    EditText reviewContentEditText;

    @BindView(R.id.foodtruck_viewer_my_review_rating_bar)
    RatingBar ratingBar;

    @BindView(R.id.foodtruck_viewer_my_review_submit_button)
    Button submitButton;

    @BindView(R.id.foodtruck_viewer_my_review_edit_button)
    Button editButton;

    @BindView(R.id.foodtruck_viewer_my_review_remove_button)
    Button removeButton;

    @BindView(R.id.foodtruck_viewer_my_review_save_button)
    Button saveButton;

    @BindView(R.id.foodtruck_viewer_my_review_cancel_button)
    Button cancelButton;

    @BindViews({
            R.id.foodtruck_viewer_edit_text_review_title,
            R.id.foodtruck_viewer_edit_text_review_content
    })
    List<EditText> editTexts;

    @BindViews({
            R.id.foodtruck_viewer_my_review_edit_button,
            R.id.foodtruck_viewer_my_review_remove_button
    })
    List<Button> reviewSubmittedButtons;

    @BindViews({
            R.id.foodtruck_viewer_my_review_save_button,
            R.id.foodtruck_viewer_my_review_cancel_button
    })
    List<Button> editSubmittedReviewButtons;

    private int currentState;
    private String tmpTitle;
    private String tmpContent;
    private float tmpRating;

    private TextWatcher textWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            refreshSubmissionButtonsEnabledState();
        }

        @Override
        public void afterTextChanged(Editable s) {}
    };

    public FoodtruckViewerMyReviewViewHolder(@NonNull View itemView) {
        super(itemView);
        currentState = FoodtruckViewerMyReviewState.UNKNOWN;
        ButterKnife.bind(this, itemView);
    }

    public void bind(@Nullable Review myReview, Foodtruck foodtruck, FoodtruckViewerContract contract) {
        if (currentState == FoodtruckViewerMyReviewState.UNKNOWN ||
                currentState == FoodtruckViewerMyReviewState.OWN_FOODTRUCK) {
            initCurrentState(myReview, foodtruck, contract);
        }
        syncViews();
        reviewTitleEditText.addTextChangedListener(textWatcher);
        reviewContentEditText.addTextChangedListener(textWatcher);
        ratingBar.setOnRatingBarChangeListener(((ratingBar1, rating, fromUser) ->
                refreshSubmissionButtonsEnabledState()));
        submitButton.setOnClickListener(v -> {
            setCurrentState(FoodtruckViewerMyReviewState.UNKNOWN);
            contract.submitReview(
                    reviewTitleEditText.getText().toString().trim(),
                    reviewContentEditText.getText().toString().trim(),
                    ratingBar.getRating());
            ViewCollections.run(editTexts, ((view, index) -> view.setEnabled(false)));
        });
        editButton.setOnClickListener(v -> {
            assignTmpFields();
            setCurrentState(FoodtruckViewerMyReviewState.EDIT_SUBMITTED_REVIEW);
        });
        cancelButton.setOnClickListener(v -> {
            restoreTmpFields();
            clearTmpFields();
            setCurrentState(FoodtruckViewerMyReviewState.REVIEW_SUBMITTED);
        });
        if (myReview != null) {
            reviewTitleEditText.setText(myReview.getTitle());
            reviewContentEditText.setText(myReview.getText());
            ratingBar.setRating(myReview.getRating());
            saveButton.setOnClickListener(v -> {
                clearTmpFields();
                setCurrentState(FoodtruckViewerMyReviewState.UNKNOWN);
                contract.updateReview(
                        myReview.getId(),
                        reviewTitleEditText.getText().toString().trim(),
                        reviewContentEditText.getText().toString().trim(),
                        ratingBar.getRating()
                );
                ViewCollections.run(editTexts, ((view, index) -> view.setEnabled(false)));
            });
            removeButton.setOnClickListener((v -> {
                setCurrentState(FoodtruckViewerMyReviewState.UNKNOWN);
                contract.removeReview(myReview.getId());
            }));
        }
    }

    public void recycle() {
        reviewTitleEditText.setText(null);
        reviewContentEditText.setText(null);
        ratingBar.setRating(0.0f);
        submitButton.setOnClickListener(null);
        saveButton.setOnClickListener(null);
        removeButton.setOnClickListener(null);
        cancelButton.setOnClickListener(null);
        editButton.setOnClickListener(null);
        reviewTitleEditText.removeTextChangedListener(textWatcher);
        reviewContentEditText.removeTextChangedListener(textWatcher);
        ratingBar.setOnRatingBarChangeListener(null);
        hintTextView.setText(null);
    }

    private void refreshSubmissionButtonsEnabledState() {
        boolean valid = areFieldsValid();
        submitButton.setEnabled(valid);
        saveButton.setEnabled(valid);
    }

    private void assignTmpFields() {
        tmpTitle = reviewTitleEditText.getText().toString();
        tmpContent = reviewContentEditText.getText().toString();
        tmpRating = ratingBar.getRating();
    }

    private void restoreTmpFields() {
        reviewTitleEditText.setText(tmpTitle);
        reviewContentEditText.setText(tmpContent);
        ratingBar.setRating(tmpRating);
    }

    private void clearTmpFields() {
        tmpTitle = null;
        tmpContent = null;
        tmpRating = 0.0f;
    }

    private boolean areFieldsValid() {
        return reviewTitleEditText.getText().toString().trim().length() > 0 &&
                reviewContentEditText.getText().toString().trim().length() > 0 &&
                ratingBar.getRating() >= 1.0f;
    }

    private void initCurrentState(Review myReview, Foodtruck foodtruck, FoodtruckViewerContract contract) {
        if (!contract.isUserAuthenticated()) {
            currentState = FoodtruckViewerMyReviewState.NOT_AUTHENTICATED;
        } else if (foodtruck == null || foodtruck.getOwner() == null ||
                foodtruck.getOwner().getId().equals(contract.getAuthenticatedUserId())) {
            currentState = FoodtruckViewerMyReviewState.OWN_FOODTRUCK;
        } else if (myReview == null || myReview.getId() == null) {
            currentState = FoodtruckViewerMyReviewState.NO_REVIEW_SUBMITTED;
        } else if (myReview.getId() != null) {
            currentState = FoodtruckViewerMyReviewState.REVIEW_SUBMITTED;
        }
    }

    private void setCurrentState(int currentState) {
        this.currentState = currentState;
        syncViews();
    }

    private void syncViews() {
        myReviewConstraintLayout.setVisibility(View.VISIBLE);
        hintTextView.setVisibility(View.GONE);
        hintTextView.setText(null);
        ViewCollections.run(editTexts, ((view, index) -> {
            view.setVisibility(View.VISIBLE);
            view.setEnabled(true);
        }));
        ratingBar.setVisibility(View.VISIBLE);
        ratingBar.setIsIndicator(false);
        submitButton.setVisibility(View.GONE);
        submitButton.setEnabled(true);
        ViewCollections.run(reviewSubmittedButtons, ((view, index) -> {
            view.setVisibility(View.GONE);
            view.setEnabled(true);
        }));
        ViewCollections.run(editSubmittedReviewButtons, ((view, index) -> {
            view.setVisibility(View.GONE);
            view.setEnabled(true);
        }));
        switch (currentState) {
            case FoodtruckViewerMyReviewState.NOT_AUTHENTICATED:
                myReviewConstraintLayout.setVisibility(View.GONE);
                hintTextView.setText(R.string.foodtruck_my_review_login_hint);
                hintTextView.setVisibility(View.VISIBLE);
                break;
            case FoodtruckViewerMyReviewState.NO_REVIEW_SUBMITTED:
                submitButton.setVisibility(View.VISIBLE);
                submitButton.setEnabled(areFieldsValid());
                break;
            case FoodtruckViewerMyReviewState.REVIEW_SUBMITTED:
                ViewCollections.run(editTexts, ((view, index) ->
                        view.setEnabled(false)));
                ratingBar.setIsIndicator(true);
                ViewCollections.run(reviewSubmittedButtons, ((view, index) ->
                        view.setVisibility(View.VISIBLE)));
                break;
            case FoodtruckViewerMyReviewState.EDIT_SUBMITTED_REVIEW:
                ViewCollections.run(editTexts, ((view, index) ->
                        view.setEnabled(true)));
                ViewCollections.run(editSubmittedReviewButtons, ((view, index) ->
                        view.setVisibility(View.VISIBLE)));
                saveButton.setEnabled(areFieldsValid());
                break;
            case FoodtruckViewerMyReviewState.OWN_FOODTRUCK:
                myReviewConstraintLayout.setVisibility(View.GONE);
                hintTextView.setText(R.string.foodtruck_my_review_own_foodtruck);
                hintTextView.setVisibility(View.VISIBLE);
                break;
        }
    }
}
