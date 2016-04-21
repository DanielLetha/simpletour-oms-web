package com.simpletour.platfrom.web.view.permission;


import com.simpletour.domain.company.Permission;

/**
 * Author:  wangLin
 * Mail  :  wl@simpletour.com
 * Date  :  2016/4/9.
 * Remark:
 */
public class PermissionQueryElementView {

    //权限id
    private Long permissionId;

    //权限名称
    private String permissionName;

    public PermissionQueryElementView(Permission permission) {
        if (permission != null){
            this.permissionId = permission.getId();
            this.permissionName = permission.getName();
        }
    }

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
}
