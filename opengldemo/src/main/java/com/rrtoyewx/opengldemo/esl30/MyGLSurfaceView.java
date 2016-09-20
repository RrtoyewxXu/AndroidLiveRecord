package com.rrtoyewx.opengldemo.esl30;

import android.content.Context;
import android.opengl.GLSurfaceView;
import android.view.MotionEvent;

/**
 * Created by Rrtoyewx on 16/9/19.
 */
public class MyGLSurfaceView extends GLSurfaceView {
    private MyRenderer renderer;
    private float mPreviousX;
    private float mPreviousY;

    public MyGLSurfaceView(Context context) {
        super(context);
        setEGLContextClientVersion(2);
        renderer = new MyRenderer();
        setRenderer(renderer);

        setRenderMode(GLSurfaceView.RENDERMODE_WHEN_DIRTY);
    }


    public boolean onTouchEvent(MotionEvent e) {
        float x = e.getX();
        float y = e.getY();

        switch (e.getAction()) {
            case MotionEvent.ACTION_MOVE:

                float dx = x - mPreviousX;
                float dy = y - mPreviousY;

                // 反向旋转中线以上
                if (y > getHeight() / 2) {
                    dx = dx * -1 ;
                }

                // 反向旋转至左中线
                if (x < getWidth() / 2) {
                    dy = dy * -1 ;
                }

                renderer.mAngle += ((dx + dy)/3) ;  // = 180.0f / 320
                requestRender();
        }

        mPreviousX = x;
        mPreviousY = y;
        return true;
    }

}
