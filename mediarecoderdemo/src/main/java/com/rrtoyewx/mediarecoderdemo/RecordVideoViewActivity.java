package com.rrtoyewx.mediarecoderdemo;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.SurfaceView;
import android.view.View;
import android.widget.Button;
import android.widget.VideoView;

public class RecordVideoViewActivity extends AppCompatActivity {
    private VideoView videoView;
    private Button recordBtn;
    private SurfaceView surfaceView;
    String videoUri = "http://106.120.175.77/videos/v1/20160815/5b/4c/3855808cdef62f3baf655c208a27e189.mp4" +
            "?key=0aa08844f0a84470271e7ad8441b53c1b&dis_k=b3404280c6817c040fc8314172c05ff3&dis_t=1473318189" +
            "&src=iqiyi.com&v=841987413&qd_src=vcl" +
            "&qd_tm=1473318166108&qd_ip=220.181.34.101&qd_sc=fb304ef28a2dfb1008e95da9611c6616" +
            "&qypid=6217043609_-101855&uuid=dcb52265-57d10d2d-59";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_record_video_view);
        videoView = (VideoView) findViewById(R.id.video_view);
        recordBtn = (Button) findViewById(R.id.record_video);
        surfaceView = (SurfaceView) findViewById(R.id.surface_view);

        videoView.setVideoPath(videoUri);
        videoView.start();
        recordBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }


}
