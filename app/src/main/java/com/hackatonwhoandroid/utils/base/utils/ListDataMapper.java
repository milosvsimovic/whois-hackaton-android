package com.hackatonwhoandroid.utils.base.utils;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.functions.Function;

@SuppressWarnings("WeakerAccess")
public class ListDataMapper<InTypeT, OutTypeT> implements Function<List<InTypeT>, List<OutTypeT>> {

    private DataMapper<InTypeT, OutTypeT> mapper;

    @Inject
    public ListDataMapper(DataMapper<InTypeT, OutTypeT> mapper) {
        this.mapper = mapper;
    }

    @Override
    public List<OutTypeT> apply(List<InTypeT> postEntities) {
        return Observable.fromIterable(postEntities)
                .map(mapper)
                .toList()
                .blockingGet();
    }
}
