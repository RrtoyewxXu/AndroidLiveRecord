package com.rrtoyewx.camerademo;

import android.content.Context;
import android.hardware.Camera;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import static android.view.SurfaceHolder.Callback;
import static android.view.SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS;

/**
 * Created by Rrtoyewx on 16/9/1.
 * CameraPreviewLayout
 */
public class CameraPreviewLayout extends SurfaceView {
    private SurfaceHolder mSurfaceHolder;
    private Camera mCamera;
    private Context mContext;



    private Camera.PictureCallback mPictureCallback = new Camera.PictureCallback() {
        @Override
        public void onPictureTaken(byte[] data, Camera camera) {
            savePhoto(data);
        }
    };

    private Callback mSurfaceCallBack = new Callback() {
        @Override
        public void surfaceCreated(SurfaceHolder holder) {
            try {
                mCamera = CameraUtil.getCameraInstance();
                Log.e(Constants.TAG, mCamera.getParameters().getSupportedPictureFormats().toString());

                mCamera.setDisplayOrientation(90);
                mCamera.setPreviewDisplay(holder);
                mCamera.startPreview();

            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        @Override
        public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
            if (holder.getSurface() == null) {
                return;
            }

            mCamera.stopPreview();

            try {
                mCamera.setPreviewDisplay(holder);
                mCamera.startPreview();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        @Override
        public void surfaceDestroyed(SurfaceHolder holder) {
            if (mCamera != null) {
                mCamera.stopPreview();
                mCamera.release();
                mCamera = null;
            }

            Log.e(Constants.TAG, "surfaceDestroyed");
        }
    };


    public CameraPreviewLayout(Context context) {
        super(context);
        mContext = context;
        init();
    }

    private void init() {
        mSurfaceHolder = getHolder();
        mSurfaceHolder.addCallback(mSurfaceCallBack);
        mSurfaceHolder.setType(SURFACE_TYPE_PUSH_BUFFERS);
    }

    private void savePhoto(byte[] data) {
        File localPhoto = new File(mContext.getExternalCacheDir(), System.currentTimeMillis() + ".jpg");

        BufferedOutputStream bos = null;
        try {
            bos = new BufferedOutputStream(new FileOutputStream(localPhoto));

            bos.write(data);
            bos.flush();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (bos != null) {
                try {
                    bos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public Camera getCamera() {
        return mCamera;
    }

    public void takePicture() {
        mCamera.takePicture(null, null, null, mPictureCallback);
    }


}
