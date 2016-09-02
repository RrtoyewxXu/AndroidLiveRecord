package com.rrtoyewx.camerademo;

import android.content.Intent;
import android.hardware.Camera;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.Toast;

import java.io.File;

public class MainActivity extends AppCompatActivity {
    private static final int TAKE_PHOTO_REQUEST_CODE = 1;
    private static final int RECORD_VIDEO_REQUEST_CODE = 2;

    private static final String LOCAL_TAKE_PHOTO_FILE_NAME = "photo.jpg";
    private static final String LOCAL_RECORD_VIDEO_FILE_NAME = "video.mp4";

    private FrameLayout mPreviewContainer;
    private CameraPreviewLayout mCameraPreviewLayout;
    private Button mTakePicButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        mPreviewContainer = (FrameLayout) findViewById(R.id.camera_preview);
        mCameraPreviewLayout = new CameraPreviewLayout(this);
        mPreviewContainer.addView(mCameraPreviewLayout);

        mTakePicButton = (Button) findViewById(R.id.btn_take_picture);
        mTakePicButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mCameraPreviewLayout.takePicture();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_to_take_picture:

                goToTakePicture();
                break;
            case R.id.action_to_record_video:
                goToRecordVideo();
                break;
        }
        return super.onOptionsItemSelected(item);
    }


    /**
     * 通过intent跳转照相
     * extra:
     * MediaStore.EXTRA_OUTPUT : file local path
     */
    private void goToTakePicture() {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        Uri localPhotoUri = Uri.fromFile(new File(this.getExternalCacheDir(), LOCAL_TAKE_PHOTO_FILE_NAME));
        intent.putExtra(MediaStore.EXTRA_OUTPUT, localPhotoUri);

        startActivityForResult(intent, TAKE_PHOTO_REQUEST_CODE);
    }

    /**
     * 通过intent跳转录制视频
     * extra:
     * MediaStore.EXTRA_VIDEO_QUALITY : video quality 0->low, 1->high
     * MediaStore.EXTRA_OUTPUT : video local path
     * MediaStore.EXTRA_DURATION_LIMIT:video time duration
     * MediaStore.EXTRA_SIZE_LIMIT: video size
     */
    private void goToRecordVideo() {
        Intent intent = new Intent(MediaStore.ACTION_VIDEO_CAPTURE);
        Uri localVideoUri = Uri.fromFile(new File(this.getExternalCacheDir(), LOCAL_RECORD_VIDEO_FILE_NAME));
        intent.putExtra(MediaStore.EXTRA_OUTPUT, localVideoUri);
        intent.putExtra(MediaStore.EXTRA_VIDEO_QUALITY, 1);
        intent.putExtra(MediaStore.EXTRA_DURATION_LIMIT, 8000);
        intent.putExtra(MediaStore.EXTRA_SIZE_LIMIT, 20 * 1024 * 1024);

        startActivityForResult(intent, RECORD_VIDEO_REQUEST_CODE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case TAKE_PHOTO_REQUEST_CODE:
                    handlePhotoResult(data);
                    break;
                case RECORD_VIDEO_REQUEST_CODE:
                    handleVideoResult(data);
                    break;
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    /**
     * 处理photo的结果
     *
     * @param intent return result
     */
    private void handlePhotoResult(Intent intent) {
        Toast.makeText(this, "take photo success", Toast.LENGTH_SHORT).show();
    }

    /**
     * 处理video的结果
     *
     * @param intent return result
     */
    private void handleVideoResult(Intent intent) {
        Toast.makeText(this, "record video success", Toast.LENGTH_SHORT).show();
    }
}
