package com.simpletour.company.web.form.sale;

import com.simpletour.company.web.form.support.BaseForm;

import java.util.Date;

/**
 * @Brief :  销售产品价格的form
 * @Author: liangfei/liangfei@simpletour.com
 * @Date :  2016/4/22 10:26
 * @Since ： ${VERSION}
 * @Remark: ${Remark}
 */
public class AgreementProductPriceForm extends BaseForm {
    /**
     * 主键
     */
    private Long id;
    /**
     * 协议主键
     */
    private Long agreementId;
    /**
     * 产品的主键
     */
    private Long productId;

    /**
     * 类型
     */
    private String type;
    /**
     * 日期
     */
    private Date date;

    /**
     * 成本
     */
    private Integer cost;
    /**
     * 结算价
     */
    private Integer settlement;

    /**
     * 建议价
     */
    private Integer retail;
    /**
     * 备注
     */
    private String remark;
    /**
     * 版本号
     */
    private Integer version;


    public AgreementProductPriceForm() {
    }

    public AgreementProductPriceForm(Long agreementId, Long productId, String type, Date date, Integer cost, Integer settlement, Integer retail, String remark, Integer version) {
        this.agreementId = agreementId;
        this.productId = productId;
        this.type = type;
        this.date = date;
        this.cost = cost;
        this.settlement = settlement;
        this.retail = retail;
        this.remark = remark;
        this.version = version;
    }

    public Long getAgreementId() {
        return agreementId;
    }

    public void setAgreementId(Long agreementId) {
        this.agreementId = agreementId;
    }

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Integer getCost() {
        return cost;
    }

    public void setCost(Integer cost) {
        this.cost = cost;
    }

    public Integer getSettlement() {
        return settlement;
    }

    public void setSettlement(Integer settlement) {
        this.settlement = settlement;
    }

    public Integer getRetail() {
        return retail;
    }

    public void setRetail(Integer retail) {
        this.retail = retail;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public Integer getVersion() {
        return version;
    }

    public void setVersion(Integer version) {
        this.version = version;
    }
}
