package com.example.videoselectorapp.videoView;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;

import com.example.videoselectorapp.R;
import com.example.videoselectorapp.ui.home.HomeActivity;

import life.knowledge4.videotrimmer.K4LVideoTrimmer;
import life.knowledge4.videotrimmer.interfaces.OnTrimVideoListener;

/**
 * TrimmerActivity to trim the video
 */
public class TrimmerActivity extends AppCompatActivity implements OnTrimVideoListener {

    private K4LVideoTrimmer mVideoTrimmer;
    private ProgressDialog mProgressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trimmer);

        Intent extraIntent = getIntent();
        String path = "";

        if (extraIntent != null) {
            path = extraIntent.getStringExtra(HomeActivity.EXTRA_VIDEO_PATH);
        }

        //setting progressbar
        mProgressDialog = new ProgressDialog(this);
        mProgressDialog.setCancelable(false);

        mVideoTrimmer = findViewById(R.id.timeLine);
        if (mVideoTrimmer != null) {
            mVideoTrimmer.setMaxDuration(10);
            mVideoTrimmer.setOnTrimVideoListener(this);
            mVideoTrimmer.setVideoURI(Uri.parse(path));
        }
    }


    @Override
    public void getResult(final Uri uri) {
        mProgressDialog.cancel();
        if (uri.getPath() != null && !uri.getPath().isEmpty()) {
            runOnUiThread(() -> Log.v("uri.getPath()", uri.getPath()));
            Intent intent = new Intent();
            intent.putExtra(HomeActivity.EXTRA_VIDEO_PATH, uri.getPath());
            setResult(RESULT_OK, intent);
            finish();
        }
    }

    @Override
    public void cancelAction() {
        mProgressDialog.cancel();
        mVideoTrimmer.destroy();
        finish();
    }

}