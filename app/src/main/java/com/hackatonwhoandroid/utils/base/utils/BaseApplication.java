package com.hackatonwhoandroid.utils.base.utils;

import android.app.Application;
import android.content.Context;
import android.content.res.Resources;

import androidx.multidex.MultiDex;

import dagger.android.support.DaggerApplication;

public abstract class BaseApplication extends DaggerApplication {

    private static Application applicationContext;

    public static Application getAppContext() {
        return applicationContext;
    }

    public static Resources getAppResources() {
        return getAppContext().getResources();
    }

    @Override
    public void onCreate() {
        super.onCreate();
        applicationContext = this;
    }

    @Override
    protected void attachBaseContext(Context context) {
        super.attachBaseContext(context);
        setViewModelId();
        setupMultiDex(context);
    }

    private void setupMultiDex(Context context){
        if (enableMultiDex()) {
            MultiDex.install(context);
        }
    }

    protected abstract void setViewModelId();

    protected boolean enableMultiDex() {
        return false;
    }

}