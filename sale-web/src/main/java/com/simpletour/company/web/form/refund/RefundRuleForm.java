package com.simpletour.company.web.form.refund;

import com.simpletour.company.web.enums.FormModeType;
import com.simpletour.company.web.form.support.BaseForm;
import com.simpletour.domain.sale.RefundRule;
import org.hibernate.validator.constraints.Range;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

/**
 * User: XuHui/xuhui@simpletour.com
 * Date: 2016/4/20
 * Time: 12:30
 */
public class RefundRuleForm extends BaseForm{
    @NotNull(message = "{refund.rule.timing.null}")
    @Min(value = 0,message ="{refund.rule.timing.min}")
    private Integer timing;

    @NotNull(message="{refund.rule.ration.null}")
    @Range(min = 0,max = 100,message = "{refund.rule.ration.range}")
    private Integer ration;

    private Integer version;

    public RefundRuleForm(){}

    public RefundRuleForm(RefundRule refundRule){
        this.id=refundRule.getId();
        this.version=refundRule.getVersion();
        this.timing=refundRule.getTiming()/24;
        this.ration=refundRule.getRation();
    }

    public RefundRule as(){
        RefundRule refundRule=new RefundRule(timing*24,ration);
        if (mode.equals(FormModeType.UPDATE.getValue())){
            refundRule.setId(id);
            refundRule.setVersion(version);
        }
        return refundRule;
    }

    public Integer getTiming() {
        return timing;
    }

    public void setTiming(Integer timing) {
        this.timing = timing;
    }

    public Integer getRation() {
        return ration;
    }

    public void setRation(Integer ration) {
        this.ration = ration;
    }

    public Integer getVersion() {
        return version;
    }

    public void setVersion(Integer version) {
        this.version = version;
    }
}
