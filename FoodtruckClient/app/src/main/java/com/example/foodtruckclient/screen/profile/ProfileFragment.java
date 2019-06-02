package com.example.foodtruckclient.screen.profile;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.ViewCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.example.foodtruckclient.R;
import com.example.foodtruckclient.dialog.DialogManager;
import com.example.foodtruckclient.generic.activity.ActivityContract;
import com.example.foodtruckclient.generic.decoration.ListItemDecoration;
import com.example.foodtruckclient.generic.fragment.BaseFragment;
import com.example.foodtruckclient.generic.list.foodtruck.FoodtruckContract;
import com.example.foodtruckclient.generic.mvp.BaseMVP;
import com.example.foodtruckclient.network.foodtruckapi.model.Account;
import com.example.foodtruckclient.network.foodtruckapi.model.Foodtruck;
import com.example.foodtruckclient.util.ImageUtils;

import java.io.File;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

import static android.app.Activity.RESULT_OK;

public class ProfileFragment extends BaseFragment
        implements ProfileMVP.View, FoodtruckContract {

    private static final String ARG_PROFILE_ID = "profile_id";
    private static final String ARG_PROFILE_NAME = "profile_name";

    private String profileId;
    private String profileName;

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @BindView(R.id.profile_swipe_refresh_layout)
    SwipeRefreshLayout swipeRefreshLayout;

    @BindView(R.id.profile_recycler_view)
    RecyclerView recyclerView;

    @Inject
    ProfileMVP.Presenter presenter;

    @Inject
    ActivityContract activityContract;

    @Inject
    DialogManager dialogManager;

    private ProfileAdapter adapter;

    public ProfileFragment() {}

    public static ProfileFragment newInstance(String profileId, String profileName) {
        ProfileFragment fragment = new ProfileFragment();
        Bundle args = new Bundle();
        fragment.generateUniqueId(args);
        args.putString(ARG_PROFILE_ID, profileId);
        args.putString(ARG_PROFILE_NAME, profileName);
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
            profileId = getArguments().getString(ARG_PROFILE_ID);
            profileName = getArguments().getString(ARG_PROFILE_NAME);
        }
        adapter = new ProfileAdapter(this);
        setHasOptionsMenu(true);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState); // called for logging purposes
        View view = inflater.inflate(R.layout.fragment_profile, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_profile, menu);
        // Handle menu item visibility
        menu.findItem(R.id.menu_upload_profile_picture)
                .setVisible(profileId.equals(activityContract.getAuthenticatedUserId()));
        menu.findItem(R.id.menu_remove_profile_picture)
                .setVisible(profileId.equals(activityContract.getAuthenticatedUserId()) &&
                        adapter.hasProfilePicture());
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_upload_profile_picture:
                openSelectImageDialog(dialogManager);
                return true;
            case R.id.menu_remove_profile_picture:
                dialogManager.showAlertDialog(
                        R.string.profile_dialog_remove_profile_picture_title,
                        R.string.profile_dialog_remove_profile_picture_message,
                        R.string.alert_dialog_action_remove,
                        R.string.alert_dialog_action_cancel,
                        ((dialog, which) -> presenter.removeProfilePicture())
                );
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        File file;
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case REQ_CODE_TAKE_PHOTO:
                    file = ImageUtils.getTempCameraFile(getContext());
                    presenter.uploadProfilePicture(file);
                    break;
                case REQ_CODE_PICK_FROM_GALLERY:
                    file = ImageUtils.getFileFromGalleryIntent(getContext(), data);
                    if (file != null) {
                        presenter.uploadProfilePicture(file);
                    }
                    break;
            }
        }
    }

    @Override
    protected void initViews() {
        toolbar.setTitle(profileName);
        activityContract.setToolbar(toolbar);
        swipeRefreshLayout.setColorSchemeResources(R.color.colorAccent);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.addItemDecoration(new ListItemDecoration(
                getResources().getDimensionPixelSize(R.dimen.general_layout_margin)
        ));
        recyclerView.setAdapter(adapter);
    }

    @Override
    protected void disposeViews() {
        recyclerView.setAdapter(null);
    }

    @Override
    protected void registerListeners() {
        swipeRefreshLayout.setOnRefreshListener(() -> presenter.loadViewModel(profileId));
    }

    @Override
    protected void unregisterListeners() {
        swipeRefreshLayout.setOnRefreshListener(null);
    }

    @Override
    protected void loadData() {
        if (!presenter.restoreDataFromCache()) {
            presenter.loadViewModel(profileId);
        }
    }

    @Override
    @SuppressWarnings("unchecked")
    protected <T extends BaseMVP.View> BaseMVP.Presenter<T> getPresenter() {
        return (BaseMVP.Presenter<T>) presenter;
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
    public void updateAccount(Account account) {
        MenuItem item = toolbar.getMenu().findItem(R.id.menu_remove_profile_picture);
        if (item != null) {
            item.setVisible(
                    profileId.equals(activityContract.getAuthenticatedUserId()) &&
                            account.getImage() != null
            );
        }
        adapter.setAccount(account);
    }

    @Override
    public void updateFoodtrucks(List<Foodtruck> foodtrucks) {
        adapter.setFoodtrucks(foodtrucks);
    }

    @Override
    public void onFoodtruckSelected(Foodtruck foodtruck) {
        activityContract.showFoodtruckViewerScreen(foodtruck.getId(), foodtruck.getName());
    }

    @Override
    public void onFoodtruckLocationButtonClicked(Foodtruck foodtruck) {
        // nothing to do here
    }

    @Override
    public void onOwnerSelected(Account owner) {
        // nothing to do here
    }
}
