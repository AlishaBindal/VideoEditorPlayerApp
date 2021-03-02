package com.example.videoselectorapp.base.view;

/**
 * Base View
 */
public interface BaseView {

    /**
     * Gets layout.
     *
     * @return the layout
     */
    int getLayout();

    /**
     * Checks if network connection is available
     *
     * @return if connectivity is available
     */
    boolean isNetworkConnected();

    /**
     * Show loader
     */
    void showLoading();

    /**
     * Show loading.
     *
     * @param messageId the message id
     */
    void showLoading(int messageId);

    /**
     * Show loading.
     *
     * @param message the message
     */
    void showLoading(String message);

    /**
     * Hide loader
     */
    void hideLoading();

    /**
     * Show error message.
     *
     * @param message the message
     */
    void showErrorMessage(int message);

    /**
     * Show error message.
     *
     * @param message                the message
     * @param mOnErrorHandleCallback the m on error handle callback
     */
    void showErrorMessage(int message, OnErrorHandleCallback mOnErrorHandleCallback);

    /**
     * Show error message.
     *
     * @param message the message
     */
    void showErrorMessage(String message);

    /**
     * Show error message.
     *
     * @param message                the message
     * @param mOnErrorHandleCallback the m on error handle callback
     */
    void showErrorMessage(String message, OnErrorHandleCallback mOnErrorHandleCallback);

    /**
     * Show dialog.
     *
     * @param message                the message
     * @param mOnErrorHandleCallback the m on error handle callback
     */
    void showDialog(String message, OnErrorHandleCallback mOnErrorHandleCallback);

    /**
     * Show dialog.
     *
     * @param title                  the title
     * @param message                the message
     * @param mOnErrorHandleCallback the m on error handle callback
     */
    void showDialog(String title, String message, OnErrorHandleCallback mOnErrorHandleCallback);


    /**
     * Show toast.
     *
     * @param messageResId the message res id
     */
    void showToast(int messageResId);

    /**
     * Show toast.
     *
     * @param message the message
     */
    void showToast(String message);

    /**
     * Parse throwable message int.
     *
     * @param cause the cause
     * @return the int
     */
    int parseThrowableMessage(Throwable cause);


    /**
     * Gets device name.
     *
     * @return the device name
     */
    String getDeviceName();

    /**
     * Check network connected and show error boolean.
     *
     * @return the boolean
     */
    boolean checkNetworkConnectedAndShowError();

    /**
     * Restart app.
     */
    void restartApp();

    /**
     * on logout
     */
    void logout();

    /**
     * on Session Expire
     */
    void onSessionExpire();

    /**
     * The interface On error handle callback.
     */
    interface OnErrorHandleCallback {
        /**
         * On error callback.
         */
        void onErrorCallback();
    }
}
