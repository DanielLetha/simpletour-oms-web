package com.simpletour.company.web.view.agreement;

import com.simpletour.company.web.view.BaseElementView;
import com.simpletour.domain.product.Product;
import com.simpletour.domain.sale.Agreement;
import com.simpletour.domain.sale.AgreementProduct;

/**
 * Author:  wangLin
 * Mail  :  wl@simpletour.com
 * Date  :  2016/4/22.
 * Remark:
 */
public class AgreementProductElementView extends BaseElementView<AgreementProduct> {

    //销售协议产品id
    private Long id;

    //产品名称
    private String productName;

    //销售端名称
    private String saleAppName;

    //类型(车,住，景，餐，娱，其他)
    private String productType;

    public AgreementProductElementView(AgreementProduct agreementProduct) {
        if (agreementProduct != null){
            this.id = agreementProduct.getId();
            Product product = agreementProduct.getProduct();
            Agreement agreement = agreementProduct.getAgreement();
            if (product != null){
                this.productName = product.getName();
                handleProductName(product.getType());
            }
            if (agreement != null && agreement.getSaleApp() != null){
                this.saleAppName = agreement.getSaleApp().getName();
            }
        }
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getSaleAppName() {
        return saleAppName;
    }

    public void setSaleAppName(String saleAppName) {
        this.saleAppName = saleAppName;
    }

    public String getProductType() {
        return productType;
    }

    public void setProductType(String productType) {
        this.productType = productType;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    /**
     * 处理产品的类型
     * @param productType
     */
    private void handleProductName(String productType){
        switch (productType){
            case "bus":
                this.productType = "车位";
                break;
            case "hotel":
                this.productType = "住宿";
                break;
            case "scenic":
                this.productType = "景点";
                break;
            case "catering":
                this.productType = "餐饮";
                break;
            case "entertainment":
                this.productType = "娱乐";
                break;
            case "other":
                this.productType = "其他";
                break;
        }
    }
}
