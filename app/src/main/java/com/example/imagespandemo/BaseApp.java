package com.example.imagespandemo;

import android.app.Activity;
import android.app.Application;
import android.content.Context;

public class BaseApp extends Application {

    protected static BaseApp instance = null;

    protected Activity mMainActivity;

    public static BaseApp getInstance() {
        return instance;
    }

    @Override
    public void onCreate() {
        super.onCreate();
    }

    @Override
    protected void attachBaseContext(Context base) {
        instance = this;
        super.attachBaseContext(base);
    }

    @Override
    public void onTerminate() {
        super.onTerminate();
    }
    public Activity getMainActivity() {
        return mMainActivity;
    }

    public void setMainActivity(Activity mMainActivity) {
        this.mMainActivity = mMainActivity;
    }
}