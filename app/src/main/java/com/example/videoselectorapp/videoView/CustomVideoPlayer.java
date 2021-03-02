package com.example.videoselectorapp.videoView;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.content.res.TypedArray;
import android.net.Uri;
import android.os.Build;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleObserver;
import androidx.lifecycle.OnLifecycleEvent;

import com.example.videoselectorapp.R;
import com.google.android.exoplayer2.DefaultLoadControl;
import com.google.android.exoplayer2.DefaultRenderersFactory;
import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.extractor.DefaultExtractorsFactory;
import com.google.android.exoplayer2.source.ExtractorMediaSource;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.ui.PlayerControlView;
import com.google.android.exoplayer2.ui.PlayerView;
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory;

/**
 * Custom Video Player
 */
public class CustomVideoPlayer extends FrameLayout implements LifecycleObserver {

    private CharSequence videoLink;
    private PlayerView playerView;
    private FrameLayout viewContainer;
    private Context mContext;
    private SimpleExoPlayer player;
    private boolean playWhenReady = false;
    private int currentWindow;
    private long playbackPosition;
    private Dialog mFullScreenDialog;
    private ImageView mFullScreenIcon;


    /**
     * Instantiates a new Custom video player.
     *
     * @param context the context
     */
    public CustomVideoPlayer(@NonNull final Context context) {
        super(context);
        initView(context, null);
    }


    /**
     * Instantiates a new Custom video player.
     *
     * @param context the context
     * @param attrs   the attrs
     */
    public CustomVideoPlayer(@NonNull final Context context, @Nullable final AttributeSet attrs) {
        super(context, attrs);
        initView(context, attrs);
    }


    /**
     * Instantiates a new Custom video player.
     *
     * @param context      the context
     * @param attrs        the attrs
     * @param defStyleAttr the def style attr
     */
    public CustomVideoPlayer(@NonNull final Context context, @Nullable final AttributeSet attrs, final int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(context, attrs);
    }


    /**
     * Instantiates a new Custom video player.
     *
     * @param context      the context
     * @param attrs        the attrs
     * @param defStyleAttr the def style attr
     * @param defStyleRes  the def style res
     */
    public CustomVideoPlayer(@NonNull final Context context, @Nullable final AttributeSet attrs, final int defStyleAttr, final int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        initView(context, attrs);
    }


    /**
     * Init view.
     *
     * @param context      the context
     * @param attributeSet the attribute set
     */
    private void initView(final Context context, final AttributeSet attributeSet) {
        TypedArray typedArray = context.getTheme().obtainStyledAttributes(attributeSet, R.styleable.CustomVideoPlayer, 0, 0);
        videoLink = typedArray.getString(R.styleable.CustomVideoPlayer_video_link);
        typedArray.recycle();
        initView(context);
    }

    /**
     * Init view.
     *
     * @param context the context
     */
    private void initView(final Context context) {
        mContext = context;
        View parentView = inflate(context, R.layout.layout_custom_video_player, this);
        if (!TextUtils.isEmpty(videoLink)) {
            ((AppCompatActivity) context).getLifecycle().addObserver(this);
        }
        playerView = parentView.findViewById(R.id.playerView);
        PlayerControlView controlView = playerView.findViewById(R.id.exo_controller);
        mFullScreenIcon = controlView.findViewById(R.id.exo_fullscreen_icon);
        mFullScreenDialog = new Dialog(mContext, android.R.style.Theme_Black_NoTitleBar_Fullscreen) {
            public void onBackPressed() {
                super.onBackPressed();
            }
        };
        viewContainer = parentView.findViewById(R.id.viewContainer);
    }

    /**
     * Change config on button click.
     *
     * @param context the context
     */
    private void changeConfigOnButtonClick(final Context context) {
        final int orientation = mContext.getResources().getConfiguration().orientation;
        switch (orientation) {
            case Configuration.ORIENTATION_PORTRAIT:
                ((Activity) context).setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_SENSOR_LANDSCAPE);
                break;
            case Configuration.ORIENTATION_LANDSCAPE:
                ((Activity) context).setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_SENSOR_PORTRAIT);
                break;
            default:
                break;
        }
    }

    /**
     * Set video link.
     *
     * @param vLink the v link
     */
    public void setVideoLink(@NonNull final String vLink) {
        videoLink = vLink;
        ((FragmentActivity) mContext).getLifecycle().addObserver(this);
    }


    /**
     * Start.
     */
    @OnLifecycleEvent(Lifecycle.Event.ON_START)
    void start() {
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.M) {
            initializePlayer();
        }
    }

    /**
     * Resume.
     */
    @OnLifecycleEvent(Lifecycle.Event.ON_RESUME)
    void resume() {
        if (Build.VERSION.SDK_INT <= Build.VERSION_CODES.M || player == null) {
            initializePlayer();
        }
    }

    /**
     * Pause.
     */
    @OnLifecycleEvent(Lifecycle.Event.ON_PAUSE)
    void pause() {
        if (Build.VERSION.SDK_INT <= Build.VERSION_CODES.M) {
            releasePlayer();
        }
    }

    @Override
    public void onConfigurationChanged(final Configuration newConfig) {
        Context context = getContext();
        if (context == null || TextUtils.isEmpty(videoLink)) {
            return;
        }
        super.onConfigurationChanged(newConfig);
        final int orientation = newConfig.orientation;
        switch (orientation) {
            case Configuration.ORIENTATION_PORTRAIT:
                ((ViewGroup) playerView.getParent()).removeView(playerView);
                mFullScreenDialog.dismiss();
                ((FrameLayout) findViewById(R.id.viewContainer)).addView(playerView);
                mFullScreenIcon.setImageResource(R.drawable.ic_fullscreen);
                break;
            case Configuration.ORIENTATION_LANDSCAPE:
                mFullScreenIcon.setImageResource(R.drawable.ic_fullscreen_exit);
                ((ViewGroup) playerView.getParent()).removeView(playerView);
                mFullScreenDialog.addContentView(playerView, new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                        ViewGroup.LayoutParams.MATCH_PARENT));
                mFullScreenDialog.show();
                break;
            default:
                break;
        }
    }

    /**
     * Stop.
     */
    @OnLifecycleEvent(Lifecycle.Event.ON_STOP)
    void stop() {
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.M) {
            releasePlayer();
        }
    }

    /**
     * Build media source media source.
     *
     * @param uri the uri
     * @return the media source
     */
    private MediaSource buildMediaSource(final Uri uri) {
        return new ExtractorMediaSource.Factory(new DefaultDataSourceFactory(mContext, "ua"))
                .setExtractorsFactory(new DefaultExtractorsFactory())
                .createMediaSource(uri);
    }

    /**
     * Initialize player.
     */
    public void initializePlayer() {
        if (TextUtils.isEmpty(videoLink)) {
            viewContainer.setVisibility(GONE);
            return;
        }
        viewContainer.setVisibility(VISIBLE);
        player = ExoPlayerFactory.newSimpleInstance(mContext,
                new DefaultRenderersFactory(mContext),
                new DefaultTrackSelector(), new DefaultLoadControl());
        playerView.setPlayer(player);
        MediaSource mediaSource = buildMediaSource(Uri.parse(videoLink.toString()));

        if (player != null) {
            player.prepare(mediaSource, true, false);
            player.setPlayWhenReady(playWhenReady);
            player.seekTo(currentWindow, playbackPosition);
        }
    }

    /**
     * Release player.
     */
    public void releasePlayer() {
        if (TextUtils.isEmpty(videoLink)) {
            return;
        }
        if (player != null) {
            playbackPosition = player.getCurrentPosition();
            currentWindow = player.getCurrentWindowIndex();
            playWhenReady = player.getPlayWhenReady();
            player.release();
            player = null;
        }
    }


    /**
     * Start player.
     *
     * @param mVideoPlayerData the m video player data
     */
    public void startPlayer(final VideoPlayerData mVideoPlayerData) {
        if (player != null) {
            if (mVideoPlayerData != null) {
                player.setPlayWhenReady(mVideoPlayerData.isPlayWhenReady());
                player.seekTo(mVideoPlayerData.getCurrentWindow(),
                        mVideoPlayerData.getPlaybackPosition());
            } else {
                player.setPlayWhenReady(false);
            }
            player.getPlaybackState();
        }
    }


    /**
     * Sets auto play status.
     *
     * @param status the status
     */
    public void setAutoPlayStatus(final boolean status) {
        this.playWhenReady = status;
    }


    /**
     * Pause player.
     *
     * @return the video player data
     */
    public VideoPlayerData pausePlayer() {
        if (player != null) {
            VideoPlayerData mVideoPlayerData = new VideoPlayerData();
            mVideoPlayerData.setCurrentWindow(player.getCurrentWindowIndex());
            mVideoPlayerData.setPlaybackPosition(player.getCurrentPosition());
            mVideoPlayerData.setPlayWhenReady(player.getPlayWhenReady());
            player.setPlayWhenReady(false);
            player.getPlaybackState();
            return mVideoPlayerData;
        }
        return null;
    }

    /**
     * Is player playing boolean.
     *
     * @return the boolean
     */
    public boolean isPlayerPlaying() {
        if (player != null) {
            return player.getPlayWhenReady();
        }
        return false;
    }
}

