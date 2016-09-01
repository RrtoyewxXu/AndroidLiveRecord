package com.rrtoyewx.camerademo;

import android.content.Context;
import android.content.pm.PackageManager;
import android.hardware.Camera;
import android.util.Log;

/**
 * Created by Rrtoyewx on 16/9/1.
 * CameraUtil
 */
public class CameraUtil {
    /**
     * 检查有没有Camera硬件
     *
     * @param context context
     * @return true have camera hardware ,otherwise
     */
    public static boolean hasCameraHardware(Context context) {
        return context.getPackageManager().hasSystemFeature(PackageManager.FEATURE_CAMERA);
    }

    /**
     * 返回Camera
     *
     * @return Creates a new Camera object to access the first back-facing camera on the device.
     * If the device does not have a back-facing camera, this returns null.
     */
    public static Camera getCameraInstance() {
        int numberOfCameras = Camera.getNumberOfCameras();
        Log.e(Constants.TAG, "numberOfCameras:" + numberOfCameras);
        return Camera.open();
    }

}

