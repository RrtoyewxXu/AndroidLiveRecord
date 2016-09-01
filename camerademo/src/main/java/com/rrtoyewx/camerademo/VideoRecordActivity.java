package com.rrtoyewx.camerademo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;

public class VideoRecordActivity extends AppCompatActivity {
    private Button startRecordButton;
    private Button stopRecordButton;
    private FrameLayout frameLayout;
    private RecordVideoLayout recordVideoLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video_record);
        startRecordButton = (Button) findViewById(R.id.btn_start_record);
        stopRecordButton = (Button) findViewById(R.id.btn_stop_record);
        frameLayout = (FrameLayout) findViewById(R.id.video_record);
        recordVideoLayout = new RecordVideoLayout(this);
        frameLayout.addView(recordVideoLayout);
        startRecordButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                recordVideoLayout.startVideoRecord();
            }
        });

        stopRecordButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                recordVideoLayout.stopVideoRecord();
            }
        });
    }
}
