package com.simpletour.company.web.form.system;

import org.hibernate.validator.constraints.NotBlank;

/**
 * Created by Mario on 2016/4/12.
 */
public class LoginForm {
    /**
     * 用户名
     */
    @NotBlank(message = "{oms.login.account.notNull}")
    private String userName;
    /**
     * 密码
     */
    @NotBlank(message = "{oms.login.password.notNull}")
    private String password;
    /**
     * 记住我
     */
    private String rememberMe;

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRememberMe() {
        return rememberMe;
    }

    public void setRememberMe(String rememberMe) {
        this.rememberMe = rememberMe;
    }
}
