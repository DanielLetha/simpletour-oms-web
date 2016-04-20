package com.simpletour.platfrom.web.form.system;

import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotNull;

/**
 * Created by Mario on 2016/4/12.
 */
public class LoginForm {
    @Length(min = 6, max = 20, message = "{oms.login.name.length}")
    private String username;

    @Length(min = 6, max = 64, message = "{oms.login.password.length}")
    private String password;

    @NotNull(message = "{oms.login.rememberMe.null}")
    private Boolean rememberMe;

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Boolean getRememberMe() {
        return rememberMe;
    }

    public void setRememberMe(Boolean rememberMe) {
        this.rememberMe = rememberMe;
    }
}
