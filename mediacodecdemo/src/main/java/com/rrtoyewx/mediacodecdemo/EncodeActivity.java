package com.rrtoyewx.mediacodecdemo;

import android.graphics.ImageFormat;
import android.hardware.Camera;
import android.media.MediaCodec;
import android.media.MediaCodecInfo;
import android.media.MediaFormat;
import android.media.MediaRecorder;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.widget.Button;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;

public class EncodeActivity extends AppCompatActivity implements SurfaceHolder.Callback {
    String encodeType = "video/avc";
    int cameraWidth = 640;
    int cameraHeight = 480;
    int bitRate = 125000;
    int frameRate = 30;

    Button encodeBtn;
    SurfaceView surfaceView;
    SurfaceHolder surfaceHolder;

    Camera camera;
    MediaCodec mediaEncoder;
    MediaCodec.BufferInfo bufferInfo = new MediaCodec.BufferInfo();


    byte[] mediaInputByte = new byte[cameraWidth * cameraHeight / 2 * 3];
    private long startTime;
    private FileOutputStream fileOutStream;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_encode);
        encodeBtn = (Button) findViewById(R.id.btn_encode);
        surfaceView = (SurfaceView) findViewById(R.id.surface_view);
        encodeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                encodeVideo();
            }
        });

        surfaceHolder = surfaceView.getHolder();
        surfaceHolder.addCallback(this);
    }

    private void encodeVideo() {
        camera.addCallbackBuffer(new byte[cameraWidth * cameraHeight / 2 * 3]);
        startTime = System.currentTimeMillis();
        try {
            fileOutStream = new FileOutputStream(new File(getExternalCacheDir(), "encodeVideo"));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        camera.setPreviewCallbackWithBuffer(new Camera.PreviewCallback() {
            @Override
            public void onPreviewFrame(byte[] data, Camera camera) {
                Log.d("fuck", "get previewFrame");

                //data = yv12 ->yuv420sp
                swapYV12toYUV420SemiPlanar(data, mediaInputByte, cameraWidth, cameraHeight);
                Log.e("fuck", "data:" + data + "mediaInputByte:" + mediaInputByte);
                saveData(mediaInputByte);
                camera.addCallbackBuffer(data);
            }
        });
        mediaEncoder.start();
    }

    private void saveData(byte[] mediaInputByte) {
        ByteBuffer[] inputBuffers = mediaEncoder.getInputBuffers();
        ByteBuffer[] outputBuffers = mediaEncoder.getOutputBuffers();
        int inputIndex = mediaEncoder.dequeueInputBuffer(-1);
        if (inputIndex >= 0) {
            ByteBuffer inputBuffer = inputBuffers[inputIndex];
            inputBuffer.clear();
            inputBuffer.put(mediaInputByte);
            mediaEncoder.queueInputBuffer(inputIndex, 0, mediaInputByte.length, System.currentTimeMillis() - startTime, 0);
        }

        int outputIndex = mediaEncoder.dequeueOutputBuffer(bufferInfo, 2000);
        while (outputIndex >= 0) {
            outputBuffers = mediaEncoder.getOutputBuffers();
            ByteBuffer outputBuffer = outputBuffers[outputIndex];
            byte[] outData = new byte[bufferInfo.size];
            outputBuffer.get(outData);

            try {
                fileOutStream.write(outData, 0, bufferInfo.size);
            } catch (IOException e) {
                e.printStackTrace();
            }
            mediaEncoder.releaseOutputBuffer(outputIndex, false);
            outputIndex = mediaEncoder.dequeueOutputBuffer(bufferInfo, 2000);
        }


    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        initCamera();
        initMediaEncoder();
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
        try {
            camera.setPreviewDisplay(surfaceHolder);
            camera.startPreview();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        releaseCamera();
        releaseMediaEncoder();
    }

    private void initCamera() {
        releaseCamera();
        camera = Camera.open();
        Camera.Parameters parameters = camera.getParameters();
        parameters.setPreviewSize(640, 480);
        parameters.setPictureSize(640, 480);
        parameters.setPreviewFormat(ImageFormat.YV12);
        camera.setParameters(parameters);
    }

    private void releaseCamera() {
        if (camera != null) {
            camera.stopPreview();
            camera.release();
            camera = null;
        }
    }

    private void initMediaEncoder() {
        releaseMediaEncoder();
        try {
            mediaEncoder = MediaCodec.createEncoderByType(encodeType);
            MediaFormat mediaFormat = MediaFormat.createVideoFormat(encodeType, cameraWidth, cameraHeight);
            mediaFormat.setInteger(MediaFormat.KEY_BIT_RATE, bitRate);
            mediaFormat.setInteger(MediaFormat.KEY_FRAME_RATE, frameRate);
            mediaFormat.setInteger(MediaFormat.KEY_COLOR_FORMAT, MediaCodecInfo.CodecCapabilities.COLOR_FormatYUV420SemiPlanar);
            mediaFormat.setInteger(MediaFormat.KEY_I_FRAME_INTERVAL, 5);
            mediaEncoder.configure(mediaFormat, null, null, MediaCodec.CONFIGURE_FLAG_ENCODE);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void releaseMediaEncoder() {
        if (mediaEncoder != null) {
            mediaEncoder.stop();
            mediaEncoder.release();
            mediaEncoder = null;
        }
    }

    private void swapYV12toYUV420SemiPlanar(byte[] yv12bytes, byte[] i420bytes, int width, int height) {
        System.arraycopy(yv12bytes, 0, i420bytes, 0, width * height);
        int startPos = width * height;
        int yv_start_pos_v = width * height + width;
        int yv_start_pos_u = width * height + width * height / 4;
        for (int i = 0; i < width * height / 4; i++) {
            i420bytes[startPos + 2 * i + 0] = yv12bytes[yv_start_pos_u + i];
            i420bytes[startPos + 2 * i + 1] = yv12bytes[yv_start_pos_v + i];
        }
    }
}
