package com.rrtoyewx.opengldemo.esl10;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import java.nio.ShortBuffer;

import javax.microedition.khronos.opengles.GL10;

/**
 * Created by Rrtoyewx on 16/9/19.
 */
public class RectangleSurfaceRenderer extends BaseGLSurfaceRenderer {
    static final float[] vertexArray = new float[]{
            -0.8f, 0.4f * 1.732f, 0f,
            -0.8f, -0.4f * 1.732f, 0f,
            0.8f, -0.4f * 1.732f, 0f,
            0.8f, 0.4f * 1.732f, 0f
    };

    static final short[] indices = {
            0, 1, 2,
            2, 3, 0,
    };

    @Override
    public void onDrawFrame(GL10 gl) {
        super.onDrawFrame(gl);
        ByteBuffer vertexByte = ByteBuffer.allocateDirect(vertexArray.length * 4);
        vertexByte.order(ByteOrder.nativeOrder());
        FloatBuffer vertexBuffer = vertexByte.asFloatBuffer();
        vertexBuffer.put(vertexArray);
        vertexBuffer.position(0);

        ByteBuffer indexByte = ByteBuffer.allocateDirect(indices.length * 2);
        indexByte.order(ByteOrder.nativeOrder());
        ShortBuffer indexBuffer = indexByte.asShortBuffer();
        indexBuffer.put(indices);
        indexBuffer.position(0);

        gl.glColor4f(1.0f, 0f, 0f, 1.0f);
        gl.glPointSize(20f);
        gl.glLineWidth(20f);
        gl.glLoadIdentity();
        gl.glTranslatef(0, 0, -4f);
        gl.glFrontFace(GL10.GL_CCW);
        gl.glEnable(GL10.GL_CULL_FACE);
        gl.glCullFace(GL10.GL_BACK);

        gl.glEnableClientState(GL10.GL_VERTEX_ARRAY);
        gl.glVertexPointer(3, GL10.GL_FLOAT, 0, vertexBuffer);
        gl.glDrawElements(GL10.GL_TRIANGLES, indices.length, GL10.GL_UNSIGNED_SHORT, indexBuffer);
        gl.glDisableClientState(GL10.GL_VERTEX_ARRAY);
    }
}
