package com.simpletour.company.web.query.support;

import com.simpletour.commons.data.dao.query.condition.Condition;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * User: XuHui
 * Date: 2016/2/23
 * Time: 13:11
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface QueryWord {
    String value() default "";

    boolean byLike() default false;

    Condition.MatchType matchType() default Condition.MatchType.eq;

    /**
     *  是否需要用空来查询
     *  false为不用，即为空则不做为搜索条件。
     */
    boolean nullable() default false;

    Class enumType()default Enum.class;

    /**
     * 当Query中的类型需要转换时，自定义TypeConverter，然后实现convert接口
     * @return
     */
    Class converter() default NullConverter.class;
}
