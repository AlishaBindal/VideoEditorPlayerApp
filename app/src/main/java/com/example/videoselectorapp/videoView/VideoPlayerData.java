package com.example.videoselectorapp.videoView;

/**
 * VideoPlayerData
 **/
public class VideoPlayerData {
    private boolean playWhenReady = false;
    private int currentWindow;
    private long playbackPosition;


    /**
     * Is play when ready boolean.
     *
     * @return the boolean
     */
    public boolean isPlayWhenReady() {
        return playWhenReady;
    }

    /**
     * Sets play when ready.
     *
     * @param playWhenReady the play when ready
     */
    public void setPlayWhenReady(final boolean playWhenReady) {
        this.playWhenReady = playWhenReady;
    }

    /**
     * Gets current window.
     *
     * @return the current window
     */
    public int getCurrentWindow() {
        return currentWindow;
    }

    /**
     * Sets current window.
     *
     * @param currentWindow the current window
     */
    public void setCurrentWindow(final int currentWindow) {
        this.currentWindow = currentWindow;
    }

    /**
     * Gets playback position.
     *
     * @return the playback position
     */
    public long getPlaybackPosition() {
        return playbackPosition;
    }

    /**
     * Sets playback position.
     *
     * @param playbackPosition the playback position
     */
    public void setPlaybackPosition(final long playbackPosition) {
        this.playbackPosition = playbackPosition;
    }
}
