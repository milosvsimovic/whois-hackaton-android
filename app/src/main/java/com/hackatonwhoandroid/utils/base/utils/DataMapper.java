package com.hackatonwhoandroid.utils.base.utils;

import javax.inject.Inject;

import io.reactivex.rxjava3.functions.Function;

/**
 * Helper data model mapper class.
 */
public class DataMapper<InTypeT, OutTypeT>
        implements Function<InTypeT, OutTypeT> {

    private IDataConverter<InTypeT, OutTypeT> converter;

    @Inject
    public DataMapper(IDataConverter<InTypeT, OutTypeT> converter) {
        this.converter = converter;
    }

    @Override
    public OutTypeT apply(InTypeT inTypeT) {
        return converter.convert(inTypeT);
    }
}
