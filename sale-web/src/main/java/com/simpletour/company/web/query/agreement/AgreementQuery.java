package com.simpletour.company.web.query.agreement;

import com.simpletour.commons.data.dao.query.condition.Condition;
import com.simpletour.company.web.query.support.QueryExt;
import com.simpletour.company.web.query.support.QueryWord;

/**
 * Created by YuanYuan/yuanyuan@simpletour.com on 2016/4/20.
 *
 * @since 2.0-SNAPSHOT
 */
public class AgreementQuery extends QueryExt {
    /**
     * 销售端名称
     */
    @QueryWord(value = "saleApp.name", matchType = Condition.MatchType.like)
    private String appName;
    /**
     * 协议状态
     */
    @QueryWord(value = "c.enabled")
    private Boolean enabled;

    public String getAppName() {
        return appName;
    }

    public void setAppName(String appName) {
        this.appName = appName;
    }

    public Boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(Boolean enabled) {
        this.enabled = enabled;
    }
}
