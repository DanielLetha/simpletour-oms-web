package com.simpletour.platfrom.web.form.company;

import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.Pattern;

/**
 * Author:  wangLin
 * Mail  :  wl@simpletour.com
 * Date  :  2016/4/8.
 * Remark:
 */
public class CompanyPermissionForm {

    //权限的id
    @Pattern(regexp = "^\\d+$", message ="{pms.company.scope.module.permission.id.notMatch}" )
    @NotBlank(message = "pms.company.scope.module.permission.id.notBlank")
    private String permissionId;

    @NotBlank(message = "pms.company.scope.module.permission.name.notBlank")
    private String permissionName;

    public String getPermissionId() {
        return permissionId;
    }

    public void setPermissionId(String permissionId) {
        this.permissionId = permissionId;
    }

    public String getPermissionName() {
        return permissionName;
    }

    public void setPermissionName(String permissionName) {
        this.permissionName = permissionName;
    }
}
