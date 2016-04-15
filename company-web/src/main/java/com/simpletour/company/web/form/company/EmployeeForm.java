package com.simpletour.company.web.form.company;

import com.simpletour.company.web.annotation.Mobile;
import com.simpletour.company.web.enums.FormModeType;
import com.simpletour.company.web.form.support.BaseForm;
import com.simpletour.domain.company.Company;
import com.simpletour.domain.company.Employee;
import com.simpletour.domain.company.Role;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.NotNull;

/**
 * Created by Mario on 2016/4/11.
 */
public class EmployeeForm extends BaseForm {
    /**
     * 工号
     * Remark: Modified by shiguanglu@simpletour.com at 2016-4-14
     */
    //@NotNull(message = "{oms.employee.jobNo.notNull}")
    //@Length(min = 0, max = 32, message = "{oms.employee.jobNo.length}")
    private Integer jobno;
    /**
     * 姓名
     */
    @NotBlank(message = "{oms.employee.name.notNull}")
    @Length(min = 2, max = 20, message = "{oms.employee.name.length}")
    private String name;

    /**
     * 手机号
     */
    @NotBlank(message = "{oms.common.mobile.notNull}")
    @Mobile(message = "{oms.common.mobile.length}")
    private String mobile;
    /**
     * 角色
     */
    @NotNull(message = "{oms.employee.role.notNull}")
    private Long roleId;
    private String roleName;
    /**
     * 公司
     * Remark: Modified by shiguanglu@simpletour.com at 2016-4-14
     */
    //@NotNull(message = "{oms.employee.company.notNull}")
    private Long companyId;
    private String companyName;

    /**
     * Remark: Modified by shiguanglu@simpletour.com at 2016-4-14
     */
    //@Length(min = 0, max = 10000, message = "{oms.employee.remark.length}")
    private String remark;

    private Integer version;

    public EmployeeForm() {
    }

    public EmployeeForm(Employee employee) {
        this.id = employee.getId();
        this.jobno = employee.getJobNo();
        this.name = employee.getName();
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

    public Employee as() {
        Employee employee = new Employee();
        if (this.getMode().equals(FormModeType.ADD.getValue())) {
            //TODO...设置默认头像地址
        }
        if (this.getMode().equals(FormModeType.UPDATE.getValue())) {
            employee.setId(this.getId());
            employee.setJobNo(this.getJobno());
            //TODO...设置头像地址
        }

        employee.setAvater("images/member.jpg");
        employee.setName(this.getName());
        employee.setMobile(this.getMobile());
        Company company = new Company();
        company.setId(this.getCompanyId());
        employee.setCompany(company);
        Role role = new Role();
        role.setId(this.getRoleId());
        employee.setRole(role);
        employee.setVersion(this.getVersion());
        employee.setAdmin(Boolean.FALSE);
        employee.setRemark(this.getRemark());
        return employee;
    }

}
