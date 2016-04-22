package com.simpletour.company.web.form.agreement.product;

import com.simpletour.company.web.form.support.BaseForm;
import com.simpletour.domain.sale.Agreement;
import com.simpletour.domain.sale.AgreementProduct;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Author:  wangLin
 * Mail  :  wl@simpletour.com
 * Date  :  2016/4/22.
 * Remark: 主要用在销售协议列表数据的接收 显示
 */
public class ProductListForm extends BaseForm {

    //销售协议id
    @NotNull(message = "oms.sale.agreement.id.notNull")
    private Long agreementId;

    //产品的集合
    @Valid
    private List<ProductForm> products = new ArrayList<>();

    public ProductListForm(Agreement agreement) {
        if (agreement != null){
            this.agreementId = agreement.getId();
            List<AgreementProduct> agreementProducts = agreement.getAgreementProducts();
            if (agreementProducts!= null && !agreementProducts.isEmpty()){
                this.products = agreementProducts.stream().map(product -> new ProductForm(product)).collect(Collectors.toList());
            }
        }
    }
    public Long getAgreementId() {
        return agreementId;
    }
    public void setAgreementId(Long agreementId) {
        this.agreementId = agreementId;
    }

    public List<ProductForm> getProducts() {
        return products;
    }

    public void setProducts(List<ProductForm> products) {
        this.products = products;
    }
}
