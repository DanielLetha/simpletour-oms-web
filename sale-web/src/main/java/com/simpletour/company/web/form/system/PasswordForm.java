package com.simpletour.company.web.form.system;

import org.hibernate.validator.constraints.NotBlank;

/**
 * 密码修改from
 * Created by zt on 2016/1/26.
 */
public class PasswordForm {
    private Long id;

    /**
     * 旧密码
     */
    @NotBlank(message = "{oms.employee.password.notNull}")
    private String oldPassword;

    /**
     * 新密码
     */
    @NotBlank(message = "{oms.employee.password.notNull}")
    private String password;
    /**
     * 确认密码
     */
    @NotBlank(message = "{oms.employee.password.notNull}")
    private String repeatPassword;

    public PasswordForm() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getOldPassword() {
        return oldPassword;
    }

    public void setOldPassword(String oldPassword) {
        this.oldPassword = oldPassword;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRepeatPassword() {
        return repeatPassword;
    }

    public void setRepeatPassword(String confirmPassword) {
        this.repeatPassword = confirmPassword;
    }
}
