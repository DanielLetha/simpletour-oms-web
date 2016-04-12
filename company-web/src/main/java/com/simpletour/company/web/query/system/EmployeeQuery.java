package com.simpletour.company.web.query.system;


import com.simpletour.common.core.dao.IBaseDao;
import com.simpletour.common.core.dao.query.ConditionOrderByQuery;
import com.simpletour.common.core.dao.query.condition.AndConditionSet;
import com.simpletour.common.core.dao.query.condition.Condition;
import com.simpletour.company.web.query.support.Query;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by mario on 16-4-12.
 */
public class EmployeeQuery extends Query {
    /**
     * jobno账号
     */
    private Integer jobno;
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

    public EmployeeQuery() {
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

    public Map<String, Object> asMap() {
        Map<String, Object> map = new HashMap<String, Object>();
        //fixed by XuHui: jobno为Integer，不用判断空字符串
        if (this.jobno != null) {
            map.put("jobNo", this.jobno);
        }
        if (this.name != null && !"".equals(this.name)) {
            map.put("name", getSearchStr(name));
        }
        //roleId == -1 时表示查询所有角色下的人员
        if (this.roleId != null && this.roleId != -1) {
            map.put("role.id", roleId);
        }
        map.put("del", false);
        return map;
    }

    public ConditionOrderByQuery asConditionQuery() {
        ConditionOrderByQuery conditionOrderByQuery = new ConditionOrderByQuery();
        AndConditionSet andConditionSet = new AndConditionSet();
        if (this.jobno != null)
            andConditionSet.addCondition("c.jobNo", jobno, Condition.MatchType.eq);
        if (!(this.name == null || this.name.isEmpty()))
            andConditionSet.addCondition("c.name", name, Condition.MatchType.like);
        if (!(this.roleId == null || this.roleId <= 0))
            andConditionSet.addCondition("c.role.id", this.roleId, Condition.MatchType.eq);
        if (!(this.mobile == null || this.mobile.isEmpty()))
            andConditionSet.addCondition("c.mobile", this.mobile, Condition.MatchType.like);
        conditionOrderByQuery.setCondition(andConditionSet);
        conditionOrderByQuery.addSortByField("c.id", IBaseDao.SortBy.ASC);
        conditionOrderByQuery.setPageIndex(this.getIndex());
        conditionOrderByQuery.setPageSize(this.getSize());
        return conditionOrderByQuery;
    }

}
