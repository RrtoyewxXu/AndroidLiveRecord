package com.rrtoyewx.opengldemo.esl10;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import java.nio.ShortBuffer;

import javax.microedition.khronos.opengles.GL10;

/**
 * Created by Rrtoyewx on 16/9/19.
 */
public class BodiesSurfaceRenderer extends BaseGLSurfaceRenderer {
    static final float X = 0.525731112119133606f;
    static final float Z = 0.850650808352039932f;

    static final float[] vertexArray = new float[]{
            -X, 0.0f, Z,
            X, 0.0f, Z,
            -X, 0.0f, -Z,
            X, 0.0f, -Z,
            0.0f, Z, X,
            0.0f, Z, -X,
            0.0f, -Z, X,
            0.0f, -Z, -X,
            Z, X, 0.0f,
            -Z, X, 0.0f,
            Z, -X, 0.0f,
            -Z, -X, 0.0f
    };

    static short indices[] = new short[]{
            0, 4, 1,
            0, 9, 4,
            9, 5, 4,
            4, 5, 8,
            4, 8, 1,
            8, 10, 1,
            8, 3, 10,
            5, 3, 8,
            5, 2, 3,
            2, 7, 3,
            7, 10, 3,
            7, 6, 10,
            7, 11, 6,
            11, 0, 6,
            0, 1, 6,
            6, 1, 10,
            9, 0, 11,
            9, 11, 2,
            9, 2, 5,
            7, 2, 11,
    };

    static final float[] colors = new float[]{
            0f, 0f, 0f, 1f,
            0f, 0f, 1f, 1f,
            0f, 1f, 0f, 1f,
            0f, 1f, 1f, 1f,
            1f, 0f, 0f, 1f,
            1f, 0f, 1f, 1f,
            1f, 1f, 0f, 1f,
            1f, 1f, 1f, 1f,
            1f, 0f, 0f, 1f,
            0f, 1f, 0f, 1f,
            0f, 0f, 1f, 1f,
            1f, 0f, 1f, 1f,
    };
    private float angle;

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

        ByteBuffer colorByte = ByteBuffer.allocateDirect(colors.length * 4);
        colorByte.order(ByteOrder.nativeOrder());
        FloatBuffer colorBuffer = colorByte.asFloatBuffer();
        colorBuffer.put(colors);
        colorBuffer.position(0);

        gl.glColor4f(1.0f, 0f, 0f, 1.0f);
        gl.glLoadIdentity();
        gl.glTranslatef(0, 0, -4f);
        gl.glRotatef(angle, 0, 1, 0);
        gl.glFrontFace(GL10.GL_CCW);
        gl.glEnable(GL10.GL_CULL_FACE);
        gl.glCullFace(GL10.GL_BACK);

        gl.glEnableClientState(GL10.GL_VERTEX_ARRAY);
        gl.glVertexPointer(3, GL10.GL_FLOAT, 0, vertexBuffer);
        gl.glEnableClientState(GL10.GL_COLOR_ARRAY);
        gl.glColorPointer(4, GL10.GL_FLOAT, 0, colorBuffer);
        gl.glDrawElements(GL10.GL_TRIANGLES, indices.length, GL10.GL_UNSIGNED_SHORT, indexBuffer);
        gl.glDisableClientState(GL10.GL_VERTEX_ARRAY);

        gl.glDisable(GL10.GL_CULL_FACE);
        angle++;
    }
}
