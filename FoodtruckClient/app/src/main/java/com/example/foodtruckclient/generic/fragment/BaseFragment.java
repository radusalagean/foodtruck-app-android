package com.example.foodtruckclient.generic.fragment;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.UiThread;
import androidx.fragment.app.Fragment;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.example.foodtruckclient.R;
import com.example.foodtruckclient.application.FoodtruckApplication;
import com.example.foodtruckclient.di.activity.ActivityComponent;
import com.example.foodtruckclient.di.activity.ActivityModule;
import com.example.foodtruckclient.dialog.DialogManager;
import com.example.foodtruckclient.generic.activity.ActivityContract;
import com.example.foodtruckclient.generic.mvp.BaseMVP;
import com.example.foodtruckclient.permission.PermissionConstants;
import com.example.foodtruckclient.permission.PermissionRequestDelegate;
import com.example.foodtruckclient.util.ImageUtils;

import java.util.UUID;

import timber.log.Timber;

public abstract class BaseFragment extends Fragment
        implements BaseMVP.View, PermissionRequestDelegate {

    public static final int REQ_CODE_TAKE_PHOTO = 1;
    public static final int REQ_CODE_PICK_FROM_GALLERY = 2;

    protected static final String ARG_UUID = "UUID";

    private String tag = getClass().getSimpleName();
    private boolean isComponentUsed = false;
    private UUID uuid;

    @Override
    public void onAttach(Context context) {
        Timber.tag(tag).v("-F-> onAttach(%s)", context);
        super.onAttach(context);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        Timber.tag(tag).v("-F-> onCreate(%s)", savedInstanceState);
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            uuid = (UUID) getArguments().getSerializable(ARG_UUID);
            getPresenter().setUuid(uuid);
            Timber.d("Fragment's UUID: %s", uuid);
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        Timber.tag(tag).v("-F-> onCreateView(%s, %s, %s)", inflater, container,
                savedInstanceState);
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        Timber.tag(tag).v("-F-> onViewCreated(%s, %s)", view, savedInstanceState);
        super.onViewCreated(view, savedInstanceState);
        initViews();
        registerListeners();
        getPresenter().takeView(this);
        loadData();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        Timber.tag(tag).v("-F-> onActivityCreated(%s)", savedInstanceState);
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public void onStart() {
        Timber.tag(tag).v("-F-> onStart()");
        super.onStart();
    }

    @Override
    public void onResume() {
        Timber.tag(tag).v("-F-> onResume()");
        super.onResume();
        if (getSwipeRefreshLayout() != null) {
            getSwipeRefreshLayout().setRefreshing(getPresenter().isRefreshing());
        }
    }

    @Override
    public void onPause() {
        Timber.tag(tag).v("-F-> onPause()");
        super.onPause();
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        Timber.tag(tag).v("-F-> onSaveInstanceState(%s)", outState);
        super.onSaveInstanceState(outState);
    }

    @Override
    public void onStop() {
        Timber.tag(tag).v("-F-> onStop()");
        getPresenter().clearCompositeDisposable();
        super.onStop();
    }

    @Override
    public void onDestroyView() {
        Timber.tag(tag).v("-F-> onDestroyView()");
        getPresenter().dropView();
        unregisterListeners();
        disposeViews();
        super.onDestroyView();
    }

    @Override
    public void onDestroy() {
        Timber.tag(tag).v("-F-> onDestroy()");
        super.onDestroy();
    }

    @Override
    public void onDetach() {
        Timber.tag(tag).v("-F-> onDetach()");
        super.onDetach();
    }

    @Override
    public void requestPermission(String permission, int requestCode) {
        requestPermissions(new String[] { permission }, requestCode);
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        Timber.d("onRequestPermissionsResult(%d, %s, %s)", requestCode, permissions, grantResults);
        getPresenter().onRequestPermissionsResult(requestCode, permissions, grantResults);
    }


    @Override
    public void setRefreshingIndicator(boolean refreshing) {
        if (getSwipeRefreshLayout() != null) {
            getSwipeRefreshLayout().setRefreshing(refreshing);
        }
    }

    @Override
    public void toast(String message) {
        Toast.makeText(getContext(), message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showSnackBar(int stringResID) {
        if (getActivityContract() != null) {
            getActivityContract().showSnackBar(getString(stringResID));
        }
    }

    @Override
    public void showSnackBar(int stringResId, Object... formatArgs) {
        if (getActivityContract() != null) {
            getActivityContract().showSnackBar(getResources().getString(stringResId, formatArgs));
        }
    }

    @Override
    public PermissionRequestDelegate getPermissionRequestDelegate() {
        return this;
    }

    @Override
    public void popFragment() {
        getFragmentManager().popBackStack();
    }

    @UiThread
    protected ActivityComponent getControllerComponent() {
        if (isComponentUsed) {
            throw new IllegalStateException("You shouldn't use ActivityComponent more than once");
        }
        isComponentUsed = true;
        return ((FoodtruckApplication) getActivity().getApplication())
                .getApplicationComponent()
                .newActivityComponent(new ActivityModule(getActivity()));
    }

    @Nullable
    protected SwipeRefreshLayout getSwipeRefreshLayout() {
        return null;
    }

    @Nullable
    protected ActivityContract getActivityContract() {
        return null;
    }

    public UUID getUuid() {
        return uuid;
    }

    /**
     * Should be called only once, during instance creation.
     * Used for uniquely identifying the fragment's view model
     */
    public void generateUniqueId(@NonNull Bundle bundle) {
        if (uuid != null) {
            throw new UnsupportedOperationException("You shouldn't generate a new unique id for this fragment if one already exists!");
        }
        UUID uuid = UUID.randomUUID();
        bundle.putSerializable(ARG_UUID, uuid);
        this.uuid = uuid;
    }

    protected void openSelectImageDialog(DialogManager dialogManager) {
        String takePhoto = getString(R.string.profile_dialog_item_take_photo);
        String pickFromGallery = getString(R.string.profile_dialog_item_pick_from_gallery);
        String[] items = new String[] { takePhoto, pickFromGallery };
        dialogManager.showListAlertDialog(
                R.string.alert_dialog_upload_picture_title,
                items,
                (dialog, which) -> {
                    if (items[which].equals(takePhoto)) {
                        getPresenter().doOnPermissionGranted(
                                PermissionConstants.PERMISSION_CAMERA,
                                R.string.alert_dialog_camera_no_permission_message,
                                this, this::openCamera
                        );
                    } else if (items[which].equals(pickFromGallery)) {
                        getPresenter().doOnPermissionGranted(
                                PermissionConstants.PERMISSION_READ_EXTERNAL_STORAGE,
                                R.string.alert_dialog_read_storage_no_permission_message,
                                this, this::openImagePicker
                        );
                    }
                });
    }

    protected void openImagePicker() {
        Intent intent = new Intent(Intent.ACTION_PICK,
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(intent, REQ_CODE_PICK_FROM_GALLERY);
    }

    protected void openCamera() {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (intent.resolveActivity(getContext().getPackageManager()) != null) {
            Uri uri = ImageUtils.getTempUriForCamera(getContext());
            if (uri != null) {
                intent.putExtra(MediaStore.EXTRA_OUTPUT, uri);
                startActivityForResult(intent, REQ_CODE_TAKE_PHOTO);
            }
        }
    }

    /**
     * Called during the {@link Fragment#onViewCreated(View, Bundle)} lifecycle method,
     * override to initialize views
     */
    protected abstract void initViews();

    /**
     * Called during the {@link Fragment#onDestroyView()} lifecycle method,
     * override to dispose resources associated to views
     */
    protected abstract void disposeViews();

    /**
     * Called during the {@link Fragment#onViewCreated(View, Bundle)} lifecycle method,
     * override to register view listeners
     */
    protected abstract void registerListeners();

    /**
     * Called during the {@link Fragment#onDestroyView()} lifecycle method,
     * override to unregister view listeners
     */
    protected abstract void unregisterListeners();

    /**
     * Called during the {@link Fragment#onViewCreated(View, Bundle)} lifecycle method,
     * override to load data from the view model cache or from the network
     */
    protected abstract void loadData();

    /**
     * Override to return the presenter
     */
    protected abstract <T extends BaseMVP.View> BaseMVP.Presenter<T> getPresenter();
}
