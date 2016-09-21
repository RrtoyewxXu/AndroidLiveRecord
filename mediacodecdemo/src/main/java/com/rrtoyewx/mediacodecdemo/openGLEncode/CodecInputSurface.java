package com.rrtoyewx.mediacodecdemo.openGLEncode;

import android.opengl.EGL14;
import android.opengl.EGLConfig;
import android.opengl.EGLContext;
import android.opengl.EGLDisplay;
import android.opengl.EGLSurface;
import android.view.Surface;

/**
 * Created by Rrtoyewx on 16/9/20.
 */
public class CodecInputSurface {
    private static final int EGL_RECORDABLE_ANDROID = 1;
    private Surface inputSurface;
    private EGLDisplay eglDisplay = EGL14.EGL_NO_DISPLAY;
    private EGLContext eglContext = EGL14.EGL_NO_CONTEXT;
    private EGLSurface eglSurface = EGL14.EGL_NO_SURFACE;

    public CodecInputSurface(Surface inputSurface) {
        this.inputSurface = inputSurface;
        setUpGL();
    }

    private void setUpGL() {
        eglDisplay = EGL14.eglGetDisplay(EGL14.EGL_DEFAULT_DISPLAY);
        int[] version = new int[2];
        EGL14.eglInitialize(eglDisplay, version, 0, version, 1);

        int[] attribList = {
                EGL14.EGL_RED_SIZE, 8,
                EGL14.EGL_GREEN_SIZE, 8,
                EGL14.EGL_BLUE_SIZE, 8,
                EGL14.EGL_ALPHA_SIZE, 8,
                EGL14.EGL_RENDERABLE_TYPE, EGL14.EGL_OPENGL_ES2_BIT,
                EGL_RECORDABLE_ANDROID, 1,
                EGL14.EGL_NONE
        };
        EGLConfig[] eglConfigs = new EGLConfig[1];
        int[] numberConfig = new int[1];
        EGL14.eglChooseConfig(eglDisplay, attribList, 0, eglConfigs, 0, eglConfigs.length, numberConfig, 0);

        int[] attrib_list = {
                EGL14.EGL_CONTEXT_CLIENT_VERSION, 2,
                EGL14.EGL_NONE
        };
        eglContext = EGL14.eglCreateContext(eglDisplay, eglConfigs[0], EGL14.EGL_NO_CONTEXT, attrib_list, 0);

        int[] surfaceAttribs = {
                EGL14.EGL_NONE
        };
        EGL14.eglCreateWindowSurface(eglDisplay, eglConfigs[0], inputSurface, surfaceAttribs, 0);
    }

    public void makeCurrent() {
        EGL14.eglMakeCurrent(eglDisplay, eglSurface, eglSurface, eglContext);
    }
}
