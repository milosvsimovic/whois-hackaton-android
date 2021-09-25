package com.hackatonwhoandroid.data.network.interceptors;

import com.google.gson.Gson;
import com.hackatonwhoandroid.domain.exceptions.ApiError;
import com.hackatonwhoandroid.domain.exceptions.ApiException;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;

import javax.inject.Inject;

import okhttp3.Interceptor;
import okhttp3.Response;

public class ErrorInterceptor implements Interceptor {

    @Inject
    Gson gson;

    @Inject
    ErrorInterceptor() {
    }

    @NotNull
    @Override
    public Response intercept(@NotNull Chain chain) throws IOException {
        Response response = chain.proceed(chain.request());

        if (!response.isSuccessful()) {
            throw new ApiException(parseResponse(response));
        }
        return response;
    }

    @SuppressWarnings("ConstantConditions")
    private ApiError parseResponse(Response response) {
        ApiError apiError;
        try {
            apiError = gson.fromJson(response.body().charStream(), ApiError.class);
        } catch (Exception exception) {
            apiError  = new ApiError();
        }
        apiError.setCode(response.code());
        return apiError;
    }
}
