package com.rrtoyewx.mediacodecdemo;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.media.MediaCodec;
import android.media.MediaCodecInfo;
import android.media.MediaFormat;
import android.media.MediaMuxer;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Surface;
import android.view.View;
import android.widget.Button;

import java.io.IOException;
import java.nio.ByteBuffer;

public class EncoderSurfaceActivity extends AppCompatActivity {
    Button encodeBtn;
    MediaCodec mediaCodec;
    MediaMuxer mediaMuxer;

    Surface surface;
    Paint paint;

    String filePath = Environment.getExternalStorageDirectory().getAbsolutePath() + "/output_surface";
    int writeTrackIndex = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_encoder_surface);

        encodeBtn = (Button) findViewById(R.id.btn_encode);
        encodeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startEncode();
            }
        });
    }

    private void startEncode() {
        init();
        encode();
    }

    private void init() {
        //初始化编码器
        initMediaCodec();
        //初始化视频合成器
        initMediaMuxer();
        //初始化画笔，用来绘制
        initPaint();
    }

    private void encode() {
        new Thread() {
            @Override
            public void run() {
                boolean remainFrame = true;
                int frameIndex = 0;

                while (remainFrame) {
                    //编码
                    drainEncoder(false);
                    long time = computePresentationTimeMs(frameIndex);
                    remainFrame = renderFromSource(time);
                    frameIndex++;
                }
                drainEncoder(true);
                Log.e("TAG", "finish");
                release();
            }
        }.start();
    }

    private void initMediaCodec() {
        try {
            // 创建mediaFormat
            MediaFormat mediaFormat = MediaFormat.createVideoFormat("video/avc", 640, 480);
            mediaFormat.setInteger(MediaFormat.KEY_COLOR_FORMAT, MediaCodecInfo.CodecCapabilities.COLOR_FormatSurface);
            mediaFormat.setInteger(MediaFormat.KEY_BIT_RATE, 1250000);
            mediaFormat.setInteger(MediaFormat.KEY_I_FRAME_INTERVAL, 2);
            mediaFormat.setInteger(MediaFormat.KEY_FRAME_RATE, 25);

            //H.264 video coding
            mediaCodec = MediaCodec.createEncoderByType("video/avc");
            mediaCodec.configure(mediaFormat, null, null, MediaCodec.CONFIGURE_FLAG_ENCODE);
            // 创建收集的surface
            surface = mediaCodec.createInputSurface();
            mediaCodec.start();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void initMediaMuxer() {
        try {
            mediaMuxer = new MediaMuxer(filePath, MediaMuxer.OutputFormat.MUXER_OUTPUT_MPEG_4);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void initPaint() {
        paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setTextSize(50);
    }


    private boolean renderFromSource(long time) {
        Canvas canvas = surface.lockCanvas(null);
        //绘制画布
        boolean frameRemain = renderFrame(canvas, time, 1000 / 25);
        surface.unlockCanvasAndPost(canvas);
        return frameRemain;
    }

    public boolean renderFrame(Canvas canvas, long time, long interval) {
        // 绘制背景
        paint.setColor(Color.GREEN);
        canvas.drawRect(0, 0, canvas.getWidth(), canvas.getHeight(), paint);

        //绘制数字
        paint.setColor(Color.WHITE);
        canvas.drawText(String.format("%.2f", (float) time / 1000f), canvas.getWidth() / 2, canvas.getHeight() / 2, paint);

        return time + interval <= 10 * 1000;
    }

    private long computePresentationTimeMs(int frameIndex) {
        return frameIndex * 1000 / 25;
    }

    private void drainEncoder(boolean endOfStream) {

        if (endOfStream) {
            mediaCodec.signalEndOfInputStream();
        }

        ByteBuffer[] encoderOutputBuffers = mediaCodec.getOutputBuffers();
        MediaCodec.BufferInfo bufferInfo = new MediaCodec.BufferInfo();
        while (true) {
            int encoderStatus = mediaCodec.dequeueOutputBuffer(bufferInfo, 12000);

            if (encoderStatus == MediaCodec.INFO_TRY_AGAIN_LATER) {
                if (!endOfStream) {
                    break;
                }

            } else if (encoderStatus == MediaCodec.INFO_OUTPUT_BUFFERS_CHANGED) {
                encoderOutputBuffers = mediaCodec.getOutputBuffers();
            } else if (encoderStatus == MediaCodec.INFO_OUTPUT_FORMAT_CHANGED) {
                MediaFormat newFormat = mediaCodec.getOutputFormat();
                Log.d("tag", "encoder output format changed: " + newFormat);

                writeTrackIndex = mediaMuxer.addTrack(newFormat);
                mediaMuxer.start();

            } else if (encoderStatus >= 0) {

                if ((bufferInfo.flags & MediaCodec.BUFFER_FLAG_CODEC_CONFIG) != 0) {
                    bufferInfo.size = 0;
                }

                ByteBuffer encodedData = encoderOutputBuffers[encoderStatus];
                if (bufferInfo.size != 0) {
                    encodedData.position(bufferInfo.offset);
                    encodedData.limit(bufferInfo.offset + bufferInfo.size);
                    if (writeTrackIndex >= 0) {
                        Log.e("tag", "write data");
                        //写数据
                        mediaMuxer.writeSampleData(writeTrackIndex, encodedData, bufferInfo);
                    }
                }

                mediaCodec.releaseOutputBuffer(encoderStatus, false);
                if ((bufferInfo.flags & MediaCodec.BUFFER_FLAG_END_OF_STREAM) != 0) {
                    break;
                }
            }
        }
    }

    private void release() {
        if (mediaCodec != null) {
            mediaCodec.release();
            mediaCodec = null;
        }

        if (mediaMuxer != null) {
            mediaMuxer.stop();
            mediaMuxer.release();
            mediaMuxer = null;
        }

        if (surface != null) {
            surface.release();
            surface = null;
        }
    }
}
