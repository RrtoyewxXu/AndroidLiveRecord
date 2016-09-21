package com.rrtoyewx.mediacodecdemo.openGLEncode;

import android.hardware.Camera;
import android.media.MediaCodec;
import android.media.MediaCodecInfo;
import android.media.MediaFormat;
import android.media.MediaMuxer;
import android.os.Environment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.rrtoyewx.mediacodecdemo.R;

import java.io.IOException;

public class EncodeOpenGLCameraActivity extends AppCompatActivity {

    Button startRecord;
    private Camera camera;
    private MediaCodec mediaEncoder;
    private CodecInputSurface codecInputSurface;
    private MediaMuxer mediaMuxer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_encode_open_glcamera);
        startRecord = (Button) findViewById(R.id.start_record);

        startRecord.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startRecord();
            }
        });
    }

    private void startRecord() {
        new Thread() {
            @Override
            public void run() {
                encodeCameraToMp4();
                super.run();
            }
        }.start();
    }

    private void encodeCameraToMp4() {
        prepareCamera();
        prepareMediaCodec();
        codecInputSurface.makeCurrent();
        prepareSurfaceTexture();
    }

    private void prepareCamera() {
        camera = Camera.open();
        Camera.Parameters parameters = camera.getParameters();
        parameters.setPictureSize(640, 480);
        parameters.setPreviewSize(640, 480);

        camera.setParameters(parameters);
    }

    private void prepareMediaCodec() {
        try {
            mediaEncoder = MediaCodec.createEncoderByType("video/avc");
            MediaFormat mediaFormat = MediaFormat.createVideoFormat("video/avc", 640, 480);
            mediaFormat.setInteger(MediaFormat.KEY_FRAME_RATE, 25);
            mediaFormat.setInteger(MediaFormat.KEY_COLOR_FORMAT,
                    MediaCodecInfo.CodecCapabilities.COLOR_FormatSurface);
            mediaFormat.setInteger(MediaFormat.KEY_I_FRAME_INTERVAL, 1);
            mediaFormat.setInteger(MediaFormat.KEY_BIT_RATE, 1250 * 1000);

            mediaEncoder.configure(mediaFormat, null, null, MediaCodec.CONFIGURE_FLAG_ENCODE);
            codecInputSurface = new CodecInputSurface(mediaEncoder.createInputSurface());

            mediaMuxer = new MediaMuxer(Environment.getExternalStorageDirectory().getAbsolutePath() + "/opengl",
                    MediaMuxer.OutputFormat.MUXER_OUTPUT_MPEG_4);

            mediaEncoder.start();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void prepareSurfaceTexture(){

    }


}
