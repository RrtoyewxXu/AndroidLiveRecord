package com.rrtoyewx.opengldemo;

import java.nio.FloatBuffer;

/**
 * Created by Rrtoyewx on 16/9/18.
 */
public class Triangle {
    private FloatBuffer vertexBuffer;
    static final int COORDS_PER_VERTEX = 3;
    static float[] triangleCoords = {
            -0.8f, -0.4f * 1.732f, 0.0f,
            0.8f, -0.4f * 1.732f, 0.0f,
            0.0f, 0.4f * 1.732f, 0.0f,
    };


}