package com.simpletour.platfrom.web.view.module;

import com.simpletour.domain.company.Module;
import com.simpletour.platfrom.web.view.permission.PermissionQueryElementView;

import java.util.ArrayList;
import java.util.List;

/**
 * Author:  wangLin
 * Mail  :  wl@simpletour.com
 * Date  :  2016/4/9.
 * Remark:
 */
public class ModuleQueryElementView {

    //模块id
    private Long moduleId;

    private boolean isSelect = false;

    //模块名称
    private String moduleName;

    //权限的集合
    private List<PermissionQueryElementView> permissions = new ArrayList<>();

    public ModuleQueryElementView(Module module) {
        if (module != null){
            this.moduleId = module.getId();
            this.moduleName = module.getName();
            if (module.getPermissions() != null && !module.getPermissions().isEmpty()){
                module.getPermissions().stream().forEach(permission -> permissions.add(new PermissionQueryElementView(permission)));
            }
        }
    }

    public Long getModuleId() {
        return moduleId;
    }

    public void setModuleId(Long moduleId) {
        this.moduleId = moduleId;
    }

    public String getModuleName() {
        return moduleName;
    }

    public void setModuleName(String moduleName) {
        this.moduleName = moduleName;
    }

    public List<PermissionQueryElementView> getPermissions() {
        return permissions;
    }

    public void setPermissions(List<PermissionQueryElementView> permissions) {
        this.permissions = permissions;
    }

    public boolean isSelect() {
        return isSelect;
    }

    public void setSelect(boolean select) {
        isSelect = select;
    }
}
