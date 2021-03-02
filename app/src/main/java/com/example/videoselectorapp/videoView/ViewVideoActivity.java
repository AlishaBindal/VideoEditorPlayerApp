package com.example.videoselectorapp.videoView;

import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;

import com.example.videoselectorapp.R;
import com.example.videoselectorapp.base.view.BaseActivity;


/**
 * ViewVideoActivity
 **/
public class ViewVideoActivity extends BaseActivity {
    private static final String EXTRA_VIDEO_URL = "extra_video_url";

    /**
     * Open activity.
     *
     * @param mContext the m context
     * @param videoUrl the video url
     */
    public static void openActivity(final Context mContext,
                                    final String videoUrl) {
        Intent intent = new Intent(mContext, ViewVideoActivity.class);
        intent.putExtra(EXTRA_VIDEO_URL, videoUrl);
        mContext.startActivity(intent);
    }

    @Override
    public int getLayout() {
        return R.layout.activity_view_video;
    }

    @Override
    protected void initView() {
        super.initView();
        CustomVideoPlayer cvp = findViewById(R.id.cvp);
        Intent intent = getIntent();
        if (!TextUtils.isEmpty(intent.getStringExtra(EXTRA_VIDEO_URL))) {
            cvp.setVideoLink(intent.getStringExtra(EXTRA_VIDEO_URL));
        }
        findViewById(R.id.ivClose).setOnClickListener(v -> {
            finish();
        });
    }
}
