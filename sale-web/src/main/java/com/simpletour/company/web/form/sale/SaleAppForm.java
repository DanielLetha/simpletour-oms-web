package com.simpletour.company.web.form.sale;


import com.simpletour.company.web.annotation.Fax;
import com.simpletour.company.web.annotation.Mobile;
import com.simpletour.company.web.enums.FormModeType;
import com.simpletour.company.web.form.support.BaseForm;
import com.simpletour.domain.sale.SaleApp;
import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.NotNull;

/**
 * @Brief :  ${用途}
 * @Author: liangfei/liangfei@simpletour.com
 * @Date :  2016/4/8 14:32
 * @Since ： ${VERSION}
 * @Remark: ${Remark}
 */
public class SaleAppForm extends BaseForm {
    /**
     * 编号
     */
    private Long id;
    /**
     * 销售端名称
     */
    @NotBlank(message = "{pms.saleApp.name.notnull}")
    @Length(min = 2,max = 20,message = "{pms.saleApp.name.length}")
    private String name;
    /**
     * 对接人
     */
    @NotBlank(message = "{pms.saleApp.contact.notnull}")
    @Length(min = 2,max = 20,message = "{pms.saleApp.contact.length}")
    private String contact;
    /**
     * 电话
     */
    @NotBlank(message = "{pms.saleApp.mobile.notnull}")
    @Mobile(message = "{pms.saleApp.mobile.format}")
    private String mobile;

    /**
     * 订单失效时间
     */
    @NotNull(message = "{pms.saleApp.reserveTime.notnull}")
    private Integer reserveTime;
    /**
     * 传真
     */
    @Fax(message = "{pms.saleApp.fax.format}")
    @Length(max = 16,message = "{pms.saleApp.fax.length}")
    private String fax;
    /**
     * 邮箱
     */
    @Email(message = "{pms.saleApp.mail.format}")
    @Length(max = 32,message = "{pms.saleApp.email.length}")
    private String email;
    /**
     * 其他方式
     */
    @Length(max = 100,message = "{pms.saleApp.link.length}")
    private String link;
    /**
     * 备注
     */
    private String remark;

    private String key;

    private String secret;

    private Integer version;

    public SaleAppForm() {
    }

    public SaleAppForm(SaleApp saleApp) {
        this.key = saleApp.getKey();
        this.secret = saleApp.getSecret();
        this.id = saleApp.getId();
        this.name = saleApp.getName();
        this.contact = saleApp.getContact();
        this.mobile = saleApp.getMobile();
        this.fax = saleApp.getFax();
        this.reserveTime = saleApp.getReserveTime();
        this.email = saleApp.getEmail();
        this.link = saleApp.getLink();
        this.remark = saleApp.getRemark();
        this.version = saleApp.getVersion();
    }


    public SaleApp as() {
        SaleApp saleApp = new SaleApp();
        if (this.mode.equals(FormModeType.UPDATE.getValue())) {
            saleApp.setId(this.id);
        }
        saleApp.setReserveTime(this.reserveTime);
        saleApp.setKey(this.key);
        saleApp.setSecret(this.secret);
        saleApp.setName(this.name);
        saleApp.setContact(this.contact);
        saleApp.setMobile(this.mobile);
        saleApp.setFax(this.fax);
        saleApp.setEmail(this.email);
        saleApp.setLink(this.link);
        saleApp.setRemark(this.remark);
        saleApp.setVersion(this.version);
        return saleApp;
    }


    public Integer getReserveTime() {
        return reserveTime;
    }

    public void setReserveTime(Integer reserveTime) {
        this.reserveTime = reserveTime;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getSecret() {
        return secret;
    }

    public void setSecret(String secret) {
        this.secret = secret;
    }

    @Override
    public Long getId() {
        return id;
    }

    @Override
    public void setId(Long id) {
        this.id = id;
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

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getFax() {
        return fax;
    }

    public void setFax(String fax) {
        this.fax = fax;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
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
