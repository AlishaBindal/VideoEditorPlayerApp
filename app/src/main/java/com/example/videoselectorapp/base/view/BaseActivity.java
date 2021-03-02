package com.example.videoselectorapp.base.view;

import android.app.Dialog;
import android.os.Bundle;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.google.gson.JsonSyntaxException;
import com.example.videoselectorapp.R;
import com.example.videoselectorapp.base.presenter.BasePresenter;
import com.example.videoselectorapp.base.presenter.BasePresenterImpl;
import com.example.videoselectorapp.base.util.CommonUtil;
import com.example.videoselectorapp.ui.dialogs.CustomAlertDialog;
import com.example.videoselectorapp.ui.dialogs.ProgressDialog;

import java.net.ConnectException;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;

/**
 * Base Activity
 */
public abstract class BaseActivity extends AppCompatActivity implements BaseView, FragmentToActivityInteractionListener {
    private Dialog mDialog, mProgressDialog;
    private BasePresenter mBasePresenter;


    @Override
    protected void onCreate(@Nullable final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayout());

        mBasePresenter = new BasePresenterImpl(getDeviceName());
        mBasePresenter.onAttach(this);
        initView();
    }

    /**
     * Init view.
     */
    protected void initView() {
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    public int getLayout() {
        return 0;
    }

    /**
     * Get current fragment.
     *
     * @return the base fragment
     */
    private Fragment getCurrentFragment() {
        FragmentManager fragmentManager = getSupportFragmentManager();
        if (fragmentManager.getBackStackEntryCount() > 0) {
            String fragmentTag = fragmentManager.getBackStackEntryAt(fragmentManager.getBackStackEntryCount() - 1).getName();
            return fragmentManager.findFragmentByTag(fragmentTag);
        } else {
            return null;
        }
    }

    /**
     * Get container fragment int.
     *
     * @return the int
     */
    protected int getFragmentContainer() {
        return 0;
    }

    /**
     * Replace or add fragment.
     *
     * @param mFragment the m fragment
     * @param isAdd     the is add
     */
    protected void replaceOrAddFragment(final Fragment mFragment, final boolean isAdd) {
        if (getFragmentContainer() == 0) {
            return;
        }

        final String currentTag = mFragment.getClass().getSimpleName();

        // if user select the current navigation menu again, don't do anything
        if (getSupportFragmentManager().findFragmentByTag(currentTag) != null) {
            return;
        }
        // update the main content by replacing fragments
        final FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        if (isAdd) {
            fragmentTransaction.add(getFragmentContainer(), mFragment, currentTag);
        } else {
            fragmentTransaction.replace(getFragmentContainer(), mFragment, currentTag);
        }
        fragmentTransaction.addToBackStack(currentTag);
        fragmentTransaction.commitAllowingStateLoss();
    }


    @Override
    public boolean checkNetworkConnectedAndShowError() {
        if (isNetworkConnected()) {
            return true;
        } else {
            showErrorMessage(R.string.error_internet_not_connected);
            return false;
        }
    }

    @Override
    public boolean isNetworkConnected() {
        return CommonUtil.isNetworkAvailable(this);
    }

    @Override
    public void showLoading() {
        hideLoading();
        mProgressDialog = new ProgressDialog().show(this);
    }

    @Override
    public void showLoading(final int messageId) {
        hideLoading();
        mProgressDialog = new ProgressDialog().show(this, messageId);
    }

    @Override
    public void showLoading(final String message) {
        hideLoading();
        mProgressDialog = new ProgressDialog().show(this, message);
    }

    @Override
    public void hideLoading() {
        if (mProgressDialog != null && mProgressDialog.isShowing()) {
            mProgressDialog.dismiss();
        }
    }

    @Override
    public void showErrorMessage(final int message) {
        showErrorMessage(message, null);
    }

    @Override
    public void showErrorMessage(final int message, final OnErrorHandleCallback mOnErrorHandleCallback) {
        showErrorMessage(getString(message), mOnErrorHandleCallback);
    }

    @Override
    public void showErrorMessage(final String message) {
        showErrorMessage(message, null);
    }

    @Override
    public void showErrorMessage(final String message,
                                 final OnErrorHandleCallback mOnErrorHandleCallback) {
        showDialog(getString(R.string.text_error_title), message, mOnErrorHandleCallback);
    }

    @Override
    public void showDialog(final String message,
                           final OnErrorHandleCallback mOnErrorHandleCallback) {
        showDialog(null, message, mOnErrorHandleCallback);
    }

    @Override
    public void showDialog(final String title, final String message,
                           final OnErrorHandleCallback mOnErrorHandleCallback) {
        dismissDialog();
        mDialog = new CustomAlertDialog.Builder(BaseActivity.this)
                .setTitle(title)
                .setMessage(message)
                .setCancelable(mOnErrorHandleCallback == null)
                .setPositiveButton(android.R.string.ok, () -> {
                    if (mOnErrorHandleCallback != null) {
                        mOnErrorHandleCallback.onErrorCallback();
                    }
                })
                .show();
    }

    /**
     * Dismiss dialog.
     */
    private void dismissDialog() {
        if (mDialog != null && mDialog.isShowing()) {
            mDialog.dismiss();
        }
    }

    @Override
    public void showToast(final int messageResId) {
        showToast(getString(messageResId));
    }

    @Override
    public void showToast(final String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public int parseThrowableMessage(final Throwable cause) {
        if (cause instanceof UnknownHostException) {
            return R.string.error_internet_not_connected;
        } else if (cause instanceof SocketTimeoutException) {
            return R.string.error_internet_not_connected;
        } else if (cause instanceof ConnectException) {
            return R.string.error_internet_not_connected;
        } else if (cause instanceof JsonSyntaxException) {
            return R.string.error_paring;
        }
        return R.string.error_unexpected_error;
    }

    @Override
    public String getDeviceName() {
        return android.os.Build.MANUFACTURER + " " + android.os.Build.MODEL;
    }

    @Override
    protected void onDestroy() {
        mBasePresenter.onDetach();
        super.onDestroy();
    }


    @Override
    public void clearBackStack() {
        final FragmentManager fm = getSupportFragmentManager();
        for (int i = 0; i < fm.getBackStackEntryCount(); ++i) {
            fm.popBackStack();
        }
    }

    @Override
    public void backPress() {
        onBackPressed();
    }

    @Override
    public void clearBackStack(final String backStackTag) {
        getSupportFragmentManager().popBackStack(backStackTag, FragmentManager.POP_BACK_STACK_INCLUSIVE);
    }

    @Override
    public void showFragment(final String tag, final boolean isAdd, final Object... object) {
    }

    @Override
    public void setResultOkAndCloseActivity() {
        setResult(RESULT_OK, getIntent());
        finish();
    }

    @Override
    public void onBackPressed() {
        try {
            final int backStackEntryCount = getSupportFragmentManager().getBackStackEntryCount();
            if (backStackEntryCount > 0) {
                Fragment currentFragment = getCurrentFragment();
                if (currentFragment != null
                        && currentFragment instanceof BaseFragment
                        && !((BaseFragment) currentFragment).isBackPressAllowedFromFragment()) {
                    return;
                }
                if (backStackEntryCount == 1) {
                    finish();
                } else {
                    getSupportFragmentManager().popBackStack();
                }
            } else {
                finish();
            }
        } catch (final IllegalStateException ignored) {
            // There's no way to avoid getting this if saveInstanceState has already been called.
        }
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
