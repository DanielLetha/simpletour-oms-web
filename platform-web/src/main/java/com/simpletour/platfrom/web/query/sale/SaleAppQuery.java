package com.simpletour.platfrom.web.query.sale;

import com.simpletour.commons.data.dao.query.condition.Condition;
import com.simpletour.platfrom.web.query.support.Query;
import com.simpletour.platfrom.web.query.support.QueryWord;

import java.util.HashMap;
import java.util.Map;

/**
 * @Brief : 销售端查询
 * @Author: liangfei/liangfei@simpletour.com
 * @Date :  2016/4/6 10:59
 * @Since ： ${VERSION}
 * @Remark: ${Remark}
 */
public class SaleAppQuery extends Query {


    @QueryWord(matchType = Condition.MatchType.like)
    private String name;
    @QueryWord(matchType = Condition.MatchType.like)
    private String contact;

    public Map<String, Object> asMap() {
        Map map = new HashMap<>();
        if(this.name != null){
            map.put("name",this.name);
        }
        if(this.contact != null){
            map.put("contact",this.contact);

        }

        return map;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }
}
