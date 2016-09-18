package com.rrtoyewx.opengldemo;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;

import javax.microedition.khronos.opengles.GL10;

/**
 * Created by Rrtoyewx on 16/9/18.
 */
public class PointSurfaceRenderer extends BaseGLSurfaceRenderer {
    float[] vertexArray = new float[]{
            -0.8f , -0.4f * 1.732f , 0.0f ,
            0.8f , -0.4f * 1.732f , 0.0f ,
            0.0f , 0.4f * 1.732f , 0.0f ,
    };
    @Override
    public void onDrawFrame(GL10 gl) {
        super.onDrawFrame(gl);

        ByteBuffer vertexByteBuffer = ByteBuffer.allocateDirect(vertexArray.length * 4);
        vertexByteBuffer.order(ByteOrder.nativeOrder());
        FloatBuffer floatBuffer = vertexByteBuffer.asFloatBuffer();
        floatBuffer.put(vertexArray);
        floatBuffer.position(0);

        gl.glColor4f(1.0f, 0.0f, 0.0f, 1.0f);
        gl.glPointSize(20f);
        gl.glLoadIdentity();
        gl.glTranslatef(0, 0, -4f);

        gl.glEnableClientState(GL10.GL_VERTEX_ARRAY);
        gl.glVertexPointer(3, GL10.GL_FLOAT, 0, floatBuffer);
        gl.glDrawArrays(GL10.GL_POINTS, 0, 3);
        gl.glDisableClientState(GL10.GL_VERTEX_ARRAY);
//        ByteBuffer vbb
//                = ByteBuffer.allocateDirect(vertexArray.length*4);
//        vbb.order(ByteOrder.nativeOrder());
//        FloatBuffer vertex = vbb.asFloatBuffer();
//        vertex.put(vertexArray);
//        vertex.position(0);
//
//        gl.glColor4f(1.0f, 0.0f, 0.0f, 1.0f);
//        gl.glPointSize(8f);
//        gl.glLoadIdentity();
//        gl.glTranslatef(0, 0, -4);
//        gl.glEnableClientState(GL10.GL_VERTEX_ARRAY);
//        gl.glVertexPointer(3, GL10.GL_FLOAT, 0, vertex);
//        gl.glDrawArrays(GL10.GL_POINTS, 0, 3);
//        gl.glDisableClientState(GL10.GL_VERTEX_ARRAY);
    }
}
