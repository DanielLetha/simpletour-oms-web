package com.simpletour.company.web.query.agreement;

import com.simpletour.commons.data.dao.query.condition.Condition;
import com.simpletour.company.web.query.support.Query;
import com.simpletour.company.web.query.support.QueryWord;

/**
 * Author:  wangLin
 * Mail  :  wl@simpletour.com
 * Date  :  2016/4/22.
 * Remark:
 */
public class ProductQuery extends Query {

    //产品类型
    @QueryWord(matchType = Condition.MatchType.like)
    private String type;

    //产品名称
    @QueryWord(matchType = Condition.MatchType.like)
    private String name;

    //目的地
    @QueryWord(matchType = Condition.MatchType.like)
    private String arrive;

    //状态
    @QueryWord
    private Boolean online;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getArrive() {
        return arrive;
    }

    public void setArrive(String arrive) {
        this.arrive = arrive;
    }

    public Boolean getOnline() {
        return online;
    }

    public void setOnline(Boolean online) {
        this.online = online;
    }
}
