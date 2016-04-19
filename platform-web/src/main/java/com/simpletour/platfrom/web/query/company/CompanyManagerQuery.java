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

    //管理员账号
    @QueryWord(matchType = Condition.MatchType.like)
    private String jobNo;

    //公司
    @QueryWord(value = "company.name",matchType = Condition.MatchType.like)
    private String companyName;

    public String getJobNo() {
        return jobNo;
    }

    public void setJobNo(String jobNo) {
        this.jobNo = jobNo;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

}
