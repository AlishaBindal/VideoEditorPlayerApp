package com.example.videoselectorapp.base.util;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.SystemClock;
import android.view.View;

/**
 * The Common util.
 */
public final class CommonUtil {
    private static final int MULTIPLE_CLICK_THRESHOLD = 1000;
    private static long mLastClickTime = 0;
    private static View mLastOccupiedView = null;

    private CommonUtil() {
    }

    /**
     * Method to check if internet is connected
     *
     * @param context context of calling class
     * @return true if connected to any network else return false
     */
    public static boolean isNetworkAvailable(final Context context) {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) (context.getApplicationContext()).getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }

    /**
     * Prevent multiple clicks boolean.
     *
     * @return the boolean
     */
    public static boolean preventMultipleClicks(View view) {
        if (SystemClock.elapsedRealtime() - mLastClickTime < MULTIPLE_CLICK_THRESHOLD && mLastOccupiedView == view) {
            return false;
        }
        mLastClickTime = SystemClock.elapsedRealtime();
        mLastOccupiedView = view;
        return true;
    }
}
