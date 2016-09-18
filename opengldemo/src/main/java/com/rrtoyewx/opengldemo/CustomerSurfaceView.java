package com.rrtoyewx.opengldemo;

import android.content.Context;

import android.opengl.GLSurfaceView;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import javax.microedition.khronos.egl.EGL10;
import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.egl.EGLContext;
import javax.microedition.khronos.egl.EGLDisplay;
import javax.microedition.khronos.egl.EGLSurface;
import javax.microedition.khronos.opengles.GL10;

/**
 * Created by Rrtoyewx on 16/9/18.
 */
public class CustomerSurfaceView extends SurfaceView implements SurfaceHolder.Callback, Runnable {
    private SurfaceHolder mHolder;
    private Thread thread;
    private boolean running;
    private Renderer mRenderer;
    private int mWidth;
    private int mHeight;

    public CustomerSurfaceView(Context context) {
        super(context);
        mHolder = getHolder();
        mHolder.addCallback(this);
        mHolder.setType(SurfaceHolder.SURFACE_TYPE_GPU);
    }

    public void setRenderer(Renderer renderer) {
        mRenderer = renderer;
    }

    public void surfaceCreated(SurfaceHolder holder) {
    }

    public void surfaceDestroyed(SurfaceHolder holder) {
        running = false;
        try {
            thread.join();
        } catch (InterruptedException e) {
        }
        thread = null;
    }

    public void surfaceChanged(SurfaceHolder holder, int format, int w, int h) {
        synchronized (this) {
            mWidth = w;
            mHeight = h;
            thread = new Thread(this);
            thread.start();
        }
    }

    public interface Renderer {
        void EGLCreate(SurfaceHolder holder);

        void EGLDestroy();

        int Initialize(int width, int height);

        void DrawScene(int width, int height);
    }

    public void run() {
        synchronized (this) {
            mRenderer.EGLCreate(mHolder);
            mRenderer.Initialize(mWidth, mHeight);
            running = true;
            while (running) {
                mRenderer.DrawScene(mWidth, mHeight);
            }
            mRenderer.EGLDestroy();
        }
    }

    class GLRenderer implements CustomerSurfaceView.Renderer {
        private EGL10 egl;
        private GL10 gl;
        private EGLDisplay eglDisplay;
        private EGLConfig eglConfig;
        private EGLContext eglContext;
        private EGLSurface eglSurface;

        public GLRenderer() {

        }

        public int Initialize(int width, int height) {
            gl.glClearColor(1.0f, 0.0f, 0.0f, 0.0f);
            return 1;
        }

        public void DrawScene(int width, int height) {
            gl.glClear(GL10.GL_COLOR_BUFFER_BIT);
            egl.eglSwapBuffers(eglDisplay, eglSurface);
        }

        public void EGLCreate(SurfaceHolder holder) {
            int[] num_config = new int[1];
            EGLConfig[] configs = new EGLConfig[1];
            int[] configSpec = {
                    EGL10.EGL_RED_SIZE, 8,
                    EGL10.EGL_GREEN_SIZE, 8,
                    EGL10.EGL_BLUE_SIZE, 8,
                    EGL10.EGL_SURFACE_TYPE, EGL10.EGL_WINDOW_BIT,
                    EGL10.EGL_NONE
            };
            this.egl = (EGL10) EGLContext.getEGL();
            eglDisplay = this.egl.eglGetDisplay(EGL10.EGL_DEFAULT_DISPLAY);
            this.egl.eglInitialize(eglDisplay, null);
            this.egl.eglChooseConfig(eglDisplay, configSpec,
                    configs, 1, num_config);
            eglConfig = configs[0];
            eglContext = this.egl.eglCreateContext(eglDisplay, eglConfig,
                    EGL10.EGL_NO_CONTEXT, null);
            eglSurface = this.egl.eglCreateWindowSurface(eglDisplay,
                    eglConfig, holder, null);
            this.egl.eglMakeCurrent(eglDisplay, eglSurface,
                    eglSurface, eglContext);
            gl = (GL10) eglContext.getGL();
        }

        public void EGLDestroy() {
            if (eglSurface != null) {
                egl.eglMakeCurrent(eglDisplay, EGL10.EGL_NO_SURFACE,
                        EGL10.EGL_NO_SURFACE, EGL10.EGL_NO_CONTEXT);
                egl.eglDestroySurface(eglDisplay, eglSurface);
                eglSurface = null;
            }
            if (eglContext != null) {
                egl.eglDestroyContext(eglDisplay, eglContext);
                eglContext = null;
            }
            if (eglDisplay != null) {
                egl.eglTerminate(eglDisplay);
                eglDisplay = null;
            }
        }
    }
}


