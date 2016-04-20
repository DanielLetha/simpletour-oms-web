package com.simpletour.company.web.query.support;

import com.simpletour.common.core.dao.query.condition.Condition;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Author：XuHui/xuhui@simpletour.com
 * Brief：
 * Date: 2016/4/5
 * Time: 16:03
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface QueryWord {
    String value() default "";

    Condition.MatchType matchType() default Condition.MatchType.eq;

    boolean nullable() default false;

    Class enumType() default Enum.class;
}
