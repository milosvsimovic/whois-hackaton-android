package com.hackatonwhoandroid.data.network.interceptors;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;

import javax.inject.Inject;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

public class ContentTypeInterceptor implements Interceptor {

    @Inject
    ContentTypeInterceptor() {
    }

    @NotNull
    @Override
    public Response intercept(@NotNull Chain chain) throws IOException {
        Request originalRequest = chain.request();

        Request.Builder builder = originalRequest.newBuilder();
        builder.header("Content-Type", "application/json");

        Request newRequest = builder.build();
        return chain.proceed(newRequest);
    }
}

