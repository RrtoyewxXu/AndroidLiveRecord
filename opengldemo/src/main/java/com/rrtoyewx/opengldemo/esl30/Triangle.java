package com.rrtoyewx.opengldemo.esl30;

import android.opengl.GLES20;
import android.opengl.Matrix;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;

/**
 * Created by Rrtoyewx on 16/9/19.
 */
public class Triangle {
    private final int mPrgram;
    private FloatBuffer vertexBuffer;


    static float triangleCoords[] = {
            0.0f, 0.622008459f, 0.0f,
            -0.5f, -0.311004243f, 0.0f,
            0.5f, -0.311004243f, 0.0f,
    };

    private final String vertexShaderCode =
            "attribute vec4 vPosition;" +
                    "uniform mat4 uMVPMatrix;" +
                    "void main() {" +
                    "   gl_Position = uMVPMatrix * vPosition;" +
                    "}";

    private final String fragmentShaderCode =
            "precision mediump float;" +
                    "uniform vec4 vColor;" +
                    "void main() {" +
                    "  gl_FragColor = vColor;" +
                    "}";

    float color[] = {
            1.0f, 0, 0, 1.0f
    };
    private int mPositionHandler;
    private int mMVPMatrixHandle;

    public Triangle() {
        ByteBuffer byteBuffer = ByteBuffer.allocateDirect(triangleCoords.length * 4);
        byteBuffer.order(ByteOrder.nativeOrder());
        vertexBuffer = byteBuffer.asFloatBuffer();
        vertexBuffer.put(triangleCoords);
        vertexBuffer.position(0);

        int vertexShader = MyRenderer.loadShader(GLES20.GL_VERTEX_SHADER, vertexShaderCode);
        int fragmentShader = MyRenderer.loadShader(GLES20.GL_FRAGMENT_SHADER, fragmentShaderCode);
        mPrgram = GLES20.glCreateProgram();
        GLES20.glAttachShader(mPrgram, vertexShader);
        GLES20.glAttachShader(mPrgram, fragmentShader);
        GLES20.glLinkProgram(mPrgram);
    }

    public void draw(float[] mMVPMatrix) {
        GLES20.glUseProgram(mPrgram);

        mMVPMatrixHandle = GLES20.glGetUniformLocation(mPrgram, "uMVPMatrix");
        GLES20.glUniformMatrix4fv(mMVPMatrixHandle, 1, false, mMVPMatrix, 0);

        mPositionHandler = GLES20.glGetAttribLocation(mPrgram, "vPosition");
        GLES20.glEnableVertexAttribArray(mPositionHandler);
        GLES20.glVertexAttribPointer(mPositionHandler, 3, GLES20.GL_FLOAT, false, 0, vertexBuffer);

        int mColorHandle = GLES20.glGetUniformLocation(mPrgram, "vColor");
        GLES20.glUniform4fv(mColorHandle, 1, color, 0);

        GLES20.glDrawArrays(GLES20.GL_TRIANGLES, 0, 3);

        GLES20.glDisableVertexAttribArray(mPositionHandler);
    }


}
