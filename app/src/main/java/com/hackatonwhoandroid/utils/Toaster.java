package com.hackatonwhoandroid.utils;

import android.app.Application;
import android.widget.Toast;

import androidx.annotation.StringRes;


public class Toaster {

    private static Toaster toaster = null;
    private static Application application;
    private Toast currentToast;

    private Toaster(Application application) {
        this.application = application;
    }

    public static void init(Application application) {
        toaster = new Toaster(application);
    }

    public static void showToast(String message) {
        if (toaster.currentToast != null) {
            toaster.currentToast.cancel();
        }
        toaster.currentToast = Toast.makeText(application, message, Toast.LENGTH_SHORT);
        toaster.currentToast.show();
    }

    public static void showToast(@StringRes int res) {
        if (toaster.currentToast != null) {
            toaster.currentToast.cancel();
        }
        toaster.currentToast = Toast.makeText(application, application.getString(res), Toast.LENGTH_SHORT);
        toaster.currentToast.show();
    }

}
