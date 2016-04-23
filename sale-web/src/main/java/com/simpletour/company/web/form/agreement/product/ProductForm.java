package com.simpletour.company.web.form.agreement.product;

import com.simpletour.company.web.form.support.BaseForm;
import com.simpletour.domain.sale.AgreementProduct;
import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.NotNull;

/**
 * Author:  wangLin
 * Mail  :  wl@simpletour.com
 * Date  :  2016/4/22.
 * Remark:
 */
public class ProductForm {

    //产品id
    @NotNull(message = "oms.sale.agreement.product.id.notNull")
    private Long productId;

    //产品名称
    @NotBlank(message = "oms.sale.agreement.product.name.notBlank")
    private String name;

    //销售协议产品的版本号
    private Integer version;

    public ProductForm(AgreementProduct agreementProduct) {
        if (agreementProduct != null){
            this.productId = agreementProduct.getId();
            this.version = agreementProduct.getVersion();
            if (agreementProduct.getProduct() != null){
                this.name = agreementProduct.getProduct().getName();
            }
        }
    }

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getVersion() {
        return version;
    }

    public void setVersion(Integer version) {
        this.version = version;
    }
}
