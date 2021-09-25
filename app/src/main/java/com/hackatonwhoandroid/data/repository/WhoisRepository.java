package com.hackatonwhoandroid.data.repository;

import android.content.res.Resources;

import com.hackatonwhoandroid.data.network.api.RESTApiMethods;
import com.hackatonwhoandroid.data.network.model.WhoIsDtoResponse;
import com.hackatonwhoandroid.domain.model.Message;
import com.hackatonwhoandroid.domain.repository.IWhoisRepository;

import org.mapstruct.factory.Mappers;

import javax.inject.Inject;

import io.reactivex.rxjava3.core.Single;

public class WhoisRepository implements IWhoisRepository {

    @Inject
    RESTApiMethods apiMethodsServer;

    @Inject
    Resources resources;

    @Inject
    WhoisRepository() {
    }

    @Override
    public Single<Message> getWhois(String domainName) {
        return apiMethodsServer.getWhois(domainName)
                .map(response -> Mappers.getMapper(WhoIsDtoResponse.Mappers.class).mapToMessage(response, resources));
    }
}
