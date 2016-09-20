package com.rrtoyewx.opengldemo.esl10;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;

import javax.microedition.khronos.opengles.GL10;

/**
 * Created by Rrtoyewx on 16/9/19.
 */
public class TriangleSurfaceRenderer extends BaseGLSurfaceRenderer {
    static final float[] vertexArray = new float[]{
            -0.8f, -0.4f * 1.732f, 0.0f,
            0.0f, -0.4f * 1.732f, 0.0f,
            -0.4f, 0.4f * 1.732f, 0.0f,
            0.0f, -0.0f * 1.732f, 0.0f,
            0.8f, -0.0f * 1.732f, 0.0f,
            0.4f, 0.4f * 1.732f, 0.0f,
    };

    @Override
    public void onDrawFrame(GL10 gl) {
        super.onDrawFrame(gl);
        ByteBuffer vertexBuffer = ByteBuffer.allocateDirect(vertexArray.length * 4);
        vertexBuffer.order(ByteOrder.nativeOrder());
        FloatBuffer floatBuffer = vertexBuffer.asFloatBuffer();
        floatBuffer.put(vertexArray);
        floatBuffer.position(0);

        gl.glColor4f(1.0f, 0.0f, 0.0f, 1.0f);
        gl.glLineWidth(20f);
        gl.glPointSize(20f);
        gl.glLoadIdentity();

        gl.glTranslatef(0, 0, -4f);
        gl.glEnableClientState(GL10.GL_VERTEX_ARRAY);
        gl.glVertexPointer(3, GL10.GL_FLOAT, 0, floatBuffer);
        gl.glDrawArrays(GL10.GL_TRIANGLE_STRIP, 0, 6);
        gl.glDisableClientState(GL10.GL_VERTEX_ARRAY);
    }
}
