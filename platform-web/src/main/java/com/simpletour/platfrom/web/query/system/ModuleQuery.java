package com.simpletour.platfrom.web.query.system;

import com.simpletour.commons.data.dao.query.condition.Condition;
import com.simpletour.platfrom.web.query.support.Query;
import com.simpletour.platfrom.web.query.support.QueryWord;

/**
 * Author：XuHui/xuhui@simpletour.com
 * Brief：
 * Date: 2016/4/8
 * Time: 14:49
 */
public class ModuleQuery extends Query {

    @QueryWord(matchType = Condition.MatchType.like)
    private String name;

    @QueryWord(matchType = Condition.MatchType.like)
    private String permissionName;

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
}
