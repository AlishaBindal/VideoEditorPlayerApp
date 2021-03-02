package com.example.videoselectorapp.ui.home;


import com.example.videoselectorapp.base.interactor.BaseInteractorImpl;
import com.example.videoselectorapp.data.entity.CommonResponse;
import com.example.videoselectorapp.data.entity.fetchMediaResponse.FetchMediaResponse;
import com.example.videoselectorapp.data.network.ApiError;
import com.example.videoselectorapp.data.network.MultipartParams;
import com.example.videoselectorapp.data.network.ResponseResolver;
import com.example.videoselectorapp.data.network.RestClient;

import java.io.File;

import static com.example.videoselectorapp.data.network.RetrofitUtils.getHeaderMap;

/**
 * Home Interactor Impl
 */
public class HomeInteractorImpl extends BaseInteractorImpl implements HomeInteractor {

    @Override
    public void fetchMediaList(FetchMediaListListener mApiListener) {
        RestClient.getApiInterface().getMediaList("4").enqueue(new ResponseResolver<FetchMediaResponse>() {
            @Override
            public void onSuccess(FetchMediaResponse mediaResponse) {
                mApiListener.onSuccess(mediaResponse);
            }

            @Override
            public void onError(ApiError error) {
                mApiListener.onFailure(error, null);
            }

            @Override
            public void onFailure(Throwable throwable) {
                mApiListener.onFailure(null, throwable);
            }
        });
    }

    @Override
    public void uploadMediaToServer(File videoFile, String thumbnail, ApiListener mApiListener) {
        MultipartParams multipartParams = new MultipartParams.Builder()
                .addFile("file", videoFile)
                .addFile("thumbFile", new File(thumbnail)).build();
        RestClient.getApiInterface().uploadMedia(getHeaderMap(), multipartParams.getMap()).enqueue(new ResponseResolver<CommonResponse>() {
            @Override
            public void onSuccess(CommonResponse mediaResponse) {
                mApiListener.onSuccess(mediaResponse);
            }

            @Override
            public void onError(ApiError error) {
                mApiListener.onFailure(error, null);
            }

            @Override
            public void onFailure(Throwable throwable) {
                mApiListener.onFailure(null, throwable);
            }
        });
    }
}
