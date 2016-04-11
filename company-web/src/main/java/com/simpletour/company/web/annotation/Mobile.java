package com.simpletour.company.web.annotation;

import com.simpletour.company.web.annotation.validator.MobileValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Created by Mario on 2016/4/11.
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = MobileValidator.class)
public @interface Mobile {

    boolean ignoreIfAbsent() default false;

    String message();

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
