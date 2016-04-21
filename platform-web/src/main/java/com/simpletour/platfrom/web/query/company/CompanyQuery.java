package com.simpletour.platfrom.web.query.company;

import com.simpletour.commons.data.dao.query.condition.Condition;
import com.simpletour.platfrom.web.query.support.Query;
import com.simpletour.platfrom.web.query.support.QueryWord;

/**
 * Author:  wangLin
 * Mail  :  wl@simpletour.com
 * Date  :  2016/4/5.
 * Remark:
 */
public class CompanyQuery extends Query {

    //公司名称(全模糊)
    @QueryWord(matchType = Condition.MatchType.like)
    private String name;

    //公司地址(全模糊)
    @QueryWord(matchType = Condition.MatchType.like)
    private String address;

    //功能名称(全模糊)
    @QueryWord(matchType = Condition.MatchType.like)
    private String permissionName;

    //对接人名称(全模糊)
    @QueryWord(matchType = Condition.MatchType.like)
    private String buttManName;

    private Long permissionId;

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

    public String getPermissionName() {
        return permissionName;
    }

    public void setPermissionName(String permissionName) {
        this.permissionName = permissionName;
    }

    public String getButtManName() {
        return buttManName;
    }

    public void setButtManName(String buttManName) {
        this.buttManName = buttManName;
    }

    public Long getPermissionId() {
        return permissionId;
    }

    public void setPermissionId(Long permissionId) {
        this.permissionId = permissionId;
    }
}
