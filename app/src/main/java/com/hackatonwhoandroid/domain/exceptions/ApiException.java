package com.hackatonwhoandroid.domain.exceptions;

import java.io.IOException;

public class ApiException extends IOException {

    private final ApiError error;

    public ApiException(ApiError error) {
        this.error = error;
    }

    public ApiError getError() {
        return error;
    }
}