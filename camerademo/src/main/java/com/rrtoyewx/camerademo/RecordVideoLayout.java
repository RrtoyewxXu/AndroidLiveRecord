package com.rrtoyewx.camerademo;

import android.content.Context;
import android.hardware.Camera;
import android.media.CamcorderProfile;
import android.media.MediaRecorder;
import android.util.Log;
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
            camera.setOneShotPreviewCallback(new Camera.PreviewCallback() {
                @Override
                public void onPreviewFrame(byte[] data, Camera camera) {
                    Log.e(Constants.TAG,data.toString()+"setOneShotPreviewCallback");
                }
            });
        }

        @Override
        public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

        }

        @Override
        public void surfaceDestroyed(SurfaceHolder holder) {
            releaseMediaRecorder();
            releaseCamera();
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

    /**
     * 开始录制之前的准备工作
     */
    public void prepareVideoRecord() {
        mediaRecorder = new MediaRecorder();
        //camera.unlock allow MediaRecorder can use camera
        camera.unlock();
        //set camera
        mediaRecorder.setCamera(camera);
        //set source
        mediaRecorder.setAudioSource(MediaRecorder.AudioSource.CAMCORDER);
        mediaRecorder.setVideoSource(MediaRecorder.VideoSource.CAMERA);
        //set profile
        mediaRecorder.setProfile(CamcorderProfile.get(CamcorderProfile.QUALITY_HIGH));
        //set output
        mediaRecorder.setOutputFile(context.getExternalCacheDir() + "/record_video");
        //set preview display
        mediaRecorder.setPreviewDisplay(surfaceHolder.getSurface());

        try {
            mediaRecorder.prepare();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     *  停止录制
     */
    public void stopVideoRecord() {
        mediaRecorder.stop();
        releaseMediaRecorder();
    }

    /**
     * 开始视频录制
     */
    public void startVideoRecord() {
        prepareVideoRecord();
        mediaRecorder.start();
    }

    /**
     * 释放Camera
     */
    public void releaseCamera() {
        if (camera != null) {
            camera.release();
            camera = null;
        }
    }

    /**
     * 释放MediaRecorder
     */
    public void releaseMediaRecorder() {
        if (mediaRecorder != null) {
            mediaRecorder.reset();
            mediaRecorder.release();
            camera.lock();
            mediaRecorder = null;
        }
    }
}

