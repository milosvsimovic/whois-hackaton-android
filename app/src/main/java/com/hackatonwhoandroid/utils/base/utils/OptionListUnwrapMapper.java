package com.hackatonwhoandroid.utils.base.utils;

//@SuppressWarnings("WeakerAccess")
//public class OptionListUnwrapMapper<InTypeT, OutTypeT>
//        implements Function<Option<List<InTypeT>>, Option<List<OutTypeT>>> {
//
//    private ListDataMapper<InTypeT, OutTypeT> mapper;
//
//    @Inject
//    public OptionListUnwrapMapper(IDataConverter<InTypeT, OutTypeT> converter) {
//        this.mapper = new ListDataMapper<>(new DataMapper<>(converter));
//    }
//
//    @Override
//    public Option<List<OutTypeT>> apply(Option<List<InTypeT>> option) {
//        return Observable.just(option)
//                .map(option1 -> option1.isNone() ? Option.<List<OutTypeT>>none() : mapList(option1))
//                .blockingFirst();
//    }
//
//    private Option<List<OutTypeT>> mapList(Option<List<InTypeT>> listIn) {
//        List<OutTypeT> listOut = mapper.apply(OptionUnsafe.getUnsafe(listIn));
//        return listOut.size() == 0 ? Option.none() : Option.ofObj(listOut);
//    }
//}
