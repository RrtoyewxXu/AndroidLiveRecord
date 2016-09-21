package com.rrtoyewx.mediacodecdemo.openGLEncode;

import android.graphics.SurfaceTexture;

/**
 * Created by Rrtoyewx on 16/9/20.
 */
public class SurfaceTextureManager implements SurfaceTexture.OnFrameAvailableListener {
    private SurfaceTexture surfaceTexture;

    private Object frameSyncObject = new Object();
    private boolean frameAvailable;

    private STextureRender sTextureRender;

    public SurfaceTextureManager() {
        sTextureRender = new STextureRender();
        sTextureRender.surfaceCreate();

        int textureId = sTextureRender.getTextureId();
        surfaceTexture = new SurfaceTexture(textureId);
        surfaceTexture.setOnFrameAvailableListener(this);
    }


    @Override
    public void onFrameAvailable(SurfaceTexture surfaceTexture) {

    }
}
