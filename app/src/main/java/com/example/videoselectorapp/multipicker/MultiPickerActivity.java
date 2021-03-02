package com.example.videoselectorapp.multipicker;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.provider.MediaStore;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.annotation.StringDef;
import androidx.appcompat.app.AppCompatActivity;

import com.example.videoselectorapp.permission.SystemPermissionActivity;
import com.kbeanie.multipicker.api.CameraVideoPicker;
import com.kbeanie.multipicker.api.Picker;
import com.kbeanie.multipicker.api.VideoPicker;
import com.kbeanie.multipicker.api.callbacks.VideoPickerCallback;
import com.kbeanie.multipicker.api.entity.ChosenVideo;

import java.lang.annotation.Retention;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import static java.lang.annotation.RetentionPolicy.SOURCE;

/**
 * MultiPickerActivity
 */
public class MultiPickerActivity extends AppCompatActivity implements VideoPickerCallback {

    /**
     * The constant MAX_VIDEO_SIZE.
     */
    public static final long MAX_VIDEO_SIZE = 199;
    /**
     * The constant BYTE_SIZE.
     */
    public static final int BYTE_SIZE = 1024;
    /**
     * The constant REQ_CODE_CHOOSE_VIDEO.
     */
    public static final int REQ_CODE_CHOOSE_VIDEO = 1001;

    /**
     * The constant EXTRA_CHOSEN_VIDEOS.
     */
    public static final String EXTRA_CHOSEN_VIDEOS = "extra_chosen_videos";

    /**
     * The constant TYPE_VIDEO.
     */
    public static final String TYPE_VIDEO = "type_video";

    /**
     * The constant TYPE_VIDEO_DIRECTLY.
     */
    public static final String TYPE_VIDEO_DIRECTLY = "type_video_directly";

    private static final String EXTRA_PICKER_TYPE = "extra_picker_type";
    private static final String EXTRA_IMAGE_VIDEO_COUNT = "extra_image_video_count";
    private static final String EXTRA_IS_OPEN_CAMERA = "extra_is_open_camera";
    private static final String BUNDLE_PICKER_PATH = "bundle_picker_path";
    private static final String BUNDLE_VIDEO_PATH = "bundle_video_path";
    private static final int REQ_CODE_GALLERY = 102;
    private static final int REQ_CODE_CAMERA = 103;
    private VideoPicker videoPicker;
    private CameraVideoPicker mCameraVideoPicker;
    private String outputPath, videoPath;
    private boolean isSystemPermissionCalled = false;
    private Dialog mProgressDialog;

    /**
     * Open activity.
     *
     * @param mActivity       the m activity
     * @param pickerType      the picker type
     * @param isOpenCamera    isOpenCamera
     * @param imageVideoCount the image video count
     */
    public static void openActivity(final Activity mActivity,
                                    @PickerType final String pickerType,
                                    final boolean isOpenCamera,
                                    final int imageVideoCount) {
        Intent intent = new Intent(mActivity, MultiPickerActivity.class);
        intent.putExtra(EXTRA_PICKER_TYPE, pickerType);
        intent.putExtra(EXTRA_IMAGE_VIDEO_COUNT, imageVideoCount);
        intent.putExtra(EXTRA_IS_OPEN_CAMERA, isOpenCamera);
        int reqCode = REQ_CODE_CHOOSE_VIDEO;
        switch (pickerType) {
            case TYPE_VIDEO:
                reqCode = REQ_CODE_CHOOSE_VIDEO;
                break;
            case TYPE_VIDEO_DIRECTLY:
                reqCode = REQ_CODE_CHOOSE_VIDEO;
                break;
            default:
                break;
        }
        mActivity.startActivityForResult(intent, reqCode);
    }

    @Override
    protected void onCreate(@Nullable final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        init();
    }

    /**
     * Init.
     */
    private void init() {
        int mImageVideoCount = getIntent().getIntExtra(EXTRA_IMAGE_VIDEO_COUNT, 0);
        boolean isOpenCamera = getIntent().getBooleanExtra(EXTRA_IS_OPEN_CAMERA, true);

        videoPicker = new VideoPicker(this);
        videoPicker.shouldGenerateMetadata(false);
        videoPicker.shouldGeneratePreviewImages(false);
        videoPicker.setVideoPickerCallback(this);

        mCameraVideoPicker = new CameraVideoPicker(this);
        mCameraVideoPicker.shouldGenerateMetadata(false);
        mCameraVideoPicker.shouldGeneratePreviewImages(false);
        mCameraVideoPicker.setVideoPickerCallback(this);

        Bundle extras = new Bundle();
        extras.putInt(MediaStore.EXTRA_VIDEO_QUALITY, 0);
        extras.putInt(MediaStore.EXTRA_SIZE_LIMIT, BYTE_SIZE);

        mCameraVideoPicker.setExtras(extras);
        if (mImageVideoCount > 1) {
            videoPicker.allowMultiple();
        }

        if (isOpenCamera) {
            onClickCamera();
        } else {
            onClickGallery();
        }
    }

    @Override
    protected void attachBaseContext(final Context newBase) {
//        Context mBase = ViewPumpContextWrapper.wrap(newBase);
        super.attachBaseContext(newBase);
    }


    @Override
    protected void onSaveInstanceState(final Bundle outState) {
        // You have to save path in case your activity is killed.
        // In such a scenario, you will need to re-initialize the CameraImagePicker
        outState.putString(BUNDLE_PICKER_PATH, outputPath);
        outState.putString(BUNDLE_VIDEO_PATH, videoPath);
        super.onSaveInstanceState(outState);
    }

    @Override
    protected void onRestoreInstanceState(final Bundle savedInstanceState) {
        // After Activity recreate, you need to re-initialize these
        // two values to be able to re-initialize CameraImagePicker
        if (savedInstanceState != null) {
            if (savedInstanceState.containsKey(BUNDLE_PICKER_PATH)) {
                outputPath = savedInstanceState.getString(BUNDLE_PICKER_PATH);
            }
            if (savedInstanceState.containsKey(BUNDLE_VIDEO_PATH)) {
                videoPath = savedInstanceState.getString(BUNDLE_VIDEO_PATH);
            }
        }
        super.onRestoreInstanceState(savedInstanceState);
    }

    private void onClickCamera() {
        if (!isSystemPermissionCalled) {
            isSystemPermissionCalled = true;
            startActivityForResult(SystemPermissionActivity.createIntent(MultiPickerActivity.this,
                    SystemPermissionActivity.CAMERA), REQ_CODE_CAMERA);
        }
    }

    private void onClickGallery() {
        if (!isSystemPermissionCalled) {
            isSystemPermissionCalled = true;
            startActivityForResult(SystemPermissionActivity.createIntent(MultiPickerActivity.this,
                    SystemPermissionActivity.GALLERY), REQ_CODE_GALLERY);
        }
    }


    @Override
    protected void onActivityResult(final int requestCode, final int resultCode, final Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        String type = getIntent().getStringExtra(EXTRA_PICKER_TYPE);
        switch (requestCode) {
            case Picker.PICK_VIDEO_DEVICE:
                if (resultCode == RESULT_OK) {
                    if (videoPicker == null) {
                        videoPicker = new VideoPicker(MultiPickerActivity.this);
                        videoPicker.setVideoPickerCallback(MultiPickerActivity.this);
                    }
                    videoPicker.submit(data);
                } else {
                    finish();
                }
                break;
            case Picker.PICK_VIDEO_CAMERA:
                if (resultCode == RESULT_OK) {
                    if (mCameraVideoPicker == null) {
                        mCameraVideoPicker = new CameraVideoPicker(MultiPickerActivity.this, videoPath);
                        mCameraVideoPicker.setVideoPickerCallback(MultiPickerActivity.this);
                    }
                    mCameraVideoPicker.submit(data);
                } else if (type.equals(TYPE_VIDEO_DIRECTLY)) {
                    finish();
                }
                break;
            default:
                break;
        }
        onResult(requestCode, resultCode, data);
    }

    /**
     * On result.
     *
     * @param requestCode the request code
     * @param resultCode  the result code
     * @param data        the data
     */
    protected void onResult(final int requestCode, final int resultCode, final Intent data) {
        String type = getIntent().getStringExtra(EXTRA_PICKER_TYPE);
        switch (requestCode) {
            case REQ_CODE_GALLERY:
                isSystemPermissionCalled = false;
                if (resultCode == RESULT_OK) {
                    if (TYPE_VIDEO.equals(type)) {
                        videoPicker.pickVideo();
                    }
                } else if (type.equals(TYPE_VIDEO)) {
                    finish();
                }
                break;
            case REQ_CODE_CAMERA:
                isSystemPermissionCalled = false;
                if (resultCode == RESULT_OK) {
                    switch (type) {
                        case TYPE_VIDEO:
                        case TYPE_VIDEO_DIRECTLY:
                            videoPath = mCameraVideoPicker.pickVideo();
                            break;
                        default:
                            break;
                    }
                } else if (type.equals(TYPE_VIDEO_DIRECTLY)) {
                    finish();
                }
                break;
            default:
                break;
        }
    }

    @Override
    public void onVideosChosen(final List<ChosenVideo> list) {
        for (ChosenVideo chosenFile : list) {
            chosenFile.setCreatedAt(Calendar.getInstance().getTime());
        }

        //in mb
        long videoSize = list.get(0).getSize() / (BYTE_SIZE * BYTE_SIZE);

        Log.e("Video size", videoSize + "");

        if (videoSize > MAX_VIDEO_SIZE) {
            onError("Video size is too large.");
        }

        Intent intent = getIntent();
        intent.putParcelableArrayListExtra(EXTRA_CHOSEN_VIDEOS, (ArrayList<? extends Parcelable>) list);
        setResult(RESULT_OK, intent);
        finish();
    }


    @Override
    public void onError(final String errorMessage) {
        Toast.makeText(this, errorMessage, Toast.LENGTH_LONG).show();
        String type = getIntent().getStringExtra(EXTRA_PICKER_TYPE);
        if (type.equals(TYPE_VIDEO_DIRECTLY) || type.equals(TYPE_VIDEO)) {
            finish();
        }
    }

    @Override
    public void onBackPressed() {
        onBackPressed();
    }

    /**
     * The interface Picker type.
     */
    @Retention(SOURCE)
    @StringDef({TYPE_VIDEO, TYPE_VIDEO_DIRECTLY})
    public @interface PickerType {
    }

}