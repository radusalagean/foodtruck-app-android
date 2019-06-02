package com.example.foodtruckclient.util;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.provider.MediaStore;

import androidx.fragment.app.Fragment;

public class IntentUtils {

    public static final int REQ_CODE_TAKE_PHOTO = 1;
    public static final int REQ_CODE_PICK_FROM_GALLERY = 2;

    public static void openLinkInBrowser(Context context, String url) {
        Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
        context.startActivity(browserIntent);
    }

    public static void openImagePicker(Fragment fragment) {
        Intent intent = new Intent(Intent.ACTION_PICK,
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        fragment.startActivityForResult(intent, REQ_CODE_PICK_FROM_GALLERY);
    }

    public static void openCamera(Fragment fragment) {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (fragment.getContext() != null &&
                intent.resolveActivity(fragment.getContext().getPackageManager()) != null) {
            Uri uri = ImageUtils.getTempUriForCamera(fragment.getContext());
            if (uri != null) {
                intent.putExtra(MediaStore.EXTRA_OUTPUT, uri);
                fragment.startActivityForResult(intent, REQ_CODE_TAKE_PHOTO);
            }
        }
    }
}
