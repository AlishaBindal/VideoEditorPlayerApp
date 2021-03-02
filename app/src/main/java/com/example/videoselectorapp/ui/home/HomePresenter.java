package com.example.videoselectorapp.ui.home;


import com.example.videoselectorapp.base.presenter.BasePresenter;

import java.io.File;

/**
 * Home Presenter
 */
public interface HomePresenter extends BasePresenter {

    /**
     * fetch Media List From Server
     */
    void fetchMediaListFromServer();

    /**
     * uploadMediaToServer
     * @param videoFile file
     * @param thumbnail thumbnail
     */
    void uploadMediaToServer(File videoFile, String thumbnail);
}
