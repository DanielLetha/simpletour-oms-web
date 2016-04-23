package com.simpletour.company.web.form.sale;

import com.simpletour.biz.sale.bo.AgreementPriceBo;
import com.simpletour.biz.sale.bo.Price;
import com.simpletour.company.web.enums.FormModeType;
import com.simpletour.company.web.form.support.BaseForm;
import com.simpletour.domain.sale.AgreementProduct;
import com.simpletour.domain.sale.AgreementProductPrice;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

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
    private Long adultId;

    private Long childId;
    /**
     * 协议产品主键
     */
    private Long agreementProductId;

    /**
     * 日期
     */
    private Date date;

    /**
     * 成本
     */
    private Integer adultCost;
    /**
     * 结算价
     */
    private Integer adultSettlement;

    /**
     * 建议价
     */
    private Integer adultRetail;


    private Integer childCost;

    private Integer childSettlement;


    private Integer childRetail;

    /**
     * 版本号
     */
    private Integer adultVersion;

    private Integer childVersion;

    public AgreementProductPriceForm() {
    }

    public AgreementPriceBo as(){
        AgreementPriceBo agreementPriceBo = new AgreementPriceBo();
        Map<AgreementProductPrice.Type,Price> map = new HashMap<>();
        Price adultPrice = null;
        Price childPrice = null;
        if(this.mode.equals(FormModeType.UPDATE)){
            adultPrice = new Price(this.adultId,this.adultCost,this.adultSettlement,this.adultRetail,this.adultVersion);
            childPrice = new Price(this.childId,this.childCost,this.childSettlement,this.childRetail,this.childVersion);
        }else{
            adultPrice = new Price(this.adultCost,this.adultSettlement,this.adultRetail,this.adultVersion);
            childPrice = new Price(this.childCost,this.childSettlement,this.childRetail,this.childVersion);
        }

        map.put(AgreementProductPrice.Type.ADULT,adultPrice);
        map.put(AgreementProductPrice.Type.CHILD,childPrice);
        agreementPriceBo.setPriceMap(map);
        agreementPriceBo.setDate(this.date);
        return agreementPriceBo;
    }


    public AgreementProductPriceForm(AgreementPriceBo agreementPriceBo) {
        this.date = agreementPriceBo.getDate();
        this.agreementProductId = agreementPriceBo.getAgreementProduct().getId();
        this.adultId = agreementPriceBo.getPriceMap().get(AgreementProductPrice.Type.ADULT).getId();
        this.adultCost = agreementPriceBo.getPriceMap().get(AgreementProductPrice.Type.ADULT).getCost();
        this.adultSettlement = agreementPriceBo.getPriceMap().get(AgreementProductPrice.Type.ADULT).getSettlement();
        this.adultVersion = agreementPriceBo.getPriceMap().get(AgreementProductPrice.Type.ADULT).getVersion();
        this.childId = agreementPriceBo.getPriceMap().get(AgreementProductPrice.Type.CHILD).getId();
        this.childCost = agreementPriceBo.getPriceMap().get(AgreementProductPrice.Type.CHILD).getCost();
        this.childSettlement = agreementPriceBo.getPriceMap().get(AgreementProductPrice.Type.CHILD).getSettlement();
        this.childRetail = agreementPriceBo.getPriceMap().get(AgreementProductPrice.Type.CHILD).getRetail();
        this.childVersion = agreementPriceBo.getPriceMap().get(AgreementProductPrice.Type.CHILD).getVersion();
    }

    public Long getAdultId() {
        return adultId;
    }

    public void setAdultId(Long adultId) {
        this.adultId = adultId;
    }

    public Long getChildId() {
        return childId;
    }

    public void setChildId(Long childId) {
        this.childId = childId;
    }

    public Long getAgreementProductId() {
        return agreementProductId;
    }

    public void setAgreementProductId(Long agreementProductId) {
        this.agreementProductId = agreementProductId;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Integer getAdultCost() {
        return adultCost;
    }

    public void setAdultCost(Integer adultCost) {
        this.adultCost = adultCost;
    }

    public Integer getAdultSettlement() {
        return adultSettlement;
    }

    public void setAdultSettlement(Integer adultSettlement) {
        this.adultSettlement = adultSettlement;
    }

    public Integer getAdultRetail() {
        return adultRetail;
    }

    public void setAdultRetail(Integer adultRetail) {
        this.adultRetail = adultRetail;
    }

    public Integer getChildCost() {
        return childCost;
    }

    public void setChildCost(Integer childCost) {
        this.childCost = childCost;
    }

    public Integer getChildSettlement() {
        return childSettlement;
    }

    public void setChildSettlement(Integer childSettlement) {
        this.childSettlement = childSettlement;
    }

    public Integer getChildRetail() {
        return childRetail;
    }

    public void setChildRetail(Integer childRetail) {
        this.childRetail = childRetail;
    }

    public Integer getAdultVersion() {
        return adultVersion;
    }

    public void setAdultVersion(Integer adultVersion) {
        this.adultVersion = adultVersion;
    }

    public Integer getChildVersion() {
        return childVersion;
    }

    public void setChildVersion(Integer childVersion) {
        this.childVersion = childVersion;
    }
}
