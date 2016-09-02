package com.rrtoyewx.camerademo;

import android.content.Context;
import android.os.Environment;

/**
 * Created by Rrtoyewx on 16/9/2.
 * StorageHelper
 */
public class StorageHelper {
    private Context context;

    public StorageHelper(Context context) {
        this.context = context;
    }

    public String getVideoStoragePath() {
        return Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_MOVIES).getAbsolutePath();
    }

    public String getPictureStoreagePath() {
        return context.getExternalFilesDir(Environment.DIRECTORY_PICTURES).getAbsolutePath();
    }
}
