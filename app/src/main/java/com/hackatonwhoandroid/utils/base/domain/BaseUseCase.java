package com.hackatonwhoandroid.utils.base.domain;

public interface BaseUseCase<ResponseT> {
    ResponseT execute();
}
