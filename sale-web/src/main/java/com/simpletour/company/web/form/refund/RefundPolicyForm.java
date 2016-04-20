package com.simpletour.company.web.form.refund;

import com.simpletour.company.web.enums.FormModeType;
import com.simpletour.company.web.form.support.BaseForm;
import com.simpletour.domain.sale.RefundPolicy;
import com.simpletour.domain.sale.RefundRule;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.stream.Collectors;

/**
 * User: XuHui/xuhui@simpletour.com
 * Date: 2016/4/20
 * Time: 12:30
 */
public class RefundPolicyForm extends BaseForm{

    @NotBlank(message = "{}")
    @Length(min = 2,max = 20,message = "{}")
    private String name;

    @NotNull(message = "{}")
    private Long deadLine;

    private String instruction;

    private String remark;

    private Integer version;

    @Valid
    @NotNull(message = "{}")
    private List<RefundRuleForm> refundRuleForms;

    public RefundPolicyForm(){}

    public RefundPolicyForm(RefundPolicy refundPolicy){
        this.id=refundPolicy.getId();
        this.version=refundPolicy.getVersion();
        this.name=refundPolicy.getName();
        this.deadLine=refundPolicy.getDeadline();
        this.instruction=refundPolicy.getInstruction();
        this.remark=refundPolicy.getRemark();
        this.refundRuleForms=refundPolicy.getRefundRules().stream()
                .map(RefundRuleForm::new).collect(Collectors.toList());
    }

    public RefundPolicy as(){
        RefundPolicy refundPolicy=new RefundPolicy(name,deadLine,instruction,remark);
        List<RefundRule> refundRules=refundRuleForms.stream()
                .map(tmp->{
                    tmp.setMode(mode);
                    return tmp.as();
                }).collect(Collectors.toList());
        if(mode.equals(FormModeType.UPDATE.getValue())){
            refundPolicy.setId(id);
            refundPolicy.setVersion(version);
        }
        return refundPolicy;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getDeadLine() {
        return deadLine;
    }

    public void setDeadLine(Long deadLine) {
        this.deadLine = deadLine;
    }

    public String getInstruction() {
        return instruction;
    }

    public void setInstruction(String instruction) {
        this.instruction = instruction;
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

    public List<RefundRuleForm> getRefundRuleForms() {
        return refundRuleForms;
    }

    public void setRefundRuleForms(List<RefundRuleForm> refundRuleForms) {
        this.refundRuleForms = refundRuleForms;
    }
}
