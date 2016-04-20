package com.simpletour.platfrom.web.form.system;

import com.simpletour.domain.company.Permission;
import com.simpletour.platfrom.web.enums.FormModeType;
import com.simpletour.platfrom.web.form.support.BaseForm;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;

/**
 * Created by XuHui on 2015/12/4.
 */
public class PermissionForm extends BaseForm {
    @NotBlank(message = "{pms.function.name.notnull}")
    @Length(min = 2, max = 20, message = "{pms.function.name.length}")
    private String name;

    @NotBlank(message = "{pms.function.path.notnull}")
    @Length(min=2,max=50,message = "{pms.function.path.length}")
    private String path;

    @NotBlank(message = "{pms.function.code.notnull}")
    @Length(min = 2, max = 50, message = "{pms.function.code.length}")
    private String code;

    private Integer version;

    public PermissionForm() {
    }

    public PermissionForm(Permission permission) {
        this.id = permission.getId();
        this.name = permission.getName();
        //this.descr=permission.getDescr();
        this.path=permission.getPath();
        this.code = permission.getCode();
        this.version = permission.getVersion();
    }

    public Permission as() {
        Permission permission = new Permission();
        if (this.getMode().equals(FormModeType.UPDATE.getValue()) || this.getMode().equals(FormModeType.DEL.getValue())) {
            permission.setId(id);
        }
        permission.setName(name);
        permission.setCode(code);
        permission.setPath(path);
        //permission.setDescr(descr);
        permission.setVersion(version);
        return permission;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

//    public String getDescr() {
//        return descr;
//    }

//    public void setDescr(String descr) {
//        this.descr = descr;
//    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Integer getVersion() {
        return version;
    }

    public void setVersion(Integer version) {
        this.version = version;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }
}
