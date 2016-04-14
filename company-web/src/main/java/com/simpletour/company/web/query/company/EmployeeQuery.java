package com.simpletour.company.web.query.company;


import com.simpletour.common.core.dao.IBaseDao;
import com.simpletour.common.core.dao.query.ConditionOrderByQuery;
import com.simpletour.common.core.dao.query.condition.AndConditionSet;
import com.simpletour.common.core.dao.query.condition.Condition;
import com.simpletour.company.web.query.support.Query;

/**
 * Created by mario on 16-4-12.
 */
public class EmployeeQuery extends Query {
    /**
     * jobno账号
     */
    private Integer jobNo;
    /**
     * 姓名
     */
    private String name;
    /**
     * 电话号码
     */
    private String mobile;
    /**
     * 角色id
     */
    private Long roleId;

    /**
     * 租户id
     */
    private Long tenantId;

    public EmployeeQuery() {
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

    public Long getTenantId() {
        return tenantId;
    }

    public void setTenantId(Long tenantId) {
        this.tenantId = tenantId;
    }

    public ConditionOrderByQuery asConditionQuery() {
        ConditionOrderByQuery conditionOrderByQuery = new ConditionOrderByQuery();
        AndConditionSet andConditionSet = new AndConditionSet();
        if (this.jobNo != null)
            andConditionSet.addCondition("c.jobNo", jobNo, Condition.MatchType.eq);
        if (!(this.name == null || this.name.isEmpty()))
            andConditionSet.addCondition("c.name", name, Condition.MatchType.like);
        if (!(this.roleId == null || this.roleId <= 0))
            andConditionSet.addCondition("c.role.id", this.roleId, Condition.MatchType.eq);
        if (!(this.mobile == null || this.mobile.isEmpty()))
            andConditionSet.addCondition("c.mobile", this.mobile, Condition.MatchType.like);

        andConditionSet.addCondition("c.company.id", tenantId);

        conditionOrderByQuery.setCondition(andConditionSet);
        conditionOrderByQuery.addSortByField("c.id", IBaseDao.SortBy.ASC);
        conditionOrderByQuery.setPageIndex(this.getIndex());
        conditionOrderByQuery.setPageSize(this.getSize());
        return conditionOrderByQuery;
    }
}
