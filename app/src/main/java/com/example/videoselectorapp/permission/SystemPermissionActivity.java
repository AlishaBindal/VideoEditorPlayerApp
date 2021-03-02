package com.example.videoselectorapp.permission;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.provider.Settings;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.IntDef;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.core.app.ActivityCompat;

import com.example.videoselectorapp.R;
import com.example.videoselectorapp.base.view.BaseActivity;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * SystemPermissionActivity
 */
public class SystemPermissionActivity extends BaseActivity {
    /**
     * The constant GALLERY.
     */
    public static final int GALLERY = 0;

    /**
     * The constant CAMERA.
     */
    public static final int CAMERA = 1;


    private static final int PERMISSION_REQ_CODE = 100;
    private static final int REQUEST_CODE_APP_SETTING = 101;
    private static final int REQUEST_CODE_CHECK_LOCATION_SETTINGS = 102;
    private static final String[] WRITE_EXTERNAL_STORAGE_PERMISSION = {Manifest.permission.WRITE_EXTERNAL_STORAGE};

    private static final String[] CAMERA_PERMISSION = {Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE};
    private static final String EXTRA_PERMISSION_TYPE = "extra_permission_type";
    private static final String EXTRA_FIRST_NAME = "extra_first_name";
    private String[] permissions;
    private String dialogTitle;
    private String dialogMessage;

    /**
     * Create intent intent.z
     *
     * @param mContext       the m context
     * @param permissionType the permission type
     * @return the intent
     */
    public static Intent createIntent(final Context mContext, @PermissionType final int permissionType) {
        return createIntent(mContext, permissionType, "");
    }

    /**
     * Create intent intent.
     *
     * @param mContext       the m context
     * @param permissionType the permission type
     * @param firstName      the first name
     * @return the intent
     */
    public static Intent createIntent(final Context mContext, @PermissionType final int permissionType,
                                      @NonNull final String firstName) {
        Intent intent = new Intent(mContext, SystemPermissionActivity.class);
        intent.putExtra(EXTRA_PERMISSION_TYPE, permissionType);
        intent.putExtra(EXTRA_FIRST_NAME, firstName);
        return intent;
    }

    @Override
    public int getLayout() {
        return R.layout.activity_system_permission;
    }

    @Override
    protected void initView() {
        super.initView();
        init();
    }

    /**
     * Init.
     */
    private void init() {
        ImageView ivInfoIcon = findViewById(R.id.ivInfoIcon);
        TextView tvHeading = findViewById(R.id.tvHeading);
        TextView tvSubHeading = findViewById(R.id.tvSubHeading);

        final int permissionType = getIntent().getIntExtra(EXTRA_PERMISSION_TYPE, 1);
        switch (permissionType) {
            case GALLERY:
                ivInfoIcon.setImageResource(R.drawable.ic_gallery);
                tvHeading.setText(R.string.text_permission_info_heading_gallery);
                tvSubHeading.setText(R.string.text_permission_info_sub_heading_gallery);
                permissions = WRITE_EXTERNAL_STORAGE_PERMISSION;
                dialogTitle = getString(R.string.text_enable_camera_roll);
                dialogMessage = getString(R.string.text_enable_camera_roll_msg);
                break;
            case CAMERA:
                ivInfoIcon.setImageResource(R.drawable.ic_camera);
                tvHeading.setText(R.string.text_permission_info_heading_camera);
                tvSubHeading.setText(R.string.text_permission_info_sub_heading_camera);
                permissions = CAMERA_PERMISSION;
                dialogTitle = getString(R.string.text_enable_camera);
                dialogMessage = getString(R.string.text_enable_camera_msg);
                break;

            default:
                setResult(RESULT_CANCELED, getIntent());
                finish();
                break;
        }
        checkAndRequestPermissions(false);

        findViewById(R.id.ivClose).setOnClickListener(v -> {
            setResult(RESULT_CANCELED, getIntent());
            finish();
        });

        findViewById(R.id.btnContinue).setOnClickListener(v -> {
            checkAndRequestPermissions(true);
        });
    }


    /**
     * Check and request permissions.
     *
     * @param showRequestDialog the show request dialog
     */
    private void checkAndRequestPermissions(final boolean showRequestDialog) {
        if (PermissionUtil.hasPermissions(this, permissions)) {
            setResult(RESULT_OK, getIntent());
            finish();
        } else {
            if (showRequestDialog) {
                ActivityCompat.requestPermissions(SystemPermissionActivity.this, permissions, PERMISSION_REQ_CODE);
            }
        }

    }

    @Override
    public void onRequestPermissionsResult(final int requestCode, @NonNull final String[] mPermissions,
                                           @NonNull final int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, mPermissions, grantResults);
        if (requestCode == PERMISSION_REQ_CODE) {
            if (PermissionUtil.verifyPermissions(grantResults)) {
                setResult(RESULT_OK, getIntent());
                finish();
            } else {
                showAppSettingsDialog();
            }
        }
    }

    /**
     * Show app settings dialog.
     */
    private void showAppSettingsDialog() {
        new AlertDialog.Builder(this)
                .setTitle(dialogTitle)
                .setMessage(dialogMessage)
                .setPositiveButton(R.string.text_go_to_settings, (dialogInterface, i) -> {
                    startInstalledAppDetailsActivity();
                })
                .setNegativeButton(R.string.text_not_now, (dialogInterface, i) -> finish())
                .setCancelable(false)
                .show();
    }

    @Override
    public void onActivityResult(final int requestCode, final int resultCode, final Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE_APP_SETTING) {
            checkAndRequestPermissions(true);
        } else if (requestCode == REQUEST_CODE_CHECK_LOCATION_SETTINGS) {
            checkAndRequestPermissions(true);
        }
    }

    /**
     * Open app setting of installed application.
     */
    private void startInstalledAppDetailsActivity() {
        final Intent i = new Intent();
        i.setAction(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
        i.addCategory(Intent.CATEGORY_DEFAULT);
        i.setData(Uri.parse("package:" + this.getPackageName()));
        startActivityForResult(i, REQUEST_CODE_APP_SETTING);
    }

    /**
     * The interface Floating label type.
     */
    @Retention(RetentionPolicy.SOURCE)
    @IntDef({GALLERY, CAMERA})
    public @interface PermissionType {
    }
}

