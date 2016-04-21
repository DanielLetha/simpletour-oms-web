package com.simpletour.platfrom.web.query.company;


import com.simpletour.commons.data.dao.query.condition.Condition;
import com.simpletour.platfrom.web.query.support.Query;
import com.simpletour.platfrom.web.query.support.QueryWord;

/**
 * Author:  wangLin
 * Mail  :  wl@simpletour.com
 * Date  :  2016/4/11.
 * Remark:
 */
public class CompanyManagerQuery extends Query {

    //公司
    @QueryWord(value = "company.name",matchType = Condition.MatchType.like)
    private String companyName;

    //是公司管理员
    @QueryWord
    private Boolean isAdmin = Boolean.TRUE;

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public Boolean getAdmin() {
        return isAdmin;
    }

    public void setAdmin(Boolean admin) {
        isAdmin = admin;
    }
}
