package com.simpletour.company.web.annotation;


import com.simpletour.company.web.annotation.validator.FaxValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Brief :  固定电话/传真的注解类
 * Author:  liangfei
 * Date  :  2015/12/28
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = FaxValidator.class)
public @interface Fax {
    boolean ignoreIfAbsent() default true;

    String message();

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
