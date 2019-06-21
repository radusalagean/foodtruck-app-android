package com.busytrack.foodtruckclient.screen.foodtruckeditor;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import androidx.viewpager.widget.ViewPager;

import com.bumptech.glide.Glide;
import com.bumptech.glide.signature.ObjectKey;
import com.busytrack.foodtruckclient.R;
import com.busytrack.foodtruckclient.dialog.DialogManager;
import com.busytrack.foodtruckclient.generic.activity.ActivityContract;
import com.busytrack.foodtruckclient.generic.mapmvp.BaseMapFragment;
import com.busytrack.foodtruckclient.generic.mvp.BaseMVP;
import com.busytrack.foodtruckclient.generic.view.OnViewInflatedListener;
import com.busytrack.foodtruckclient.network.NetworkConstants;
import com.busytrack.foodtruckclient.network.foodtruckapi.model.Coordinates;
import com.busytrack.foodtruckclient.network.foodtruckapi.model.Foodtruck;
import com.busytrack.foodtruckclient.util.ImageUtils;
import com.busytrack.foodtruckclient.util.IntentUtils;
import com.busytrack.foodtruckclient.util.ViewUtils;
import com.busytrack.foodtruckclient.view.TagLayout;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.common.base.Strings;
import com.radusalagean.quickdotsview.QuickDotsView;

import java.io.File;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

import static android.app.Activity.RESULT_OK;

public class FoodtruckEditorFragment extends BaseMapFragment
        implements FoodtruckEditorMVP.View {

    private static final String ARG_FOODTRUCK = "foodtruck";

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @BindView(R.id.foodtruck_editor_swipe_refresh_layout)
    SwipeRefreshLayout swipeRefreshLayout;

    @BindView(R.id.foodtruck_editor_view_pager)
    ViewPager viewPager;

    @BindView(R.id.fab)
    FloatingActionButton fab;

    @BindView(R.id.foodtruck_editor_navigate_previous)
    ImageView previousButton;

    @BindView(R.id.foodtruck_editor_navigate_next)
    ImageView nextButton;

    @BindView(R.id.foodtruck_editor_quick_dots_view)
    QuickDotsView quickDotsView;

    @Inject
    FoodtruckEditorMVP.Presenter presenter;

    @Inject
    ActivityContract activityContract;

    @Inject
    DialogManager dialogManager;

    private FoodtruckEditorPagerAdapter pagerAdapter;

    // NAME
    private EditText nameEditText;

    // FOODTYPES
    private EditText foodtypeEditText;
    private ImageView addFoodtypeImageView;
    private TagLayout tagLayout;

    // LOCATION
    // nothing here

    // IMAGE
    private Button selectImageButton;
    private Button removeImageButton;
    private ImageView previewImageView;

    private ViewPager.OnPageChangeListener onPageChangeListener = new ViewPager.OnPageChangeListener() {
        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {}

        @Override
        public void onPageSelected(int position) {
            handleNavigationButtonsVisibility(position);
            if (toolbar.getMenu().findItem(R.id.menu_show_my_location) != null) {
                toolbar.getMenu().findItem(R.id.menu_show_my_location)
                        .setVisible(position == FoodtruckEditorPagerMapper.POSITION_LOCATION);
            }
        }

        @Override
        public void onPageScrollStateChanged(int state) {}
    };

    private TextWatcher nameEditTextTextWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            presenter.setTypedName(s.toString().trim());
            handleSaveButtonVisibility();
        }

        @Override
        public void afterTextChanged(Editable s) {}
    };

    private TagLayout.Listener tagLayoutListener = () -> {
        foodtypeEditText.setEnabled(tagLayout.getCount() < TagLayout.MAX_TAG_COUNT);
        presenter.setAddedFoodtypes(tagLayout.getTags());
        handleSaveButtonVisibility();
    };

    private OnViewInflatedListener onViewInflatedListener = new OnViewInflatedListener() {
        @Override
        public void onViewInflated(View view) {
            switch (view.getId()) {
                case R.id.layout_foodtruck_editor_name:
                    nameEditText = view.findViewById(R.id.foodtruck_editor_edit_text_name);
                    nameEditText.addTextChangedListener(nameEditTextTextWatcher);
                    ViewUtils.setText(nameEditText, presenter.getTypedName());
                    break;
                case R.id.layout_foodtruck_editor_foodtypes:
                    foodtypeEditText = view.findViewById(R.id.foodtruck_editor_edit_text_foodtype);
                    addFoodtypeImageView = view.findViewById(R.id.foodtruck_editor_image_view_add);
                    tagLayout = view.findViewById(R.id.foodtruck_editor_tag_layout);
                    tagLayout.setListener(tagLayoutListener);
                    addFoodtypeImageView.setOnClickListener(v -> {
                        String foodtype = foodtypeEditText.getText().toString().trim();
                        if (!Strings.isNullOrEmpty(foodtype)) {
                            tagLayout.addTag(foodtype);
                            foodtypeEditText.setText(null);
                        }
                    });
                    ViewUtils.setText(foodtypeEditText, presenter.getTypedFoodtype());
                    tagLayout.setTags(presenter.getAddedFoodtypes());
                    break;
                case R.id.layout_foodtruck_editor_location:
                    mapView = view.findViewById(R.id.foodtruck_editor_map_view);
                    FoodtruckEditorFragment.this.mapViewManager.takeMapView(mapView);
                    presenter.setOnMapClickListener(latLng -> {
                        presenter.addManualMarker(
                                new Coordinates(latLng.latitude, latLng.longitude)
                        );
                        presenter.setCoordinates(presenter.getManualMarkerCoordinates());
                        handleSaveButtonVisibility();
                    });
                    Coordinates coordinates = presenter.getCoordinates();
                    if (coordinates != null) {
                        presenter.addManualMarker(coordinates);
                        presenter.zoomOnLocation(coordinates.getLatitude(),
                                coordinates.getLongitude(), true);
                    }
                    break;
                case R.id.layout_foodtruck_editor_image:
                    selectImageButton = view.findViewById(R.id.foodtruck_editor_button_select_image);
                    removeImageButton = view.findViewById(R.id.foodtruck_editor_button_remove_image);
                    previewImageView = view.findViewById(R.id.foodtruck_editor_image_view_preview);
                    selectImageButton.setOnClickListener(v -> openSelectImageDialog(dialogManager));
                    if (presenter.getImageFile() != null) {
                        onImageFileReceived(presenter.getImageFile(), true);
                    } else if (presenter.getImageBundle() != null) {
                        onImageAdded(presenter.getImageBundle().getImageUrl(),
                                presenter.getImageBundle().getSignature());
                    }
                    removeImageButton.setOnClickListener(v -> onImageRemoved());
                    break;
            }
            handleSaveButtonVisibility();
        }

        @Override
        public void onViewDestroyed(View view) {
            switch (view.getId()) {
                case R.id.layout_foodtruck_editor_name:
                    presenter.setTypedName(nameEditText.getText().toString().trim());
                    nameEditText = null;
                    break;
                case R.id.layout_foodtruck_editor_foodtypes:
                    tagLayout.setOnClickListener(null);
                    addFoodtypeImageView.setOnClickListener(null);
                    presenter.setTypedFoodtype(foodtypeEditText.getText().toString());
                    presenter.setAddedFoodtypes(tagLayout.getTags());
                    tagLayout = null;
                    addFoodtypeImageView = null;
                    foodtypeEditText = null;
                    break;
                case R.id.layout_foodtruck_editor_location:
                    presenter.setCoordinates(presenter.getManualMarkerCoordinates());
                    presenter.setOnMapClickListener(null);
                    FoodtruckEditorFragment.this.mapViewManager.dropMapView(mapView);
                    break;
                case R.id.layout_foodtruck_editor_image:
                    Glide.with(previewImageView).clear(previewImageView);
                    break;
            }
        }
    };

    private View.OnClickListener fabOnClickListener = v ->
        presenter.onSaveButtonClicked();

    public FoodtruckEditorFragment() {}

    public static FoodtruckEditorFragment newInstance() {
        return newInstance(null);
    }

    public static FoodtruckEditorFragment newInstance(@Nullable Foodtruck foodtruck) {
        FoodtruckEditorFragment fragment = new FoodtruckEditorFragment();
        Bundle args = new Bundle();
        fragment.generateUniqueId(args);
        if (foodtruck != null) {
            args.putParcelable(ARG_FOODTRUCK, foodtruck);
        }
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onAttach(Context context) {
        getControllerComponent().inject(this);
        super.onAttach(context);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            Foodtruck foodtruck = getArguments().getParcelable(ARG_FOODTRUCK);
            presenter.setFoodtruck(foodtruck);
        }
        pagerAdapter = new FoodtruckEditorPagerAdapter(getContext(), onViewInflatedListener);
        setHasOptionsMenu(true);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState); // called for logging purposes
        View view = inflater.inflate(R.layout.fragment_foodtruck_editor, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        File file;
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case IntentUtils.REQ_CODE_TAKE_PHOTO:
                    file = ImageUtils.getTempCameraFile(getContext());
                    onImageFileReceived(file, false);
                    break;
                case IntentUtils.REQ_CODE_PICK_FROM_GALLERY:
                    file = ImageUtils.getFileFromGalleryIntent(getContext(), data);
                    onImageFileReceived(file, false);
                    break;
            }
        }
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        inflater.inflate(R.menu.menu_foodtruck_editor, menu);
        menu.findItem(R.id.menu_show_my_location)
                .setVisible(viewPager.getCurrentItem() == FoodtruckEditorPagerMapper.POSITION_LOCATION);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.menu_show_my_location) {
            presenter.zoomOnCurrentDeviceLocation();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void initViews() {
        toolbar.setTitle(
                presenter.isInEditMode() ?
                        R.string.foodtruck_editor_toolbar_title_edit_foodtruck :
                        R.string.foodtruck_editor_toolbar_title_add_foodtruck
        );
        activityContract.setToolbar(toolbar);
        viewPager.setAdapter(pagerAdapter);
        viewPager.setOffscreenPageLimit(FoodtruckEditorPagerMapper.getCount());
        fab.setOnClickListener(fabOnClickListener);
        handleNavigationButtonsVisibility(viewPager.getCurrentItem());
        swipeRefreshLayout.setColorSchemeResources(R.color.colorAccent);
    }

    @Override
    protected void disposeViews() {
        viewPager.setAdapter(null);
    }

    @Override
    protected void registerListeners() {
        quickDotsView.linkViewPager(viewPager);
        viewPager.addOnPageChangeListener(onPageChangeListener);
        nextButton.setOnClickListener(v -> viewPager.setCurrentItem(viewPager.getCurrentItem() + 1));
        previousButton.setOnClickListener(v -> viewPager.setCurrentItem(viewPager.getCurrentItem() - 1));
    }

    @Override
    protected void unregisterListeners() {
        quickDotsView.unlinkViewPager(viewPager);
        viewPager.removeOnPageChangeListener(onPageChangeListener);
        fab.setOnClickListener(null);
        nextButton.setOnClickListener(null);
        previousButton.setOnClickListener(null);
    }

    @Override
    protected void loadData() {
        presenter.handleInvalidationEffects();
    }

    @Override
    @SuppressWarnings("unchecked")
    protected <T extends BaseMVP.View> BaseMVP.Presenter<T> getPresenter() {
        return (BaseMVP.Presenter<T>) presenter;
    }

    @Override
    public String getContentId() {
        return presenter.getFoodtruck() != null ?
                presenter.getFoodtruck().getId() : super.getContentId();
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
    public void onImageFileReceived(@Nullable File file, boolean fromCache) {
        if (file != null && file.exists()) {
            if (file.length() > NetworkConstants.MAX_FILE_SIZE) {
                showSnackBar(R.string.message_file_size_exceeded);
                return;
            }
            if (!fromCache) {
                presenter.setImageFile(file);
            }
            if (previewImageView != null) {
                onImageAdded(file);
            }
        }
    }

    @Override
    public void setRefreshingIndicator(boolean refreshing) {
        super.setRefreshingIndicator(refreshing);
        if (refreshing) {
            fab.hide();
        } else {
            handleSaveButtonVisibility();
        }
        nameEditText.setEnabled(!refreshing);
        foodtypeEditText.setEnabled(!refreshing);
    }

    private void onImageAdded(@Nullable String imageUrl, @NonNull String signature) {
        if (imageUrl != null) {
            Glide.with(previewImageView).clear(previewImageView);
            Glide.with(previewImageView)
                    .load(imageUrl)
                    .signature(new ObjectKey(signature))
                    .into(previewImageView);
            removeImageButton.setEnabled(true);
        }
    }

    private void onImageAdded(File imageFile) {
        Glide.with(previewImageView).clear(previewImageView);
        Bitmap bitmap = BitmapFactory.decodeFile(imageFile.getAbsolutePath());
        previewImageView.setImageBitmap(bitmap);
        removeImageButton.setEnabled(true);
    }

    private void onImageRemoved() {
        presenter.setImageFile(null);
        presenter.setImageBundle(null);
        Glide.with(previewImageView).clear(previewImageView);
        previewImageView.setImageBitmap(null);
        removeImageButton.setEnabled(false);
    }

    private void handleNavigationButtonsVisibility(int position) {
        previousButton.setVisibility(position == 0 ? View.INVISIBLE : View.VISIBLE);
        nextButton.setVisibility(position == pagerAdapter.getCount() - 1 ? View.GONE : View.VISIBLE);
    }

    public void handleSaveButtonVisibility() {
        if (nameEditText != null && nameEditText.length() > 0 &&
                tagLayout != null && tagLayout.getCount() > 0 &&
                presenter.getCoordinates() != null) {
            fab.show();
        } else {
            fab.hide();
        }
    }
}
