package com.simpletour.platfrom.web.form.account;

import com.simpletour.platfrom.web.form.support.BaseForm;
import org.hibernate.validator.constraints.Length;

/**
 * 修改密码时，所提交的form
 * User: Hawk
 * Date: 2016/4/7 - 14:01
 */
public class ModifyPasswordForm extends BaseForm {

    @Length(min = 6, max = 64, message = "{oms.login.name.length}")
    private String oldPassword;

    @Length(min = 6, max = 64, message = "{oms.login.password.length}")
    private String newPassword;

    public String getNewPassword() {
        return newPassword;
    }

    public void setNewPassword(String newPassword) {
        this.newPassword = newPassword;
    }

    public String getOldPassword() {
        return oldPassword;
    }

    public void setOldPassword(String oldPassword) {
        this.oldPassword = oldPassword;
    }
}
