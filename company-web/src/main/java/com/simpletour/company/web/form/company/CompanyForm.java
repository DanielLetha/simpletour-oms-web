package com.simpletour.company.web.form.company;

import com.simpletour.company.web.annotation.Mobile;
import com.simpletour.company.web.enums.FormModeType;
import com.simpletour.company.web.form.support.BaseForm;
import com.simpletour.domain.system.Company;
import com.simpletour.domain.system.Module;
import com.simpletour.domain.system.Permission;
import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Author:  wangLin
 * Mail  :  wl@simpletour.com
 * Date  :  2016/4/5.
 * Remark:
 */
public class CompanyForm extends BaseForm {

    //名称
    @NotBlank(message = "{pms.company.name.notBlank}")
    @Length(message = "{pms.company.name.length}",min=2,max = 20)
    private String name;

    //地址
    @NotBlank(message = "{pms.company.address.notBlank}")
    @Length(message = "{pms.company.address.length}",min=2,max = 50)
    private String address;

    //备注
    @Length(message = "{pms.company.remark.length}", max = 10000)
    private String remark;

    //公司的对接人
    //姓名
    @NotBlank(message = "{pms.company.contacts.notBlank}")
    @Length(message = "{pms.company.contacts.length}",min=2,max = 20)
    private String contacts;

    //手机号码
    @NotBlank(message = "{pms.company.mobile.notBlank}")
    @Mobile(message = "pms.company.mobile.notMatch")
    private String mobile;

    //传真
    @Length(message = "{pms.company.fax.length}",max = 16)
    private String fax;

    //邮箱
    @Length(message = "{pms.company.email.length}",max = 32)
    @Email(message = "pms.company.email.notMatch")
    private String email;

    //其他联系方式
    @Length(message = "{pms.company.link.length}",max = 100)
    private String link;

    //权限范围的id
    @Valid
    private List<CompanyScopeForm> scopes = new ArrayList<>();

    private Integer version;

    public static CompanyForm toCompanyForm(Company company){
        CompanyForm companyForm = new CompanyForm();
        companyForm.setId(company.getId());
        companyForm.setName(company.getName());
        companyForm.setAddress(company.getAddress());
        companyForm.setRemark(company.getRemark());
        companyForm.setVersion(company.getVersion());

        companyForm.setContacts(company.getContacts());
        companyForm.setMobile(company.getMobile());
        companyForm.setEmail(company.getEmail());
        companyForm.setFax(company.getFax());
        companyForm.setLink(company.getLink());

        ArrayList<CompanyScopeForm> scopes = new ArrayList<>();
        //对权限按照模块id进行分组
        if (company.getPermissions() != null && !company.getPermissions().isEmpty()){
            Map<Module, List<Permission>> moduleMap = company.getPermissions().stream().collect(Collectors.groupingBy(Permission::getModule));
            for(Map.Entry<Module,List<Permission>> entry : moduleMap.entrySet()){
                Module module = entry.getKey();
                List<Permission> permissions = entry.getValue();

                CompanyScopeForm scopeForm = new CompanyScopeForm();
                scopeForm.setModuleId(module.getId());
                scopeForm.setModuleName(module.getName());
                ArrayList<CompanyPermissionForm> permissionForms = new ArrayList<>();
                for (Permission permission : permissions){
                    permissionForms.add(new CompanyPermissionForm(){{
                        setPermissionId(permission.getId());
                        setPermissionName(permission.getName());
                    }});
                }
                scopeForm.setPermissions(permissionForms);
                scopes.add(scopeForm);
            }
            companyForm.setScopes(scopes);
        }
        return companyForm;
    }

    public Company as(){
        Company company = new Company();
        if (this.getMode().equals(FormModeType.UPDATE.getValue())) {
            company.setId(this.getId());
            company.setVersion(this.getVersion());
        }
        company.setName(this.getName());
        company.setAddress(this.getAddress());
        company.setRemark(this.getRemark());
        company.setContacts(this.getContacts());
        company.setMobile(this.getMobile());
        company.setFax(this.getFax());
        company.setEmail(this.getEmail());
        company.setLink(this.getLink());
        ArrayList<Permission> permissions = new ArrayList<>();
        for (CompanyScopeForm scopeForm : this.scopes) {
            for (CompanyPermissionForm permissionForm:scopeForm.getPermissions()){
                Permission permission = new Permission();
                permission.setId(permissionForm.getPermissionId());
                Module module = new Module();
                module.setId(scopeForm.getModuleId());
                permission.setModule(module);
                permissions.add(permission);
            }
        }
        company.setPermissions(permissions);
        return company;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getContacts() {
        return contacts;
    }

    public void setContacts(String contacts) {
        this.contacts = contacts;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getFax() {
        return fax;
    }

    public void setFax(String fax) {
        this.fax = fax;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public List<CompanyScopeForm> getScopes() {
        return scopes;
    }

    public void setScopes(List<CompanyScopeForm> scopes) {
        this.scopes = scopes;
    }

    public Integer getVersion() {
        return version;
    }

    public void setVersion(Integer version) {
        this.version = version;
    }
}
