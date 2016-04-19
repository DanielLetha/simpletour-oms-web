package com.simpletour.platfrom.web.query.support;

/**
 * Created by yangdongfeng@simpletour.com on 2016/3/24.
 */
public interface TypeConverter<I, O> {
    O convert(I in);
}
