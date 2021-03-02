package com.example.videoselectorapp.ui.home;

import android.util.Log;

import androidx.annotation.NonNull;

import com.example.videoselectorapp.base.interactor.BaseInteractor;
import com.example.videoselectorapp.base.presenter.BasePresenterImpl;
import com.example.videoselectorapp.data.entity.CommonResponse;
import com.example.videoselectorapp.data.entity.fetchMediaResponse.FetchMediaResponse;
import com.example.videoselectorapp.data.network.ApiError;

import java.io.File;

import static com.example.videoselectorapp.data.network.AppConstant.SESSION_EXPIRED;


/**
 * Home Presenter Impl
 */
public class HomePresenterImpl extends BasePresenterImpl implements HomePresenter {

    private final HomeView mHomeView;
    private final HomeInteractor mHomeInteractor;

    HomePresenterImpl(final String deviceName, @NonNull HomeView homeView) {
        super(deviceName);
        mHomeView = homeView;
        mHomeInteractor = new HomeInteractorImpl();

    }

    @Override
    public void fetchMediaListFromServer() {
        if (isViewAttached()) {
            mHomeView.showShimmerLoading();
        }
        mHomeInteractor.fetchMediaList(new HomeInteractor.FetchMediaListListener() {
            @Override
            public void onSuccess(FetchMediaResponse mediaResponse) {
                if (isViewAttached()) {
                    mHomeView.hideShimmerLoading();
                    mHomeView.onFetchMediaApiSuccess(mediaResponse);
                }
            }

            @Override
            public void onFailure(ApiError apiError, Throwable throwable) {
                if (isViewAttached()) {
                    mHomeView.hideShimmerLoading();
                    if (apiError != null) {
                        mHomeView.showErrorMessage(apiError.getMessage(), () -> {
                            if (apiError.getStatusCode() == SESSION_EXPIRED) {
                                mHomeView.restartApp();
                            }
                        });
                    } else if (throwable != null) {
                        mHomeView.showErrorMessage(throwable.getMessage());
                        if (throwable.getCause() != null)
                            Log.v("error", throwable.getCause().getMessage() + " " + throwable.getCause().getLocalizedMessage());
                    }
                }
            }
        });
    }

    @Override
    public void uploadMediaToServer(final File videoFile, final String thumbnail) {
        if (isViewAttached()) {
            mHomeView.showShimmerLoading();
        }
        mHomeInteractor.uploadMediaToServer(videoFile, thumbnail, new BaseInteractor.ApiListener() {
            @Override
            public void onSuccess(CommonResponse mediaResponse) {
                if (isViewAttached()) {
                    mHomeView.hideShimmerLoading();
                    mHomeView.onUploadMedia(videoFile);
                }
            }

            @Override
            public void onFailure(ApiError apiError, Throwable throwable) {
                if (isViewAttached()) {
                    mHomeView.hideShimmerLoading();
                    if (apiError != null) {
                        mHomeView.showErrorMessage(apiError.getMessage(), () -> {
                            if (apiError.getStatusCode() == SESSION_EXPIRED) {
                                mHomeView.restartApp();
                            }
                        });
                    } else if (throwable != null) {
                        mHomeView.showErrorMessage(throwable.getMessage());
                        if (throwable.getCause() != null)
                            Log.v("error", throwable.getCause().getMessage() + " " + throwable.getCause().getLocalizedMessage());
                    }
                }
            }
        });
    }
}
