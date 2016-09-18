package com.rrtoyewx.opengldemo;

import android.opengl.GLSurfaceView;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity {
    GLSurfaceView glSurfaceView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        glSurfaceView = (GLSurfaceView) findViewById(R.id.gl_surface_view);
        glSurfaceView.setRenderer(new LineSurfaceRenderer());
    }

    @Override
    protected void onResume() {
        glSurfaceView.onResume();
        super.onResume();
    }

    @Override
    protected void onPause() {
        glSurfaceView.onPause();
        super.onPause();
    }
}
