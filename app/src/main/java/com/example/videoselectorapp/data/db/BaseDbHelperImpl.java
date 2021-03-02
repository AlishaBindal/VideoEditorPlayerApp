package com.example.videoselectorapp.data.db;


import androidx.annotation.NonNull;

import java.util.ArrayList;

import io.paperdb.Book;
import io.paperdb.Paper;

/**
 * BaseDbHelperImpl
 */
public class BaseDbHelperImpl implements BaseDbHelper {

    /**
     * Instantiates a new Base db helper.
     */
    public BaseDbHelperImpl() {
    }


    @Override
    public String getAccessToken() {
        return getAppBook().read(Db.Table.ACCESS_TOKEN, null);
    }

    @Override
    public void saveAccessToken(final String accessToken) {
        getAppBook().write(Db.Table.ACCESS_TOKEN, accessToken);
    }

    @Override
    public void saveRecentVideoList(ArrayList<String> videoList) {
        getAppBook().write(Db.Table.RECENT_VIDEO_LIST, videoList);
    }

    @Override
    public ArrayList<String> getRecentVideoList() {
        return getAppBook().read(Db.Table.RECENT_VIDEO_LIST, null);
    }


    @Override
    public void clearSessionManager() {
        clearDb();
    }

    /**
     * Gets book.
     *
     * @return the book
     */
    private Book getAppBook() {
        return Paper.book();
    }

    //================================== Write in DB method ========================================

    /**
     * Write in db.
     *
     * @param <T>       the type parameter
     * @param tableName the table name
     * @param value     the value
     */
    public <T> void writeInDb(@NonNull final String tableName, final T value) {
        getAppBook().write(tableName, value);
    }

    //================================= Read from DB methods =======================================

    /**
     * Read from db t.
     *
     * @param <T>       the type parameter
     * @param tableName the table name
     * @return the t
     */
    public <T> T readFromDb(@NonNull final String tableName) {
        return getAppBook().read(tableName);
    }

    /**
     * Read from db t.
     *
     * @param <T>          the type parameter
     * @param tableName    the table name
     * @param defaultValue the default value
     * @return the t
     */
    public <T> T readFromDb(@NonNull final String tableName, final T defaultValue) {
        return getAppBook().read(tableName, defaultValue);
    }

    //================================ Delete a table from DB method ===============================

    /**
     * Delete table from db.
     *
     * @param tableName the table name
     */
    public void deleteTableFromDb(@NonNull final String tableName) {
        getAppBook().delete(tableName);
    }

    /**
     * Clear data.
     */
    public void clearDb() {
        getAppBook().destroy();
    }
}
