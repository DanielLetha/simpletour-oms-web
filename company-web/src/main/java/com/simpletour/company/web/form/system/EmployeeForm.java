package com.simpletour.company.web.form.system;

import com.simpletour.company.web.annotation.Mobile;
import com.simpletour.company.web.enums.FormModeType;
import com.simpletour.company.web.form.support.BaseForm;
import com.simpletour.company.web.util.PasswordUtil;
import com.simpletour.domain.system.Company;
import com.simpletour.domain.system.Employee;
import com.simpletour.domain.system.Role;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.NotNull;

/**
 * Created by Mario on 2016/4/11.
 */
public class EmployeeForm extends BaseForm {
    /**
     * 工号
     */
    @NotNull(message = "{admin.employee.jobno.notnull}")
    private Integer jobno;
    /**
     * 姓名
     */
    @NotBlank(message = "{admin.employee.name.notnull}")
    @Length(min = 2, max = 20, message = "{admin.employee.name.length}")
    private String name;
    /**
     * 密码
     */
    @NotBlank(message = "{admin.employee.password.notnull}")
    @Length(min = 6, message = "{admin.employee.password.length}")
    private String password;
    /**
     * 原始密码,用于更新密码时比较,如果不同，则重新进行加密
     */
    private String originalPwd;
    /**
     * 手机号
     */
    @Mobile(message = "{admin.employee.mobile.notnull}")
    private String mobile;
    /**
     * 角色
     */
    @NotNull(message = "{admin.employee.role.notnull}")
    private Long roleId;
    private String roleName;
    /**
     * 公司
     */
    @NotNull(message = "{admin.employee.company.notnull}")
    private Long companyId;
    private String companyName;
    /**
     * 类型
     */
    @NotBlank(message = "{admin.employee.status.notnull}")
    private String status;
    private String salt;
    private Integer version;

    public EmployeeForm() {
    }

    public EmployeeForm(Employee employee) {
        this.id = employee.getId();
        this.jobno = employee.getJobNo();
        this.name = employee.getName();
        this.password = employee.getPasswd();
        this.salt = employee.getSalt();
        this.originalPwd = employee.getPasswd();
        this.mobile = employee.getMobile();
        this.roleId = employee.getRole().getId();
        this.roleName = employee.getRole().getName();
        this.companyId = employee.getCompany().getId();
        this.companyName = employee.getCompany().getName();
        this.version = employee.getVersion();
    }

    public Integer getJobno() {
        return jobno;
    }

    public void setJobno(Integer jobno) {
        this.jobno = jobno;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getOriginalPwd() {
        return originalPwd;
    }

    public void setOriginalPwd(String originalPwd) {
        this.originalPwd = originalPwd;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public Long getRoleId() {
        return roleId;
    }

    public void setRoleId(Long roleId) {
        this.roleId = roleId;
    }

    public String getRoleName() {
        return roleName;
    }

    @Override
    public Long getCompanyId() {
        return companyId;
    }

    @Override
    public void setCompanyId(Long companyId) {
        this.companyId = companyId;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getSalt() {
        return salt;
    }

    public void setSalt(String salt) {
        this.salt = salt;
    }

    public Integer getVersion() {
        return version;
    }

    public void setVersion(Integer version) {
        this.version = version;
    }

    public Employee as() {
        Employee employee = new Employee();
        if (this.getMode().equals(FormModeType.UPDATE.getValue())) {
            employee.setId(this.getId());
            if (!this.getOriginalPwd().equals(this.getPassword())) {
                this.setSalt(PasswordUtil.generateSalt());
                employee.setSalt(this.getSalt());
                employee.setPasswd(PasswordUtil.getMd5Password(this.getPassword(), this.getSalt()));
            } else {
                employee.setSalt(this.getSalt());
                employee.setPasswd(this.getPassword());
            }
        }
        if (this.getMode().equals(FormModeType.ADD.getValue())) {
            this.setSalt(PasswordUtil.generateSalt());
            employee.setSalt(this.getSalt());
            employee.setPasswd(PasswordUtil.getMd5Password(this.getPassword(), this.getSalt()));
        }
        employee.setJobNo(this.getJobno());
        employee.setName(this.getName());
        employee.setMobile(this.getMobile());
        Company company = new Company();
        company.setId(this.getCompanyId());
        employee.setCompany(company);
        Role role = new Role();
        role.setId(this.getRoleId());
        employee.setRole(role);
        employee.setVersion(this.getVersion());
        return employee;
    }

}
