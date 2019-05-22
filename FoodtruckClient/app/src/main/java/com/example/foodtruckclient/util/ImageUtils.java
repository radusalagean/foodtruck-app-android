package com.example.foodtruckclient.util;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;

import androidx.annotation.Nullable;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;

import timber.log.Timber;

public class ImageUtils {

    public static final String KEY_INTENT_EXTRA_CAMERA_PHOTO_DATA = "data";
    public static final String TEMP_PHOTO_FILE_NAME = "tmp_camera_photo.jpg";

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

    public static @Nullable File getFileFromCameraIntent(Context context, Intent data) {
        File file = null;
        try {
            Bitmap bitmap = (Bitmap) data.getExtras().get(KEY_INTENT_EXTRA_CAMERA_PHOTO_DATA);
            // create a file to write bitmap data
            file = getTempCameraFile(context);
            file.createNewFile();
            // Convert bitmap to byte array
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.PNG, 0, bos);
            byte[] bitmapData = bos.toByteArray();
            // write the bytes to file
            FileOutputStream fos = new FileOutputStream(file);
            fos.write(bitmapData);
            fos.flush();
            fos.close();
        } catch (Exception e) {
            Timber.e(e);
        }
        return file;
    }

    public static void removeTmpFile(Context context) {
        try {
            File file = getTempCameraFile(context);
            if (file.exists()) {
                file.delete();
            }
        } catch (Exception e) {
            Timber.e(e);
        }
    }

    private static File getTempCameraFile(Context context) {
        return new File(context.getCacheDir(), TEMP_PHOTO_FILE_NAME);
    }
}
