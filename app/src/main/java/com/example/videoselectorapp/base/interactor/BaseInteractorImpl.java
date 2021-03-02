package com.example.videoselectorapp.base.interactor;


import com.example.videoselectorapp.data.db.BaseCommonData;

/**
 * Base Interactor Impl
 */
public class BaseInteractorImpl implements BaseInteractor {

    @Override
    public void clearSessionManager() {
        BaseCommonData.clearData();
    }

}
