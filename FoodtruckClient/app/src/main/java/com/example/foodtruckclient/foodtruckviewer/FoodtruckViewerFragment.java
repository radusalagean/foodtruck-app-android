package com.example.foodtruckclient.foodtruckviewer;

import android.content.Context;
import android.os.Bundle;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.appcompat.widget.Toolbar;

import com.example.foodtruckclient.R;
import com.example.foodtruckclient.generic.activity.ActivityContract;
import com.example.foodtruckclient.generic.fragment.BaseFragment;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

public class FoodtruckViewerFragment extends BaseFragment {

    private static final String ARG_FOODTRUCK_ID = "foodtruck_id";
    private static final String ARG_FOODTRUCK_NAME = "foodtruck_name";

    private String foodtruckId;
    private String foodtruckName;

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @BindView(R.id.foodtruck_viewer_image_view_top)
    ImageView topImageView;

    @Inject
    ActivityContract activityContract;

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
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_foodtruck_viewer, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    protected void initViews() {
        toolbar.setTitle(foodtruckName);
        activityContract.setActionBar(toolbar);

    }

    @Override
    protected void registerListeners() {

    }

    @Override
    protected void unregisterListeners() {

    }
}
