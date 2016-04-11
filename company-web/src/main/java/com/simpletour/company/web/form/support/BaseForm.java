package com.simpletour.company.web.form.support;

/**
 * Created by sczly on 2015/6/3.
 */
public class BaseForm {
    //主键，如果存在
    protected Long id;
    //form的作用，添加:add,更新:update,删除:del
    protected String mode;

    protected Long companyId;

    public BaseForm() {}

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getMode() {
        return mode;
    }

    public void setMode(String mode) {
        this.mode = mode;
    }

    public Long getCompanyId() {
        return companyId;
    }

    public void setCompanyId(Long companyId) {
        this.companyId = companyId;
    }
}
