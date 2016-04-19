package com.simpletour.platfrom.web.view.company;

import com.simpletour.commons.data.domain.DomainPage;
import com.simpletour.domain.company.Company;
import com.simpletour.platfrom.web.query.company.CompanyQuery;
import com.simpletour.platfrom.web.view.BaseListView;

/**
 * Author:  wangLin
 * Mail  :  wl@simpletour.com
 * Date  :  2016/4/5.
 * Remark:
 */
public class CompanyListView extends BaseListView<CompanyElementView>{

    public CompanyListView(DomainPage<Company> domainPage, CompanyQuery query){
        super(domainPage,query);
        if (domainPage.getDomains() != null && !domainPage.getDomains().isEmpty()){
            domainPage.getDomains().stream().forEach(company -> subViews.add(new CompanyElementView(company,query)));
        }
    }
}
