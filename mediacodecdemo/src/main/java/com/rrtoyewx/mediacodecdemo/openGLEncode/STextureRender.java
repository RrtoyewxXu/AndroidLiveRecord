package com.rrtoyewx.mediacodecdemo.openGLEncode;

import android.opengl.GLES11Ext;
import android.opengl.GLES20;

/**
 * Created by Rrtoyewx on 16/9/20.
 */
public class STextureRender {
    private int textureId;

    public STextureRender() {

    }

    public void surfaceCreate() {
        createTextureId();
    }

    private void createTextureId() {
        int[] texture = new int[1];

        GLES20.glGenTextures(1, texture, 0);
        textureId = texture[0];
        GLES20.glBindTexture(GLES11Ext.GL_TEXTURE_EXTERNAL_OES, textureId);

        GLES20.glTexParameterf(GLES11Ext.GL_TEXTURE_EXTERNAL_OES, GLES20.GL_TEXTURE_MIN_FILTER,
                GLES20.GL_NEAREST);
        GLES20.glTexParameterf(GLES11Ext.GL_TEXTURE_EXTERNAL_OES, GLES20.GL_TEXTURE_MAG_FILTER,
                GLES20.GL_LINEAR);
        GLES20.glTexParameteri(GLES11Ext.GL_TEXTURE_EXTERNAL_OES, GLES20.GL_TEXTURE_WRAP_S,
                GLES20.GL_CLAMP_TO_EDGE);
        GLES20.glTexParameteri(GLES11Ext.GL_TEXTURE_EXTERNAL_OES, GLES20.GL_TEXTURE_WRAP_T,
                GLES20.GL_CLAMP_TO_EDGE);
    }

    public int getTextureId(){
        return textureId;
    }
}