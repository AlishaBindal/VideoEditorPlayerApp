package com.example.videoselectorapp.ui.home;

import com.example.videoselectorapp.base.view.BaseView;
import com.example.videoselectorapp.data.entity.fetchMediaResponse.FetchMediaResponse;

import java.io.File;

public interface HomeView extends BaseView {

    void showShimmerLoading();

    void hideShimmerLoading();

    void onFetchMediaApiSuccess(FetchMediaResponse mediaResponse);

    void onUploadMedia(File videoFile);
}
