package com.simpletour.company.web.query.company;

import com.simpletour.company.web.query.support.Query;
import com.simpletour.dao.company.query.RoleQuery;

/**
 * 文件描述：角色查询条件封装类
 * 创建人员：石广路（shiguanglu@simpletour.com）
 * 创建日期：2016-4-11 17:54
 * 备注说明：null
 * @since 2.0-SNAPSHOT
 */
public class RoleQueryConditions extends Query {
    private String name;

    private String module;

    private String permission;

    Integer type = 0;

    public RoleQueryConditions() {
    }

    public RoleQueryConditions(String name, String module, String permission, Integer type) {
        this.name = name;
        this.module = module;
        this.permission = permission;
        this.type = null == type ? 0 : type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getModule() {
        return module;
    }

    public void setModule(String module) {
        this.module = module;
    }

    public String getPermission() {
        return permission;
    }

    public void setPermission(String permission) {
        this.permission = permission;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public RoleQuery asRoleQuery() {
        return new RoleQuery(name, module, permission, type);
    }
}
