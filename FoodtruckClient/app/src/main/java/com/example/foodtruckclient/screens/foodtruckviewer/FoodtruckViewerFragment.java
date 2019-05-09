package com.example.foodtruckclient.screens.foodtruckviewer;

import android.content.Context;
import android.os.Bundle;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.appcompat.widget.Toolbar;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.bumptech.glide.Glide;
import com.example.foodtruckclient.R;
import com.example.foodtruckclient.generic.activity.ActivityContract;
import com.example.foodtruckclient.generic.fragment.BaseFragment;
import com.example.foodtruckclient.network.foodtruckapi.model.Foodtruck;
import com.example.foodtruckclient.network.foodtruckapi.model.Review;
import com.example.foodtruckclient.view.StateAwareAppBarLayout;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

public class FoodtruckViewerFragment extends BaseFragment
        implements FoodtruckViewerMVP.View {

    private static final String ARG_FOODTRUCK_ID = "foodtruck_id";
    private static final String ARG_FOODTRUCK_NAME = "foodtruck_name";

    private String foodtruckId;
    private String foodtruckName;

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @BindView(R.id.foodtruck_viewer_swipe_to_refresh_layout)
    SwipeRefreshLayout swipeRefreshLayout;

    @BindView(R.id.app_bar)
    StateAwareAppBarLayout appBarLayout;

    @BindView(R.id.foodtruck_viewer_image_view_top)
    ImageView topImageView;

    @Inject
    FoodtruckViewerMVP.Presenter presenter;

    @Inject
    ActivityContract activityContract;

    private StateAwareAppBarLayout.OnStateChangedListener appBarOnStateChangeListener = state -> {
        swipeRefreshLayout.setEnabled(state == StateAwareAppBarLayout.STATE_EXPANDED);
    };

    public FoodtruckViewerFragment() {}

    public static FoodtruckViewerFragment newInstance(String foodtruckId, String foodtruckName) {
        FoodtruckViewerFragment fragment = new FoodtruckViewerFragment();
        Bundle args = new Bundle();
        fragment.generateUniqueId(args);
        args.putString(ARG_FOODTRUCK_ID, foodtruckId);
        args.putString(ARG_FOODTRUCK_NAME, foodtruckName);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onAttach(Context context) {
        getControllerComponent().inject(this);
        super.onAttach(context);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            foodtruckId = getArguments().getString(ARG_FOODTRUCK_ID);
            foodtruckName = getArguments().getString(ARG_FOODTRUCK_NAME);
        }
        //
        presenter.loadViewModel(foodtruckId, false);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_foodtruck_viewer, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        presenter.takeView(this);
    }

    @Override
    public void onStop() {
        super.onStop();
        presenter.dropView();
    }

    @Override
    protected void initViews() {
        toolbar.setTitle(foodtruckName);
        activityContract.setActionBar(toolbar);
        swipeRefreshLayout.setColorSchemeResources(R.color.colorAccent);
    }

    @Override
    protected void registerListeners() {
        swipeRefreshLayout.setOnRefreshListener(() -> presenter.reloadData(foodtruckId));
        appBarLayout.setOnStateChangedListener(appBarOnStateChangeListener);
    }

    @Override
    protected void unregisterListeners() {
        swipeRefreshLayout.setOnRefreshListener(null);
        appBarLayout.setOnStateChangedListener(null);
    }

    @Override
    public void updateFoodtruck(Foodtruck foodtruck) {
        if (swipeRefreshLayout != null && swipeRefreshLayout.isRefreshing()) {
            swipeRefreshLayout.setRefreshing(false);
        }
        Glide.with(topImageView)
                .load(foodtruck.getImageUrl())
                .centerCrop()
                .into(topImageView);
    }

    @Override
    public void updateReviews(List<Review> reviews) {

    }

    @Override
    public void updateMyReview(Review myReview) {

    }
}
