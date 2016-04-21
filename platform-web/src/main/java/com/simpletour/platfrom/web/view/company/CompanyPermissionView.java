package com.simpletour.platfrom.web.view.company;

/**
 * Author:  wangLin
 * Mail  :  wl@simpletour.com
 * Date  :  2016/4/9.
 * Remark: 公司列表中，收索框功能名称需要数据的包装
 */
public class CompanyPermissionView {

    private Long id;

    private String name;

    public CompanyPermissionView(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}
