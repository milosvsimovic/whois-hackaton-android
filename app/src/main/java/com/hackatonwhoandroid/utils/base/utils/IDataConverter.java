package com.hackatonwhoandroid.utils.base.utils;

/**
 * Base interface used for data model conversion.
 */
public interface IDataConverter<InTypeT, OutTypeT> {

    /**
     * Method used for converting one item to another.
     *
     * @param item to be converted
     * @return converted item.
     */
    OutTypeT convert(InTypeT item);

}
