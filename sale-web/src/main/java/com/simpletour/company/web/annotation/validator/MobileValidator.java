package com.simpletour.company.web.annotation.validator;

import com.simpletour.company.web.annotation.Mobile;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Mario on 2016/4/11.
 */
public class MobileValidator implements ConstraintValidator<Mobile, String> {
    private Pattern pattern = Pattern.compile("^0?(13|14|15|17|18)[0-9]{9}$");

    private boolean ignoreIfAbsent;

    @Override
    public void initialize(Mobile constraintAnnotation) {
        this.ignoreIfAbsent = constraintAnnotation.ignoreIfAbsent();
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if (ignoreIfAbsent && (value == null || value.isEmpty())) return true;
        Matcher matcher = pattern.matcher(value);
        return matcher.matches();
    }
}
