package com.simpletour.platfrom.web.form.system;

import com.simpletour.domain.company.Module;
import com.simpletour.domain.company.Permission;
import com.simpletour.platfrom.web.enums.FormModeType;
import com.simpletour.platfrom.web.form.support.BaseForm;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.NotEmpty;

import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Author：XuHui/xuhui@simpletour.com
 * Brief：
 * Date: 2016/4/7
 * Time: 10:01
 */
public class ModuleForm extends BaseForm {
    @NotBlank(message = "{pms.module.name.notnull}")
    @Length(min = 2,max = 50,message = "{pms.module.name.length}")
    private String name;

    @NotBlank(message="{pms.module.domain.notnull}")
    @Length(min = 2,max=50,message="{pms.module.domain.length}")
    private String domain;

    @NotNull(message = "{pms.module.function.null}")
    @NotEmpty(message = "{pms.module.function.length}")
    private List<PermissionForm> permissionFormList=new ArrayList<>();

    private Integer version;

    public ModuleForm(){}

    public ModuleForm(Module module){
        this.id=module.getId();
        this.name=module.getName();
        this.domain=module.getDomain();
        this.version=module.getVersion();
        this.permissionFormList=module.getPermissions().stream()
                .map(PermissionForm::new).collect(Collectors.toList());
    }

    public Module as(){
        Module module=new Module(name,domain);
        List<Permission> permissions=permissionFormList.stream()
                .map(tmp-> {
                    tmp.setMode(this.mode);
                    return tmp.as();
                }).collect(Collectors.toList());
        module.setPermissions(permissions);
        if(mode.equals(FormModeType.UPDATE.getValue())) {
            module.setId(id);
            module.setVersion(version);
        }
        return module;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDomain() {
        return domain;
    }

    public void setDomain(String domain) {
        this.domain = domain;
    }

    public List<PermissionForm> getPermissionFormList() {
        return permissionFormList;
    }

    public void setPermissionFormList(List<PermissionForm> permissionFormList) {
        this.permissionFormList = permissionFormList;
    }

    public Integer getVersion() {
        return version;
    }

    public void setVersion(Integer version) {
        this.version = version;
    }
}
