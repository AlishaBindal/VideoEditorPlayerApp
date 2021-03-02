package com.example.videoselectorapp.ui.home;

import com.example.videoselectorapp.base.interactor.BaseInteractor;
import com.example.videoselectorapp.data.entity.fetchMediaResponse.FetchMediaResponse;
import com.example.videoselectorapp.data.network.ApiError;

import java.io.File;


/**
 * Home Interactor
 */
public interface HomeInteractor extends BaseInteractor {

    /**
     * fetchMediaList
     *
     * @param mApiListener the m api listener
     */
    void fetchMediaList(FetchMediaListListener mApiListener);

    /**
     * uploadMediaToServer
     *
     * @param listener the m api listener
     */
    void uploadMediaToServer(File videoFile, String thumbnail, ApiListener listener);

    /**
     * The interface Api listener.
     */
    interface FetchMediaListListener {
        /**
         * On success.
         *
         * @param mediaResponse mediaResponse
         */
        void onSuccess(FetchMediaResponse mediaResponse);

        /**
         * On failure.
         *
         * @param apiError  the api error
         * @param throwable the throwable
         */
        void onFailure(ApiError apiError, Throwable throwable);
    }
}
