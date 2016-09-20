package com.rrtoyewx.opengldemo.esl10;

import android.opengl.GLSurfaceView;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.rrtoyewx.opengldemo.R;

public class MainActivity extends AppCompatActivity {
    GLSurfaceView glSurfaceView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        glSurfaceView = (GLSurfaceView) findViewById(R.id.gl_surface_view);
        glSurfaceView.setRenderer(new PointSurfaceRenderer());
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
