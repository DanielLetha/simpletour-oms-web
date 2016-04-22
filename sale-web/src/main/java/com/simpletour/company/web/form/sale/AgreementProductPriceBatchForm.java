package com.simpletour.company.web.form.sale;

import com.simpletour.company.web.form.support.BaseForm;

import java.util.Date;

/**
 * @Brief :  ${用途}
 * @Author: liangfei/liangfei@simpletour.com
 * @Date :  2016/4/22 11:54
 * @Since ： ${VERSION}
 * @Remark: ${Remark}
 */
public class AgreementProductPriceBatchForm  extends BaseForm{

    private Date startDate;

    private Date endDate;

    private boolean all;

    private boolean Sunday;

    private boolean Monday;

    private boolean Tuesday;

    private boolean Wednesday;

    private boolean Thursday;

    private boolean Friday;

    private boolean Saturday;

    private Integer adultCost;

    private Integer adultSettlement;

    private Integer adultRetail;

    private Integer childCost;

    private Integer childSettlement;

    private Integer childRetail;

    public AgreementProductPriceBatchForm() {
    }

    public AgreementProductPriceBatchForm(Date startDate, Date endDate, boolean all, boolean sunday, boolean monday, boolean tuesday, boolean wednesday, boolean thursday, boolean friday, boolean saturday, Integer adultCost, Integer adultSettlement, Integer adultRetail, Integer childCost, Integer childSettlement, Integer childRetail) {
        this.startDate = startDate;
        this.endDate = endDate;
        this.all = all;
        Sunday = sunday;
        Monday = monday;
        Tuesday = tuesday;
        Wednesday = wednesday;
        Thursday = thursday;
        Friday = friday;
        Saturday = saturday;
        this.adultCost = adultCost;
        this.adultSettlement = adultSettlement;
        this.adultRetail = adultRetail;
        this.childCost = childCost;
        this.childSettlement = childSettlement;
        this.childRetail = childRetail;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public boolean isAll() {
        return all;
    }

    public void setAll(boolean all) {
        this.all = all;
    }

    public boolean isSunday() {
        return Sunday;
    }

    public void setSunday(boolean sunday) {
        Sunday = sunday;
    }

    public boolean isMonday() {
        return Monday;
    }

    public void setMonday(boolean monday) {
        Monday = monday;
    }

    public boolean isTuesday() {
        return Tuesday;
    }

    public void setTuesday(boolean tuesday) {
        Tuesday = tuesday;
    }

    public boolean isWednesday() {
        return Wednesday;
    }

    public void setWednesday(boolean wednesday) {
        Wednesday = wednesday;
    }

    public boolean isThursday() {
        return Thursday;
    }

    public void setThursday(boolean thursday) {
        Thursday = thursday;
    }

    public boolean isFriday() {
        return Friday;
    }

    public void setFriday(boolean friday) {
        Friday = friday;
    }

    public boolean isSaturday() {
        return Saturday;
    }

    public void setSaturday(boolean saturday) {
        Saturday = saturday;
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
}
