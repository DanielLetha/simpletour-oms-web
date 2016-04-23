package com.simpletour.company.web.view.agreement;

import com.simpletour.domain.product.Product;

import java.util.ArrayList;
import java.util.List;

/**
 * Author:  wangLin
 * Mail  :  wl@simpletour.com
 * Date  :  2016/4/22.
 * Remark:
 */
public class ProductQueryListView {

    private List<ProductQuerElementView> list = new ArrayList<>();

    public ProductQueryListView(List<Product> products) {
        if (products != null && !products.isEmpty()){
            products.stream().forEach(product -> list.add(new ProductQuerElementView(product)));
        }
    }

    public List<ProductQuerElementView> getList() {
        return list;
    }

    public void setList(List<ProductQuerElementView> list) {
        this.list = list;
    }
}
