package com.simpletour.company.web.form.company;

import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.NotNull;

/**
 * Author:  wangLin
 * Mail  :  wl@simpletour.com
 * Date  :  2016/4/8.
 * Remark:
 */
public class CompanyPermissionForm {
    //权限的id
    @NotNull(message = "pms.company.scope.module.permission.id.notNull")
    private Long permissionId;

    @NotBlank(message = "pms.company.scope.module.permission.name.notBlank")
    private String permissionName;

    private boolean checked = false;

    public Long getPermissionId() {
        return permissionId;
    }

    public void setPermissionId(Long permissionId) {
        this.permissionId = permissionId;
    }

    public String getPermissionName() {
        return permissionName;
    }

    public void setPermissionName(String permissionName) {
        this.permissionName = permissionName;
    }

    public boolean isChecked() {
        return checked;
    }

    public void setChecked(boolean checked) {
        this.checked = checked;
    }
}
