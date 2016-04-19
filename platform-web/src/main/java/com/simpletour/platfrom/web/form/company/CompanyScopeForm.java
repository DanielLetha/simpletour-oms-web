package com.simpletour.platfrom.web.form.company;

import org.hibernate.validator.constraints.NotBlank;

import javax.validation.Valid;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.List;

/**
 * Author:  wangLin
 * Mail  :  wl@simpletour.com
 * Date  :  2016/4/5.
 * Remark:
 */
public class CompanyScopeForm {

    //模块的id
    @Pattern(regexp = "^\\d+$", message ="{pms.company.scope.module.id.notMatch}" )
    @NotBlank(message = "pms.company.scope.module.id.notBlank")
    private Long moduleId;


    //模块名称
    @NotBlank(message = "pms.company.scope.module.notBlank")
    private String moduleName;

    @Valid
    @Size(min=1,message = "{pms.company.scope.module.permission.size}")
    private List<CompanyPermissionForm> permissions = new ArrayList<>();

    public List<CompanyPermissionForm> getPermissions() {
        return permissions;
    }

    public void setPermissions(List<CompanyPermissionForm> permissions) {
        this.permissions = permissions;
    }

    public String getModuleName() {
        return moduleName;
    }

    public void setModuleName(String moduleName) {
        this.moduleName = moduleName;
    }

    public Long getModuleId() {
        return moduleId;
    }

    public void setModuleId(Long moduleId) {
        this.moduleId = moduleId;
    }
}
