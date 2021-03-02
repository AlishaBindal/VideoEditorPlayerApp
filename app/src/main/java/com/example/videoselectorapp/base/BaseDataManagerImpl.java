package com.example.videoselectorapp.base;


import com.example.videoselectorapp.data.db.BaseDbHelper;
import com.example.videoselectorapp.data.db.BaseDbHelperImpl;

import java.util.ArrayList;

/**
 * Base Data Manager Impl
 **/
public class BaseDataManagerImpl implements BaseDataManager {
    private final BaseDbHelper mBaseDbHelper;

    /**
     * Instantiates a new Data manager.
     */
    public BaseDataManagerImpl() {
        this.mBaseDbHelper = new BaseDbHelperImpl();
    }

    @Override
    public String getAccessToken() {
        return mBaseDbHelper.getAccessToken();
    }

    @Override
    public void saveAccessToken(final String accessToken) {
        mBaseDbHelper.saveAccessToken(accessToken);
    }

    @Override
    public void clearSessionManager() {
        mBaseDbHelper.clearSessionManager();
    }


    @Override
    public void saveRecentVideoList(final ArrayList<String> searchList) {
        mBaseDbHelper.saveRecentVideoList(searchList);
    }

    @Override
    public ArrayList<String> getRecentVideoList() {
        return mBaseDbHelper.getRecentVideoList();
    }

}
