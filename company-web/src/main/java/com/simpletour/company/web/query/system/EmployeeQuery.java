package com.simpletour.company.web.query.system;


import com.simpletour.company.web.query.support.Query;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by lh on 15-12-3.
 */
public class EmployeeQuery extends Query {
    private Integer jobno;

    private String name;


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

}
