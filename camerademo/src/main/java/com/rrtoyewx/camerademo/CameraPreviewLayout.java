package com.rrtoyewx.camerademo;

import android.content.Context;
import android.graphics.Rect;
import android.hardware.Camera;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

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

    private Camera.FaceDetectionListener faceDetectionListener = new Camera.FaceDetectionListener() {
        @Override
        public void onFaceDetection(Camera.Face[] faces, Camera camera) {
            for (int i = 0; i < faces.length; i++) {
                Camera.Face face = faces[i];
                Log.e(Constants.TAG, "id" + face.id + "score" + face.score + "rect" + face.rect
                        + "leftEye" + face.leftEye + "mouth" + face.mouth + "rightEye" + face.rightEye);
            }
        }
    };

    private Callback mSurfaceCallBack = new Callback() {
        @Override
        public void surfaceCreated(SurfaceHolder holder) {
            try {
                mCamera = CameraUtil.getCameraInstance();
                Log.e(Constants.TAG, "getSupportedPictureFormats" + mCamera.getParameters().getSupportedPictureFormats().toString());
                Log.e(Constants.TAG, "getSupportedFocusModes:" + mCamera.getParameters().getSupportedFocusModes().toString());
                Log.e(Constants.TAG, "getSupportedFlashModes:" + mCamera.getParameters().getSupportedFlashModes().toString());
                Camera.Parameters parameters = mCamera.getParameters();
                parameters.setFocusMode(Camera.Parameters.FOCUS_MODE_AUTO);
                parameters.setFlashMode(Camera.Parameters.FLASH_MODE_ON);

                mCamera.setParameters(parameters);
                mCamera.setFaceDetectionListener(faceDetectionListener);
                mCamera.setPreviewCallback(new Camera.PreviewCallback() {
                    @Override
                    public void onPreviewFrame(byte[] data, Camera camera) {
                        Log.e(Constants.TAG,data.toString()+"setPreviewCallback");
                    }
                });
                mCamera.startFaceDetection();

                //  mCamera.setDisplayOrientation(90);
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
                mCamera.startFaceDetection();
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
        mCamera.autoFocus(new Camera.AutoFocusCallback() {
            @Override
            public void onAutoFocus(boolean success, Camera camera) {
                Log.e(Constants.TAG, "success:" + success);
                mCamera.takePicture(null, null, null, mPictureCallback);
            }
        });
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                Rect rect = calculateFocusArea((int) event.getX(), (int) event.getY());
                Camera.Parameters parameters = mCamera.getParameters();

                if (parameters.getMaxNumMeteringAreas() > 0) {
                    List<Camera.Area> meteringAreas = new ArrayList<Camera.Area>();
                    meteringAreas.add(new Camera.Area(rect, 600));

                    parameters.setMeteringAreas(meteringAreas);
                }

                if (parameters.getMaxNumFocusAreas() > 0) {
                    List<Camera.Area> focusAreas = new ArrayList<Camera.Area>();
                    focusAreas.add(new Camera.Area(rect, 600));

                    parameters.setFocusAreas(focusAreas);
                }

                mCamera.autoFocus(new Camera.AutoFocusCallback() {
                    @Override
                    public void onAutoFocus(boolean success, Camera camera) {
                        Log.e(Constants.TAG, "onTouchEvent success:" + success);
                    }
                });

                return true;

        }

        return super.onTouchEvent(event);
    }

    private Rect calculateFocusArea(int x, int y) {
        return new Rect(x - 50, y - 50, x + 50, y + 50);
    }
}
