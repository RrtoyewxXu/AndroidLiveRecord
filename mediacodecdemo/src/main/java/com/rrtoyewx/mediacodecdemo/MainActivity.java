package com.rrtoyewx.mediacodecdemo;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {
    Button encodeCameraBtn;
    Button encodeSurfaceBtn;
    Button decodeSurfaceBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        encodeCameraBtn = (Button) findViewById(R.id.encode_camera_video);
        encodeSurfaceBtn = (Button) findViewById(R.id.encode_surface_video);
        decodeSurfaceBtn = (Button) findViewById(R.id.decode_surface_video);

        encodeCameraBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, EncodeCameraActivity.class));
            }
        });
        encodeSurfaceBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, EncoderSurfaceActivity.class));
            }
        });

        decodeSurfaceBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, DecodeMp4Activity.class));
            }
        });

    }
}
