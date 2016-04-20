package com.simpletour.platfrom.web.query.system;

import com.simpletour.commons.data.dao.query.condition.Condition;
import com.simpletour.platfrom.web.query.support.Query;
import com.simpletour.platfrom.web.query.support.QueryWord;

/**
 * Author:  wangLin
 * Mail  :  wl@simpletour.com
 * Date  :  2016/4/9.
 * Remark:
 */
public class ScopeTemplateQuery extends Query{
    @QueryWord(matchType = Condition.MatchType.like)
    private String name;

    @QueryWord(matchType = Condition.MatchType.like)
    private String permissionName;

    @QueryWord(matchType = Condition.MatchType.like)
    private String moduleName;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPermissionName() {
        return permissionName;
    }

    public void setPermissionName(String permissionName) {
        this.permissionName = permissionName;
    }

    public String getModuleName() {
        return moduleName;
    }

    public void setModuleName(String moduleName) {
        this.moduleName = moduleName;
    }
}
