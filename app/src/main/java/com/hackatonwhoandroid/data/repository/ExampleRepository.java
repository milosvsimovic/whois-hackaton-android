package com.hackatonwhoandroid.data.repository;

import com.hackatonwhoandroid.data.network.api.RESTApiMethods;
import com.hackatonwhoandroid.domain.repository.IExampleRepository;

import javax.inject.Inject;

import io.reactivex.Observable;

public class ExampleRepository implements IExampleRepository {

    @Inject
    RESTApiMethods apiMethodsServer;

    @Inject
    ExampleRepository() {
    }

    @Override
    public Observable<String> getExample() {
        return null;
    }
}
