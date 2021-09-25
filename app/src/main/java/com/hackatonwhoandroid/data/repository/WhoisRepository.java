package com.hackatonwhoandroid.data.repository;

import com.hackatonwhoandroid.data.network.api.RESTApiMethods;
import com.hackatonwhoandroid.domain.repository.IWhoisRepository;

import javax.inject.Inject;

import io.reactivex.rxjava3.core.Single;

public class WhoisRepository implements IWhoisRepository {

    @Inject
    RESTApiMethods apiMethodsServer;

    @Inject
    WhoisRepository() {
    }

    @Override
    public Single<String> getWhois(String domain) {
        return apiMethodsServer.getWhois(domain);
    }
}
