package com.example.foodtruckclient.util;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.provider.MediaStore;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.FileProvider;

import com.example.foodtruckclient.network.NetworkConstants;

import java.io.File;
import java.io.IOException;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import timber.log.Timber;

public class ImageUtils {

    public static final String TEMP_PHOTO_DIR = "tmp_photo";
    public static final String TEMP_PHOTO_FILE_NAME = "tmp_photo.jpg";
    public static final String FILE_PROVIDER_AUTHORITY = "com.example.foodtruckclient.fileprovider";

    public static MultipartBody.Part createPartFromImageFile(@NonNull File imageFile) {
        RequestBody requestBody = RequestBody
                .create(MediaType.parse(NetworkConstants.MIME_TYPE_IMAGE), imageFile);
        return MultipartBody.Part.createFormData(
                NetworkConstants.MULTIPART_IMAGE_FIELD, imageFile.getName(), requestBody
        );
    }

    public static @Nullable File getFileFromGalleryIntent(Context context, Intent data) {
        File file = null;
        String path;
        // data.getData return the content URI for the selected Image
        Uri selectedImage = data.getData();
        String[] filePathColumn = { MediaStore.Images.Media.DATA };
        // Get the cursor
        try (Cursor cursor = context.getContentResolver()
                .query(selectedImage, filePathColumn, null, null, null)) {
            // Move to first row
            cursor.moveToFirst();
            // Get the column index of MediaStore.Images.Media.DATA
            int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
            // Gets the String value in the column
            path = cursor.getString(columnIndex);
            if (path != null) {
                file = new File(path);
            }
        }
        return file;
    }

    public static @Nullable Uri getTempUriForCamera(Context context) {
        Uri uri = null;
        try {
            File photoDir = new File(context.getCacheDir(), TEMP_PHOTO_DIR);
            if (!photoDir.exists()) {
                photoDir.mkdirs();
            }
            File photoFile = new File(photoDir, TEMP_PHOTO_FILE_NAME);
            if (!photoFile.exists()) {
                photoFile.createNewFile();
            }
            photoFile.deleteOnExit();
            uri = FileProvider.getUriForFile(
                    context,
                    FILE_PROVIDER_AUTHORITY,
                    photoFile
            );
        } catch (IOException ioe) {
            Timber.e(ioe);
        }
        return uri;
    }

    public static File getTempCameraFile(Context context) {
        File photoDir = new File(context.getCacheDir(), TEMP_PHOTO_DIR);
        return new File(photoDir, TEMP_PHOTO_FILE_NAME);
    }
}
