package com.hackatonwhoandroid.data.network.interceptors;

import android.net.ConnectivityManager;

import com.hackatonwhoandroid.domain.exceptions.NoNetworkException;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;

import javax.inject.Inject;

import okhttp3.Interceptor;
import okhttp3.Response;

public class NetworkConnectionInterceptor implements Interceptor {

    private final ConnectivityManager connectivityManager;

    @Inject
    NetworkConnectionInterceptor(ConnectivityManager connectivityManager) {
        this.connectivityManager = connectivityManager;
    }

    @NotNull
    @Override
    public Response intercept(@NotNull Chain chain) throws IOException {
        if (!hasNetworkConnection()) {
            throw new NoNetworkException();
        }
        return chain.proceed(chain.request());
    }

    private boolean hasNetworkConnection() {
        return connectivityManager != null && connectivityManager.getActiveNetworkInfo() != null && connectivityManager.getActiveNetworkInfo().isConnected();
    }

}
