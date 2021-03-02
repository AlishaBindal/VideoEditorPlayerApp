package com.example.videoselectorapp.data.db;

/**
 * DB table
 */
public final class Db {

    /**
     * Prevent instantiation
     */
    private Db() {
    }


    /**
     * The type Table.
     */
    public static final class Table {

        /**
         * The constant DEVICE_TOKEN.
         */
        public static final String DEVICE_TOKEN = "paper_device_token";
        /**
         * The constant ACCESS_TOKEN.
         */
        public static final String ACCESS_TOKEN = "paper_access_token";
        /**
         * RECENT_VIDEO_LIST
         */
        public static final String RECENT_VIDEO_LIST = "RECENT_VIDEO_LIST";

        /**
         * Prevent instantiation
         */
        private Table() {
        }
    }
}
