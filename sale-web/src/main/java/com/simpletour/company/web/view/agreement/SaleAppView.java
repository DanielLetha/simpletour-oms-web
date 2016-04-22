package com.simpletour.company.web.view.agreement;

import com.simpletour.domain.sale.SaleApp;

/**
 * Author:  wangLin
 * Mail  :  wl@simpletour.com
 * Date  :  2016/4/22.
 * Remark: 包装销售协议产品列表 中销售端输入框需要的数据
 */
public class SaleAppView {

    private Long id;

    private String name;

    public SaleAppView(Long id,String name) {
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
