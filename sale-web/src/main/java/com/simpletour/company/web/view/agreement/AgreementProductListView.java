package com.simpletour.company.web.view.agreement;

import com.simpletour.commons.data.domain.DomainPage;
import com.simpletour.company.web.query.agreement.AgreementProductQuery;
import com.simpletour.company.web.view.BaseListView;
import com.simpletour.domain.company.Company;
import com.simpletour.domain.sale.AgreementProduct;

/**
 * Author:  wangLin
 * Mail  :  wl@simpletour.com
 * Date  :  2016/4/22.
 * Remark:
 */
public class AgreementProductListView extends BaseListView<AgreementProductElementView>{

    public AgreementProductListView(DomainPage<AgreementProduct> domainPage, AgreementProductQuery query){
        super(domainPage,query);
        if (domainPage.getDomains() != null && !domainPage.getDomains().isEmpty()){
            domainPage.getDomains().stream().forEach(agreementProduct -> subViews.add(new AgreementProductElementView(agreementProduct)));
        }
    }
}
