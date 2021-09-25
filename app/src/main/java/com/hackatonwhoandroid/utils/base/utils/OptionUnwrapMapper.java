package com.hackatonwhoandroid.utils.base.utils;

/**
 * Mapper class used to unwrap InTypeT object from {@link Option}
 * and map it to corresponding OutTypeT object.
 */
//public class OptionUnwrapMapper<InTypeT, OutTypeT>
//        implements Function<Option<InTypeT>, Option<OutTypeT>> {
//
//    private DataMapper<InTypeT, OutTypeT> mapper;
//
//    @Inject
//    public OptionUnwrapMapper(IDataConverter<InTypeT, OutTypeT> converter) {
//        this.mapper = new DataMapper<>(converter);
//    }
//
//    @Override
//    public Option<OutTypeT> apply(Option<InTypeT> option) {
//        return Observable.just(option)
//                .map(option1 -> option1.isNone() ? Option.<OutTypeT>none() :
//                        Option.ofObj(mapper.apply(OptionUnsafe.getUnsafe(option1))))
//                .blockingFirst();
//    }
//}
