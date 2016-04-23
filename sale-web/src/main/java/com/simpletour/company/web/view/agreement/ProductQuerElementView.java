package com.simpletour.company.web.view.agreement;

import com.simpletour.domain.product.Product;

/**
 * Author:  wangLin
 * Mail  :  wl@simpletour.com
 * Date  :  2016/4/22.
 * Remark:
 */
public class ProductQuerElementView {

    //产品id
    private Long productId;

    //类型
    private String type;

    //产品名称
    private String name;

    //目的地
    private String arrive;

    //状态(上线 下线)
    private String online;

    public ProductQuerElementView(Product product) {
        if (product != null){
            this.productId = product.getId();
            this.type = Product.Type.valueOf(product.getType()).getRemark();
            this.name = product.getName();
            this.arrive = product.getArrive();
            this.online = product.getOnline() ? "上线" : "下线";
        }
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getArrive() {
        return arrive;
    }

    public void setArrive(String arrive) {
        this.arrive = arrive;
    }

    public String getOnline() {
        return online;
    }

    public void setOnline(String online) {
        this.online = online;
    }
}
