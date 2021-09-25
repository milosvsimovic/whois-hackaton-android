package com.hackatonwhoandroid.domain.usecase;


import com.hackatonwhoandroid.domain.repository.IWhoisRepository;
import com.hackatonwhoandroid.utils.base.domain.BaseParamUseCase;

import javax.inject.Inject;

import io.reactivex.rxjava3.core.Single;


public class WhoisUseCase implements BaseParamUseCase<String, Single<String>> {

    @Inject
    IWhoisRepository syncRepository;

    @Inject
    WhoisUseCase() {
    }

    @Override
    public Single<String> execute(String domain) {
        return syncRepository.getWhois(domain);
    }

}
