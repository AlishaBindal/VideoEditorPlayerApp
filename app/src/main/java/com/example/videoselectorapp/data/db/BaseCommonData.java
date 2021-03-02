package com.example.videoselectorapp.data.db;


import java.util.ArrayList;

/**
 * BaseCommonData
 */
public class BaseCommonData {
    private static BaseDbHelperImpl mBaseDbHelper;

    /**
     * Empty Constructor
     * not called
     */
    private BaseCommonData() {
    }

    /**
     * Gets db.
     *
     * @return the db
     */
    private static BaseDbHelperImpl getDb() {
        if (mBaseDbHelper == null) {
            mBaseDbHelper = new BaseDbHelperImpl();
        }
        return mBaseDbHelper;
    }

    /**
     * Save access token.
     *
     * @param accessToken the access token
     */
    public static void saveAccessToken(final String accessToken) {
        getDb().saveAccessToken(accessToken);
    }

    /**
     * Gets access token.
     *
     * @return the access token
     */
    public static String getAccessToken() {
        return getDb().getAccessToken();
    }

    /**
     * Save recent videoList
     *
     * @param videoList the videoList
     */
    public static void saveRecentVideoList(final ArrayList<String> videoList) {
        getDb().saveRecentVideoList(videoList);
    }

    /**
     * Gets recent video List
     *
     * @return the recent videoList
     */
    public static ArrayList<String> getRecentVideoList() {
        return getDb().getRecentVideoList();
    }

    /**
     * Clear data.
     */
    public static void clearData() {
        getDb().clearDb();
    }

}
