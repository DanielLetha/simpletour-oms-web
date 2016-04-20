package com.simpletour.platfrom.web.form.system;

import com.simpletour.domain.company.Permission;
import com.simpletour.domain.company.ScopeTemplate;
import com.simpletour.platfrom.web.enums.FormModeType;
import com.simpletour.platfrom.web.form.support.BaseForm;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;

import java.util.ArrayList;
import java.util.List;

/**
 * Author：XuHui/xuhui@simpletour.com
 * Brief：
 * Date: 2016/4/7
 * Time: 10:16
 */
public class ScopeTemplateForm extends BaseForm {

    @NotBlank(message = "{pms.scope.name.notnull}")
    @Length(min = 2,max = 20,message = "{pms.scope.name.lenght}")
    private String name;

    private String remark;

    private Integer version;

    private List<ModuleForm> moduleForms = new ArrayList<>();

    public ScopeTemplateForm(){}

    public ScopeTemplateForm(ScopeTemplate scopeTemplate){
        this.id=scopeTemplate.getId();
        this.version=scopeTemplate.getVersion();
        this.name=scopeTemplate.getName();
        this.remark=scopeTemplate.getRemark();
        scopeTemplate.getPermissions().stream().forEach(tmp->{
            boolean content=false;
            for(ModuleForm moduleForm:moduleForms){
                if(moduleForm.getId().equals(tmp.getModule().getId())){
                    moduleForm.getPermissionFormList().add(new PermissionForm(tmp));
                    content=true;
                }
            }
            if(!content){
                ModuleForm moduleForm = new ModuleForm();
                moduleForm.setId(tmp.getModule().getId());
                moduleForm.setName(tmp.getModule().getName());
                moduleForm.setVersion(tmp.getModule().getVersion());
                moduleForm.setDomain(tmp.getModule().getDomain());
                moduleForm.getPermissionFormList().add(new PermissionForm(tmp));
                moduleForms.add(moduleForm);
            }
        });
    }

    public ScopeTemplate as(){
        ScopeTemplate scopeTemplate=new ScopeTemplate(name,remark);
        if(mode.equals(FormModeType.UPDATE.getValue())){
            scopeTemplate.setId(id);
            scopeTemplate.setVersion(version);
        }
        moduleForms.forEach(moduleForm->{
            moduleForm.getPermissionFormList().forEach(permissionForm -> {
                Permission permission = new Permission();
                permission.setId(permissionForm.getId());
                scopeTemplate.getPermissions().add(permission);
            });
        });
        return scopeTemplate;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public Integer getVersion() {
        return version;
    }

    public void setVersion(Integer version) {
        this.version = version;
    }

    public List<ModuleForm> getModuleForms() {
        return moduleForms;
    }

    public void setModuleForms(List<ModuleForm> moduleForms) {
        this.moduleForms = moduleForms;
    }
}
