package com.rrtoyewx.opengldemo;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;

import javax.microedition.khronos.opengles.GL10;

/**
 * Created by Rrtoyewx on 16/9/18.
 */
public class LineSurfaceRenderer extends BaseGLSurfaceRenderer {
    static final float[] vertexArray = {
            -0.8f, -0.4f * 1.732f, 0.0f,
            -0.4f, 0.4f * 1.732f, 0.0f,
            0.0f, -0.4f * 1.732f, 0.0f,
            0.4f, 0.4f * 1.732f, 0.0f
    };

    @Override
    public void onDrawFrame(GL10 gl) {
        super.onDrawFrame(gl);
        ByteBuffer byteBuffer = ByteBuffer.allocateDirect(vertexArray.length * 4);
        byteBuffer.order(ByteOrder.nativeOrder());
        FloatBuffer floatBuffer = byteBuffer.asFloatBuffer();
        floatBuffer.put(vertexArray);
        floatBuffer.position(0);

        gl.glPointSize(30f);
        gl.glLineWidth(10f);
        gl.glColor4f(1.0f, 0, 0, 1.0f);
        gl.glLoadIdentity();
        gl.glTranslatef(0, 0, -4f);

        gl.glEnableClientState(GL10.GL_VERTEX_ARRAY);
        gl.glVertexPointer(3, GL10.GL_FLOAT, 0, floatBuffer);
        gl.glDrawArrays(GL10.GL_LINE_LOOP, 0, 4);
        gl.glDisableClientState(GL10.GL_VERTEX_ARRAY);
    }
}
