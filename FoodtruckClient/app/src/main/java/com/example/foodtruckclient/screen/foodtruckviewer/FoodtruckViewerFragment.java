package com.example.foodtruckclient.screen.foodtruckviewer;

import android.animation.Animator;
import android.content.Context;
import android.os.Bundle;

import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.bumptech.glide.Glide;
import com.bumptech.glide.signature.ObjectKey;
import com.example.foodtruckclient.R;
import com.example.foodtruckclient.dialog.DialogManager;
import com.example.foodtruckclient.generic.activity.ActivityContract;
import com.example.foodtruckclient.generic.decoration.ListItemDecoration;
import com.example.foodtruckclient.generic.mapmvp.BaseMapFragment;
import com.example.foodtruckclient.generic.mvp.BaseMVP;
import com.example.foodtruckclient.network.foodtruckapi.model.Account;
import com.example.foodtruckclient.network.foodtruckapi.model.Foodtruck;
import com.example.foodtruckclient.network.foodtruckapi.model.Review;
import com.example.foodtruckclient.view.StateAwareAppBarLayout;
import com.google.android.gms.maps.MapView;
import com.google.android.material.appbar.CollapsingToolbarLayout;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

public class FoodtruckViewerFragment extends BaseMapFragment
        implements FoodtruckViewerMVP.View, FoodtruckViewerContract {

    // Fragment Args
    private static final String ARG_FOODTRUCK_ID = "foodtruck_id";
    private static final String ARG_FOODTRUCK_NAME = "foodtruck_name";

    // Instance State Args
    private static final String ARG_MAP_POPUP_VISIBLE = "map_popup_visible";
    private static final String ARG_INSTANT_ZOOM_ON_MAP = "instant_zoom_on_map";

    private String foodtruckId;
    private String foodtruckName;

    @BindView(R.id.foodtruck_viewer_toolbar_layout)
    CollapsingToolbarLayout collapsingToolbarLayout;

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @BindView(R.id.foodtruck_viewer_swipe_refresh_layout)
    SwipeRefreshLayout swipeRefreshLayout;

    @BindView(R.id.foodtruck_viewer_app_bar_layout)
    StateAwareAppBarLayout appBarLayout;

    @BindView(R.id.foodtruck_viewer_image_view_top)
    ImageView topImageView;

    @BindView(R.id.foodtruck_viewer_recycler_view)
    RecyclerView recyclerView;

    @BindView(R.id.fab)
    FloatingActionButton fab;

    @BindView(R.id.foodtruck_viewer_frame_layout_map_popup)
    FrameLayout mapPopupRootView;

    @BindView(R.id.foodtruck_viewer_map_view)
    MapView mapView;

    @Inject
    FoodtruckViewerMVP.Presenter presenter;

    @Inject
    ActivityContract activityContract;

    @Inject
    DialogManager dialogManager;

    private FoodtruckViewerAdapter adapter;
    private boolean instantZoomOnMap;

    private StateAwareAppBarLayout.OnStateChangedListener appBarOnStateChangeListener = state ->
        swipeRefreshLayout.setEnabled(state == StateAwareAppBarLayout.STATE_EXPANDED);

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

    private void openMapPopup() {
        mapPopupRootView.setAlpha(0f);
        mapPopupRootView.setVisibility(View.VISIBLE);
        mapPopupRootView.animate().alpha(1f)
                .setDuration(getResources().getInteger(
                        android.R.integer.config_shortAnimTime))
                .setListener(null);
        presenter.zoomOnFoodtruck(instantZoomOnMap);
        instantZoomOnMap = true;
        View currentFocus = getView().findFocus();
        if (currentFocus instanceof EditText) {
            currentFocus.clearFocus();
        }
    }

    private void closeMapPopup() {
        mapPopupRootView.setAlpha(1f);
        mapPopupRootView.animate()
                .alpha(0f)
                .setDuration(getResources().getInteger(
                        android.R.integer.config_shortAnimTime))
                .setListener(new Animator.AnimatorListener() {
                    @Override
                    public void onAnimationStart(Animator animation) { }

                    @Override
                    public void onAnimationEnd(Animator animation) {
                        mapPopupRootView.setVisibility(View.GONE);
                    }

                    @Override
                    public void onAnimationCancel(Animator animation) { }

                    @Override
                    public void onAnimationRepeat(Animator animation) { }
                });
    }

    private boolean isMapPopupOpen() {
        return mapPopupRootView.getVisibility() == View.VISIBLE;
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
        adapter = new FoodtruckViewerAdapter(this);
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState); // called for logging purposes
        View view = inflater.inflate(R.layout.fragment_foodtruck_viewer, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putBoolean(ARG_MAP_POPUP_VISIBLE, mapPopupRootView.getVisibility() == View.VISIBLE);
        outState.putBoolean(ARG_INSTANT_ZOOM_ON_MAP, instantZoomOnMap);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_foodtruck_viewer, menu);
        // Handle menu item visibility
        if (adapter.getFoodtruckOwnerId() != null) {
            menu.findItem(R.id.menu_remove_foodtruck)
                    .setVisible(adapter.getFoodtruckOwnerId()
                            .equals(activityContract.getAuthenticatedUserId()));
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.menu_remove_foodtruck) {
            dialogManager.showAlertDialog(
                    R.string.foodtruck_remove_dialog_title,
                    R.string.foodtruck_remove_dialog_message,
                    R.string.alert_dialog_action_remove,
                    R.string.alert_dialog_action_cancel,
                    ((dialog, which) -> presenter.removeFoodtruck(foodtruckId)));
            return true;
        } else if (item.getItemId() == R.id.menu_show_on_map) {
            openMapPopup();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void initViews() {
        collapsingToolbarLayout.setTitle(foodtruckName);
        activityContract.setToolbar(toolbar);
        swipeRefreshLayout.setColorSchemeResources(R.color.colorAccent);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.addItemDecoration(new ListItemDecoration(
                getResources().getDimensionPixelSize(R.dimen.general_layout_margin)
        ));
        recyclerView.setAdapter(adapter);
        mapViewManager.takeMapView(mapView);
    }

    @Override
    protected void disposeViews() {
        recyclerView.setAdapter(null);
        mapViewManager.dropMapView(mapView);
    }

    @Override
    protected void registerListeners() {
        swipeRefreshLayout.setOnRefreshListener(() -> presenter.loadViewModel(foodtruckId));
        appBarLayout.setOnStateChangedListener(appBarOnStateChangeListener);
    }

    @Override
    protected void unregisterListeners() {
        swipeRefreshLayout.setOnRefreshListener(null);
        appBarLayout.setOnStateChangedListener(null);
    }

    @Override
    protected void loadData() {
        if (!presenter.restoreDataFromCache()) {
            presenter.loadViewModel(foodtruckId);
        }
    }

    @Override
    protected void restoreInstanceState(Bundle savedInstanceState) {
        boolean popupMapVisible = savedInstanceState.getBoolean(ARG_MAP_POPUP_VISIBLE, false);
        mapPopupRootView.setVisibility(popupMapVisible ? View.VISIBLE : View.GONE);
        instantZoomOnMap = savedInstanceState.getBoolean(ARG_INSTANT_ZOOM_ON_MAP, false);
    }

    @Override
    @SuppressWarnings("unchecked")
    protected <T extends BaseMVP.View> BaseMVP.Presenter<T> getPresenter() {
        return (BaseMVP.Presenter<T>) presenter;
    }

    @Override
    public String getContentId() {
        return getArguments() != null ? getArguments().getString(ARG_FOODTRUCK_ID) : foodtruckId;
    }

    @Override
    public boolean onBackPressed() {
        if (isMapPopupOpen()) {
            closeMapPopup();
            return true;
        }
        return super.onBackPressed();
    }

    @Nullable
    @Override
    protected SwipeRefreshLayout getSwipeRefreshLayout() {
        return swipeRefreshLayout;
    }

    @Nullable
    @Override
    protected ActivityContract getActivityContract() {
        return activityContract;
    }

    @Override
    public void updateFoodtruck(Foodtruck foodtruck) {
        foodtruckName = foodtruck.getName();
        getArguments().putString(ARG_FOODTRUCK_NAME, foodtruckName);
        collapsingToolbarLayout.setTitle(foodtruckName);
        if (foodtruck.getImageUrl() != null) {
            Glide.with(topImageView)
                    .load(foodtruck.getImageUrl())
                    .centerCrop()
                    .signature(new ObjectKey(foodtruck.getImageSignature()))
                    .into(topImageView);
        }
        boolean isOwnerAuthenticated = foodtruck.getOwner().getId()
                .equals(activityContract.getAuthenticatedUserId());
        MenuItem removeFoodtruckMenuItem = toolbar.getMenu().findItem(R.id.menu_remove_foodtruck);
        if (removeFoodtruckMenuItem != null) {
            removeFoodtruckMenuItem.setVisible(isOwnerAuthenticated);
        }
        if (isOwnerAuthenticated) {
            fab.show();
            fab.setOnClickListener(v -> activityContract.showFoodtruckEditorScreen(foodtruck));
        } else {
            fab.hide();
            fab.setOnClickListener(null);
        }
        adapter.setFoodtruck(foodtruck);
    }

    @Override
    public void updateMyReview(Review myReview) {
        adapter.setMyReview(myReview);
    }

    @Override
    public void updateReviews(List<Review> reviews) {
        adapter.setReviews(reviews);
    }

    @Override
    public boolean isUserAuthenticated() {
        return activityContract.isUserAuthenticated();
    }

    @Nullable
    @Override
    public String getAuthenticatedUserId() {
        return activityContract.getAuthenticatedUserId();
    }

    @Override
    public void submitReview(String title, String content, float rating) {
        presenter.submitReview(foodtruckId, title, content, rating);
    }

    @Override
    public void updateReview(String reviewId, String title, String content, float rating) {
        presenter.updateReview(reviewId, title, content, rating);
    }

    @Override
    public void removeReview(String reviewId) {
        presenter.removeReview(reviewId);
    }

    @Override
    public void onAccountSelected(Account account) {
        activityContract.showProfileScreen(account.getId(), account.getUsername());
    }

    @Nullable
    @Override
    public Review getMyReview() {
        return presenter.getMyReview();
    }

    @Override
    public void setMyReviewViewModel(int myReviewState, String title, String content, float rating) {
        presenter.setMyReviewViewModel(new FoodtruckViewerMyReviewViewModel(
                myReviewState, title, content, rating
        ));
    }

    @Override
    public FoodtruckViewerMyReviewViewModel getMyReviewViewModel() {
        return presenter.getMyReviewViewModel();
    }
}
