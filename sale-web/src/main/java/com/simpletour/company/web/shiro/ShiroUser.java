package com.simpletour.company.web.shiro;

import java.io.Serializable;

/**
 * Created by Mario on 2016/4/12.
 */
public class ShiroUser implements Serializable {
    private static final long serialVersionUID = -379708339491988932L;
    /**
     * 人员id
     */
    private Long id;
    /**
     * 工号
     */
    private Integer jobNo;
    /**
     * 姓名
     */
    private String name;
    /**
     * 所属公司
     */
    private Long companyId;

    public ShiroUser() {
    }

    public ShiroUser(Long id, Integer jobNo, String name, Long companyId) {
        this.id = id;
        this.jobNo = jobNo;
        this.name = name;
        this.companyId = companyId;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getJobNo() {
        return jobNo;
    }

    public void setJobNo(Integer jobNo) {
        this.jobNo = jobNo;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getCompanyId() {
        return companyId;
    }

    public void setCompanyId(Long companyId) {
        this.companyId = companyId;
    }
}
