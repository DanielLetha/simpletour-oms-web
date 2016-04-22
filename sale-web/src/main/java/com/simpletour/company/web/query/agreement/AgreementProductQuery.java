package com.simpletour.company.web.query.agreement;

import com.simpletour.commons.data.dao.query.condition.Condition;
import com.simpletour.company.web.query.support.Query;
import com.simpletour.company.web.query.support.QueryExt;
import com.simpletour.company.web.query.support.QueryWord;

/**
 * Author:  wangLin
 * Mail  :  wl@simpletour.com
 * Date  :  2016/4/22.
 * Remark:
 */
public class AgreementProductQuery extends Query {

    //产品类型(全模糊,下拉) 例如： bus
    @QueryWord(value = "product.type",matchType = Condition.MatchType.like)
    private String productType;

    //销售端名称(全模糊，类似订单的渠道名称)
    @QueryWord(value = "agreement.saleApp.name",matchType = Condition.MatchType.like)
    private String saleAppName;

    //产品名称(全模糊)
    @QueryWord(value = "product.name",matchType = Condition.MatchType.like)
    private String productName;

    private Long saleAppId;

    public String getProductType() {
        return productType;
    }

    public void setProductType(String productType) {
        this.productType = productType;
    }

    public String getSaleAppName() {
        return saleAppName;
    }

    public void setSaleAppName(String saleAppName) {
        this.saleAppName = saleAppName;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public Long getSaleAppId() {
        return saleAppId;
    }

    public void setSaleAppId(Long saleAppId) {
        this.saleAppId = saleAppId;
    }
}
