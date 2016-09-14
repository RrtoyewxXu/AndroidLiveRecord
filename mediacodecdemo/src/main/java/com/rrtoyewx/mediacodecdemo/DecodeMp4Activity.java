package com.rrtoyewx.mediacodecdemo;

import android.media.MediaCodec;
import android.media.MediaExtractor;
import android.media.MediaFormat;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.SurfaceView;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.io.IOException;
import java.nio.ByteBuffer;

public class DecodeMp4Activity extends AppCompatActivity {
    Button decodeBtn;
    TextView stampTimeTxt;
    SurfaceView surfaceView;

    MediaCodec mediaCodec;
    MediaExtractor mediaExtractor;
    String filePath = Environment.getExternalStorageDirectory().getAbsolutePath() + "/from_camera";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_decode_mp4);
        decodeBtn = (Button) findViewById(R.id.decode_btn);
        surfaceView = (SurfaceView) findViewById(R.id.surface_view);
        stampTimeTxt = (TextView) findViewById(R.id.stamp_time);

        decodeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                initMedia();
                decodeMp4();
                startCountDownTime();
            }
        });
    }

    private void initMedia() {
        initMediaExtractor();
        initMediaCodec();
    }

    private void initMediaExtractor() {
        mediaExtractor = new MediaExtractor();
        try {
            mediaExtractor.setDataSource(filePath);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void startCountDownTime() {
        new CountDownTimer(20 * 1000, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                stampTimeTxt.setText(String.valueOf(millisUntilFinished / 1000));
            }

            @Override
            public void onFinish() {

            }
        }.start();
    }

    private void initMediaCodec() {
        try {
            mediaCodec = MediaCodec.createDecoderByType("video/avc");

            int trackCount = mediaExtractor.getTrackCount();
            for (int i = 0; i < trackCount; i++) {
                MediaFormat trackFormat = mediaExtractor.getTrackFormat(i);
                String mimeType = trackFormat.getString(MediaFormat.KEY_MIME);
                if (mimeType.startsWith("video/")) {
                    mediaExtractor.selectTrack(i);
                    mediaCodec.configure(trackFormat, surfaceView.getHolder().getSurface(), null, 0);
                    mediaCodec.start();
                    break;
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void decodeMp4() {
        new Thread() {
            @Override
            public void run() {
                ByteBuffer[] outputBuffers = mediaCodec.getOutputBuffers();
                ByteBuffer[] inputBuffers = mediaCodec.getInputBuffers();
                MediaCodec.BufferInfo bufferInfo = new MediaCodec.BufferInfo();
                boolean frameRemain = true;
                while (true) {
                    if (frameRemain) {
                        int inputBufferIndex = mediaCodec.dequeueInputBuffer(-1);

                        if (inputBufferIndex >= 0) {
                            ByteBuffer inputBuffer = inputBuffers[inputBufferIndex];
                            int readSampleDataSize = mediaExtractor.readSampleData(inputBuffer, 0);

                            if (readSampleDataSize >= 0) {
                                mediaCodec.queueInputBuffer(inputBufferIndex, 0, readSampleDataSize, mediaExtractor.getSampleTime(), 0);
                                mediaExtractor.advance();
                            } else {
                                mediaCodec.queueInputBuffer(inputBufferIndex, 0, 0, 0, MediaCodec.BUFFER_FLAG_END_OF_STREAM);
                                frameRemain = false;
                            }
                        }
                    }

                    Log.e("TAG",frameRemain+"frameRemain");
                    int outputBufferIndex = mediaCodec.dequeueOutputBuffer(bufferInfo, 12000);
                    if (outputBufferIndex == MediaCodec.INFO_OUTPUT_BUFFERS_CHANGED) {
                        outputBuffers = mediaCodec.getOutputBuffers();
                    } else if (outputBufferIndex >= 0) {
                        ByteBuffer outputBuffer = outputBuffers[outputBufferIndex];
                        mediaCodec.releaseOutputBuffer(outputBufferIndex, true);
                    }

                    if ((bufferInfo.flags & MediaCodec.BUFFER_FLAG_END_OF_STREAM) != 0) {
                        break;
                    }
                }

                release();
                Log.e("TAG", "finish");
            }
        }.start();
    }

    private void release() {
        if (mediaExtractor != null) {
            mediaExtractor.release();
            mediaExtractor = null;
        }

        if (mediaCodec != null) {
            mediaCodec.stop();
            mediaCodec.release();
            mediaCodec = null;
        }
    }
}
