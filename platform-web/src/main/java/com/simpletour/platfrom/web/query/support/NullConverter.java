package com.simpletour.platfrom.web.query.support;

/**
 * Created by yangdongfeng@simpletour.com on 2016/3/24.
 */
public class NullConverter implements TypeConverter<Object, Object> {
    @Override
    public Object convert(Object in) {
        return in;
    }
}
