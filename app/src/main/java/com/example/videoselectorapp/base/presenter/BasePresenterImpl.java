package com.example.videoselectorapp.base.presenter;

import com.example.videoselectorapp.base.BaseDataManager;
import com.example.videoselectorapp.base.BaseDataManagerImpl;
import com.example.videoselectorapp.base.view.BaseView;
import com.example.videoselectorapp.data.network.ApiError;

import static com.example.videoselectorapp.data.network.AppConstant.UNAUTHORIZED;

/**
 * Base Presenter Impl
 */
public class BasePresenterImpl implements BasePresenter {
    private BaseView mView;
    private final BaseDataManager mBaseDataManager;

    /**
     * Instantiates a new Base presenter.
     *
     * @param deviceName the device name
     */
    public BasePresenterImpl(final String deviceName) {
        mBaseDataManager = new BaseDataManagerImpl();
    }

    @Override
    public void onAttach(final BaseView view) {
        this.mView = view;
    }

    @Override
    public void onDetach() {
        mView = null;
    }

    @Override
    public void deleteLocalDb() {
        mBaseDataManager.clearSessionManager();
    }


    /**
     * Handle api error.
     *
     * @param apiError  the api error
     * @param throwable the throwable
     */
    protected void handleApiError(final ApiError apiError, final Throwable throwable) {
        handleApiError(apiError, throwable, null);
    }

    /**
     * Handle api error.
     *
     * @param mApiError              the m api error
     * @param mThrowable             the m throwable
     * @param mOnErrorHandleCallback the m on error handle callback
     */
    protected void handleApiError(final ApiError mApiError, final Throwable mThrowable,
                                  final BaseView.OnErrorHandleCallback mOnErrorHandleCallback) {
        if (mApiError != null) {
            if (mApiError.getStatusCode() == UNAUTHORIZED) {
                mView.showErrorMessage(mApiError.getMessage(), mOnErrorHandleCallback);
            } else {
                mView.showErrorMessage(mApiError.getMessage(), mOnErrorHandleCallback);

            }
        } else {
            mView.showErrorMessage(mView.parseThrowableMessage(mThrowable), mOnErrorHandleCallback);
        }
    }

    /**
     * Gets view.
     *
     * @return the view
     */
    protected BaseView getView() {
        return mView;
    }

    /**
     * Is view attached boolean.
     *
     * @return the boolean
     */
    protected boolean isViewAttached() {
        return mView != null;
    }

    /**
     * Get data manager data manager.
     *
     * @return the data manager
     */
    public BaseDataManager getDataManager() {
        return mBaseDataManager;
    }
}
