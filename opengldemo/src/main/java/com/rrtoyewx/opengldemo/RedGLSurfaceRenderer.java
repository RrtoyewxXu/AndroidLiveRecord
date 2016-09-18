package com.rrtoyewx.opengldemo;

import javax.microedition.khronos.opengles.GL10;

/**
 * Created by Rrtoyewx on 16/9/18.
 */
public class RedGLSurfaceRenderer extends BaseGLSurfaceRenderer {
    @Override
    public void onDrawFrame(GL10 gl) {
        gl.glClearColor(1.0f, 0, 0, 0);
        gl.glClear(GL10.GL_COLOR_BUFFER_BIT | GL10.GL_DEPTH_BUFFER_BIT);
    }
}
