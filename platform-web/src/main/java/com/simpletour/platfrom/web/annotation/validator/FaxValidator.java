package com.simpletour.platfrom.web.annotation.validator;


import com.simpletour.platfrom.web.annotation.Fax;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Brief :  固定电话/传真的验证器
 * Author:  liangfei
 * Date  :  2015/12/28
 */
public class FaxValidator implements ConstraintValidator<Fax, String> {
    private Pattern pattern = Pattern.compile("[0]{1}[0-9]{2,3}-[0-9]{7,8}");

    private boolean ignoreIfAbsent;

    @Override
    public void initialize(Fax fax) {
        this.ignoreIfAbsent = fax.ignoreIfAbsent();
    }

    @Override
    public boolean isValid(String s, ConstraintValidatorContext constraintValidatorContext) {
        if(ignoreIfAbsent&&(s==null||s.isEmpty())) return true;
        Matcher matcher = pattern.matcher(s);
        return matcher.matches();
    }
}
