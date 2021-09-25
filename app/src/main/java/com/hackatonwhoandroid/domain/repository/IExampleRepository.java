package com.hackatonwhoandroid.domain.repository;

import io.reactivex.Observable;

public interface IExampleRepository {

    Observable<String> getExample();

}
