package com.simpletour.company.web.query.system;

import com.simpletour.company.web.query.support.Query;

/**
 * Created by XuHui on 2015/12/3.
 */
public class RoleQuery extends Query {

    private String name;

    private String code;

    private String descr;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getDescr() {
        return descr;
    }

    public void setDescr(String descr) {
        this.descr = descr;
    }
}
