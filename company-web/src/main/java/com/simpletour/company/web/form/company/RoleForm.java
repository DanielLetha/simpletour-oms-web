package com.simpletour.company.web.form.company;

import com.simpletour.company.web.form.support.BaseForm;
import com.simpletour.domain.system.Company;
import com.simpletour.domain.system.Module;
import com.simpletour.domain.system.Permission;
import com.simpletour.domain.system.Role;
import com.simpletour.service.system.IModuleService;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;

import java.util.*;

/**
 * 文件描述：角色权限关系表单类
 * 创建人员：石广路（shiguanglu@simpletour.com）
 * 创建日期：2016-4-12
 * 备注说明：null
 * @since 2.0-SNAPSHOT
 */
public class RoleForm extends BaseForm {
    @NotBlank(message = "{st.osp.role.name.length}")
    @Length(min = 2, max = 20, message = "{st.osp.role.name.length}")
    private String name;

    private Integer type;

    private String remark;

    private Company company;

    private List<Permission> permissionList = new ArrayList<>();

    private List<CompanyScopeForm> scopes = new ArrayList<>();

    private Integer version;

    private int count;

    public RoleForm() {
    }

    public RoleForm(Role role) {
        convert2RoleForm(role);
    }

    public RoleForm convert2RoleForm(Role role) {
        this.id = role.getId();
        this.name = role.getName();
        this.remark = role.getRemark();
        this.type = role.getType();
        this.version = role.getVersion();
        this.company = role.getCompany();
        this.permissionList = role.getPermissionList();
        this.scopes = null == this.company ? Collections.emptyList() : CompanyForm.toCompanyForm(role.getCompany()).getScopes();
        selectPermissions();
        return this;
    }

    public Role convert2Role(IModuleService moduleService) {
        Map<Long, Module> modulesMap = new HashMap<>();

        permissionList.clear();

        scopes.forEach(companyScopeForm -> {
            List<CompanyPermissionForm> permissions = companyScopeForm.getPermissions();
            if (permissions.isEmpty()) {
                return;
            }

            Module module;
            Long moduleId = companyScopeForm.getModuleId();

            if (modulesMap.containsKey(moduleId)) {
                module = modulesMap.get(moduleId);
            } else {
                Optional<Module> optional = moduleService.getModuleById(moduleId);
                if (!optional.isPresent()) {
                    return;
                }

                module = optional.get();
            }

            permissions.forEach(companyPermissionForm -> {
                if (companyPermissionForm.isChecked()) {
                    Long permissionId = companyPermissionForm.getPermissionId();
                    module.getPermissions().stream().anyMatch(item -> {
                        if (item.getId().equals(permissionId)) {
                            permissionList.add(item);
                            return true;
                        }
                        return false;
                    });
                }
            });
        });

        return new Role(id, name, getType(), company, remark, permissionList, version);
    }

    private void selectPermissions() {
        scopes.forEach(companyScopeForm -> {
            Long moduleId = companyScopeForm.getModuleId();
            String moduleName = companyScopeForm.getModuleName();
            List<CompanyPermissionForm> permissions = companyScopeForm.getPermissions();
            if (null == moduleId || 0L >= moduleId || null == moduleName || moduleName.isEmpty() || permissions.isEmpty()) {
                return;
            }

            count = 0;
            permissionList.forEach(permission -> {
                Module module = permission.getModule();
                if (!module.getId().equals(moduleId) || !module.getName().equals(moduleName)) {
                    return;
                }

                permissions.forEach(companyPermissionForm -> {
                    Long permissionId = companyPermissionForm.getPermissionId();
                    String permissionName = companyPermissionForm.getPermissionName();
                    if (null == permissionId || 0L >= permissionId || null == permissionName || permissionName.isEmpty()) {
                        return;
                    }

                    if (permission.getId().equals(permissionId) && permission.getName().equals(permissionName)) {
                        companyPermissionForm.setChecked(true);
                        count++;
                    }
                });
            });

            companyScopeForm.setChecked(count == permissions.size());
            companyScopeForm.setIndeterminate(0 < count && count != permissions.size());
        });
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getType() {
        return null == type ? 0 : type;
    }

    public void setType(Integer type) {
        this.type = null == type ? 0 : type;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public Company getCompany() {
        return company;
    }

    public void setCompany(Company company) {
        this.company = company;
    }

    public List<CompanyScopeForm> getScopes() {
        return scopes;
    }

    public void setScopes(List<CompanyScopeForm> scopes) {
        this.scopes = scopes;
    }

//    public List<Permission> getPermissionList() {
//        return permissionList;
//    }
//
//    public void setPermissionList(List<Permission> permissionList) {
//        this.permissionList = permissionList;
//    }

    public Integer getVersion() {
        return version;
    }

    public void setVersion(Integer version) {
        this.version = version;
    }
}
