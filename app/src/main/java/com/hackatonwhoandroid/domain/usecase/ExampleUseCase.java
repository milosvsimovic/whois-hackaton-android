package com.hackatonwhoandroid.domain.usecase;


import com.hackatonwhoandroid.domain.repository.IExampleRepository;
import com.hackatonwhoandroid.utils.base.domain.BaseUseCase;

import javax.inject.Inject;

import io.reactivex.Observable;


public class ExampleUseCase implements BaseUseCase<Observable<String>> {

    @Inject
    IExampleRepository syncRepository;

    @Inject
    ExampleUseCase() {
    }

    @Override
    public Observable<String> execute() {
        return syncRepository.getExample();
    }

}
