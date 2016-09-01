package com.rrtoyewx.camerademo;

import android.content.Context;
import android.hardware.Camera;
import android.media.CamcorderProfile;
import android.media.MediaRecorder;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import java.io.IOException;

/**
 * Created by Rrtoyewx on 16/9/1.
 * RecordVideoLayout
 */
public class RecordVideoLayout extends SurfaceView {
    private SurfaceHolder surfaceHolder;
    private MediaRecorder mediaRecorder;
    private Context context;
    private Camera camera;
    private SurfaceHolder.Callback callback = new SurfaceHolder.Callback() {
        @Override
        public void surfaceCreated(SurfaceHolder holder) {
            camera = CameraUtil.getCameraInstance();
            camera.setDisplayOrientation(90);
        }

        @Override
        public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

        }

        @Override
        public void surfaceDestroyed(SurfaceHolder holder) {
            releaseCamera();
            releaseMediaRecorder();
        }
    };

    public RecordVideoLayout(Context context) {
        super(context);
        this.context = context;
        init();
    }

    private void init() {
        surfaceHolder = getHolder();
        surfaceHolder.addCallback(callback);
    }

    public void prepareVideoRecord() {
        mediaRecorder = new MediaRecorder();
        camera.unlock();
        mediaRecorder.setCamera(camera);
        mediaRecorder.setAudioSource(MediaRecorder.AudioSource.CAMCORDER);
        mediaRecorder.setVideoSource(MediaRecorder.VideoSource.CAMERA);

        mediaRecorder.setProfile(CamcorderProfile.get(CamcorderProfile.QUALITY_HIGH));
        mediaRecorder.setOutputFile(context.getExternalCacheDir() + "/record_video");
        mediaRecorder.setPreviewDisplay(surfaceHolder.getSurface());

        try {
            mediaRecorder.prepare();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void stopVideoRecord(){
        mediaRecorder.stop();
        releaseMediaRecorder();
        camera.lock();
    }

    public void startVideoRecord(){
        prepareVideoRecord();
        mediaRecorder.start();
    }

    public void releaseCamera() {
        if (camera != null) {
            try {
                camera.setPreviewDisplay(null);
                camera.release();
                camera = null;
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void releaseMediaRecorder() {
        if (mediaRecorder != null) {
            mediaRecorder.reset();
            mediaRecorder.release();
            mediaRecorder = null;
        }
    }
}

