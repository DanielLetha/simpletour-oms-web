package com.simpletour.company.web.query.refund;

import com.simpletour.commons.data.dao.query.condition.Condition;
import com.simpletour.company.web.query.support.QueryExt;
import com.simpletour.company.web.query.support.QueryWord;

/**
 * User: XuHui/xuhui@simpletour.com
 * Date: 2016/4/20
 * Time: 12:01
 */
public class RefundPolicyQuery extends QueryExt {

    @QueryWord(value = "c.name",matchType = Condition.MatchType.like)
    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
