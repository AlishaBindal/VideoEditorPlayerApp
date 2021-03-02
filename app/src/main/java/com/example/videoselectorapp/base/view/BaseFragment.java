package com.example.videoselectorapp.base.view;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import org.jetbrains.annotations.NotNull;

/**
 * Base Fragment
 */
public class BaseFragment extends Fragment implements BaseView, FragmentToActivityInteractionListener {
    private Context mContext;
    private FragmentToActivityInteractionListener mFragmentToActivityInteractionListener;

    @Nullable
    @Override
    public View onCreateView(final @NotNull LayoutInflater inflater, @Nullable final ViewGroup container, @Nullable final Bundle savedInstanceState) {
        if (getLayout() > 0) {
            View view = inflater.inflate(getLayout(), container, false);
            return view;
        } else {
            return super.onCreateView(inflater, container, savedInstanceState);
        }
    }

    /**
     * Init view.
     *
     * @param view the view
     */
    protected void initView(final View view) {

    }

    @Override
    public void onViewCreated(@NonNull final View view, @Nullable final Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initView(view);
        refreshData();
    }


    /**
     * to refresh data when this fragment becomes visible
     */
    public void refreshData() {
    }

    @Override
    public void onAttach(final @NotNull Context context) {
        super.onAttach(context);
        this.mContext = context;
        if (getContext() instanceof FragmentToActivityInteractionListener) {
            this.mFragmentToActivityInteractionListener = (FragmentToActivityInteractionListener) context;
        } else {
            if (getContext() != null) {
                throw new RuntimeException(getContext().getClass().getSimpleName()
                        + " must implement FragmentToActivityInteractionListener");
            }
        }
        attachComponent();
    }

    @Override
    public Context getContext() {
        return mContext;
    }


    /**
     * Attach component.
     */
    protected void attachComponent() {
    }


    @Override
    public void onDetach() {
        mContext = null;
        detachComponent();
        super.onDetach();
    }


    /**
     * Detach component.
     */
    protected void detachComponent() {
    }


    /**
     * Is back press allowed from fragment boolean.
     *
     * @return the boolean
     */
    public boolean isBackPressAllowedFromFragment() {
        return true;
    }


    @Override
    public void clearBackStack() {
        mFragmentToActivityInteractionListener.clearBackStack();
    }

    @Override
    public void backPress() {
        mFragmentToActivityInteractionListener.backPress();
    }


    @Override
    public void clearBackStack(final String backStackTag) {
        mFragmentToActivityInteractionListener.clearBackStack(backStackTag);
    }

    @Override
    public void showFragment(final String tag, final boolean isAdd, final Object... object) {
        mFragmentToActivityInteractionListener.showFragment(tag, isAdd, object);
    }

    @Override
    public void setResultOkAndCloseActivity() {
        mFragmentToActivityInteractionListener.setResultOkAndCloseActivity();
    }


    @Override
    public int getLayout() {
        return 0;
    }

    @Override
    public boolean isNetworkConnected() {
        return mFragmentToActivityInteractionListener.isNetworkConnected();
    }

    @Override
    public void showLoading() {
        mFragmentToActivityInteractionListener.showLoading();
    }

    @Override
    public void showLoading(final int messageId) {
        mFragmentToActivityInteractionListener.showLoading(messageId);
    }

    @Override
    public void showLoading(final String message) {
        mFragmentToActivityInteractionListener.showLoading(message);
    }

    @Override
    public void hideLoading() {
        mFragmentToActivityInteractionListener.hideLoading();
    }

    @Override
    public void showErrorMessage(final int message) {
        mFragmentToActivityInteractionListener.showErrorMessage(message);
    }

    @Override
    public void showErrorMessage(final int message, final OnErrorHandleCallback mOnErrorHandleCallback) {
        mFragmentToActivityInteractionListener.showErrorMessage(message, mOnErrorHandleCallback);
    }

    @Override
    public void showErrorMessage(final String message) {
        mFragmentToActivityInteractionListener.showErrorMessage(message);
    }

    @Override
    public void showErrorMessage(final String message, final OnErrorHandleCallback mOnErrorHandleCallback) {
        mFragmentToActivityInteractionListener.showErrorMessage(message, mOnErrorHandleCallback);
    }

    @Override
    public void showDialog(final String message, final OnErrorHandleCallback mOnErrorHandleCallback) {
        mFragmentToActivityInteractionListener.showDialog(message, mOnErrorHandleCallback);
    }

    @Override
    public void showDialog(final String title, final String message, final OnErrorHandleCallback mOnErrorHandleCallback) {
        mFragmentToActivityInteractionListener.showDialog(title, message, mOnErrorHandleCallback);
    }

    @Override
    public void showToast(final int messageResId) {
        mFragmentToActivityInteractionListener.showToast(messageResId);
    }

    @Override
    public void showToast(final String message) {
        mFragmentToActivityInteractionListener.showToast(message);
    }

    @Override
    public int parseThrowableMessage(final Throwable cause) {
        return mFragmentToActivityInteractionListener.parseThrowableMessage(cause);
    }

    @Override
    public String getDeviceName() {
        return mFragmentToActivityInteractionListener.getDeviceName();
    }

    @Override
    public boolean checkNetworkConnectedAndShowError() {
        return mFragmentToActivityInteractionListener.checkNetworkConnectedAndShowError();
    }

    @Override
    public void restartApp() {
    }

    @Override
    public void logout() {
    }

    @Override
    public void onSessionExpire() {
    }
}