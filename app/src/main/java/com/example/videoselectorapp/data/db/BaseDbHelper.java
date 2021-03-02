package com.example.videoselectorapp.data.db;

import java.util.ArrayList;

/**
 * BaseDbHelper
 */
public interface BaseDbHelper {

    /**
     * Gets access token.
     *
     * @return the access token
     */
    String getAccessToken();

    /**
     * Save access token.
     *
     * @param accessToken the access token
     */
    void saveAccessToken(String accessToken);

    /**
     * Clear session manager.
     */
    void clearSessionManager();

    /**
     * save Recent video list
     *
     * @param videoList videoList
     */
    void saveRecentVideoList(ArrayList<String> videoList);

    /**
     * get recent video List
     *
     * @return recent  video list
     */
    ArrayList<String> getRecentVideoList();
}
