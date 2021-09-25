package com.hackatonwhoandroid.utils.base.domain;

public interface BaseParamUseCase<RequestT, ResponseT> {
    ResponseT execute(RequestT request);
}
