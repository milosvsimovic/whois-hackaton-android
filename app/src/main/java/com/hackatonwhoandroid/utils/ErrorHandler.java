package com.hackatonwhoandroid.utils;

import android.content.res.Resources;
import android.util.Log;

import com.hackatonwhoandroid.R;
import com.hackatonwhoandroid.domain.exceptions.ApiException;
import com.hackatonwhoandroid.domain.exceptions.NoNetworkException;

import java.util.Arrays;

import javax.inject.Inject;

public class ErrorHandler {

    private static final String TAG = ErrorHandler.class.getSimpleName();

    @Inject
    Resources resources;

    @Inject
    ErrorHandler() {
    }

    public String parse(Throwable throwable) {
        if (throwable instanceof NoNetworkException) {
            Log.e(TAG, resources.getString(R.string.error_no_network));
            return resources.getString(R.string.error_no_network);
        } else if (throwable instanceof ApiException) {
            ApiException apiException = (ApiException) throwable;
            String apiError = apiException.getError().getError();
            Log.e(TAG, apiError + " " + Arrays.toString(throwable.getStackTrace()));
            return apiError;
        } else {
            Log.e(TAG, "general Error: " + Arrays.toString(throwable.getStackTrace()));
            return resources.getString(R.string.error_general);
        }
    }

}
