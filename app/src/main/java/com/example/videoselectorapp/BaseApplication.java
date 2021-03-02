package com.example.videoselectorapp;

import android.app.Application;
import android.content.Context;

import java.lang.ref.WeakReference;

import io.paperdb.Paper;

/**
 * Base Application
 */
public class BaseApplication extends Application {
    private static final String TAG = BaseApplication.class.getSimpleName();
    private static WeakReference<Context> mWeakReference;

    /**
     * Getter to access Singleton instance
     *
     * @return instance of MyApplication
     */
    public static Context getAppContext() {
        return mWeakReference.get();
    }

    @Override
    public void onCreate() {
        super.onCreate();
        mWeakReference = new WeakReference<Context>(this);
        Paper.init(this);
    }
}
