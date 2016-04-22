package com.simpletour.company.web.form.agreement;

import com.simpletour.company.web.enums.FormModeType;
import com.simpletour.company.web.form.support.BaseForm;
import com.simpletour.domain.sale.Agreement;
import com.simpletour.domain.sale.SaleApp;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;

/**
 * Created by YuanYuan/yuanyuan@simpletour.com on 2016/4/20.
 *
 * @since 2.0-SNAPSHOT
 */
public class AgreementForm extends BaseForm {
    /**
     * 销售端名称
     */
    @NotBlank(message = "{agreement.appName.null}")
    private String appName;
    /**
     * 销售端ID
     */
    private Long appId;
    /**
     * 协议状态
     */
    private String status;
    /**
     * 备注
     */
    @Length(max = 10000, message = "{pms.common.object.remark.length}")
    private String remark;

    private Integer version;

    public AgreementForm() {
    }

    public AgreementForm(Agreement agreement) {
        this.id = agreement.getId();
        this.appName = agreement.getSaleApp().getName();
        this.appId = agreement.getSaleApp().getId();
        this.status = agreement.isEnabled().toString();
        this.remark = agreement.getRemark();
        this.version = agreement.getVersion();
    }

    public Agreement as() {
        SaleApp app = new SaleApp();
        app.setId(appId);
        app.setName(appName);
        Agreement agreement = new Agreement(app, !status.equals("false"), remark);
        if (mode.equals(FormModeType.UPDATE.getValue())) {
            agreement.setId(id);
            agreement.setVersion(version);
        }
        return agreement;
    }

    public String getAppName() {
        return appName;
    }

    public void setAppName(String appName) {
        this.appName = appName;
    }

    public Long getAppId() {
        return appId;
    }

    public void setAppId(Long appId) {
        this.appId = appId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
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
