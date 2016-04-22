//package com.simpletour.company.web.controller.agreement;
//
//import com.simpletour.commons.data.dao.query.ConditionOrderByQuery;
//import com.simpletour.commons.data.domain.DomainPage;
//import com.simpletour.company.web.controller.support.BaseController;
//import com.simpletour.company.web.controller.support.PageHelper;
//import com.simpletour.company.web.enums.Option;
//import com.simpletour.company.web.query.agreement.AgreementProductQuery;
//import com.simpletour.company.web.query.agreement.AgreementQuery;
//import com.simpletour.company.web.util.OptionsUtil;
//import com.simpletour.company.web.view.agreement.AgreementProductListView;
//import com.simpletour.company.web.view.agreement.SaleAppView;
//import com.simpletour.dao.company.query.CompanyDaoQuery;
//import com.simpletour.domain.company.Company;
//import com.simpletour.domain.company.Permission;
//import com.simpletour.domain.order.OrderStatus;
//import com.simpletour.domain.product.Product;
//import com.simpletour.domain.sale.Agreement;
//import com.simpletour.domain.sale.AgreementProduct;
//import com.simpletour.domain.sale.SaleApp;
//import com.simpletour.service.sale.IAgreementProductService;
//import com.simpletour.service.sale.IAgreementService;
//import com.simpletour.service.sale.ISaleAppService;
//import org.springframework.stereotype.Controller;
//import org.springframework.ui.Model;
//import org.springframework.web.bind.annotation.RequestMapping;
//
//import javax.annotation.Resource;
//import java.util.ArrayList;
//import java.util.List;
//
///**
// * Author:  wangLin
// * Mail  :  wl@simpletour.com
// * Date  :  2016/4/21.
// * Remark:
// */
//@Controller
//@RequestMapping("/agreement/product")
//public class AgreementProductController extends BaseController {
//
//    private static final String DOMAIN = "销售协议-产品";
//    private static final String LIST_URL = "/agreement/product/list";
//
//    @Resource
//    private IAgreementProductService agreementProductService;
//
//    @Resource
//    private ISaleAppService saleAppService;
//
//    @RequestMapping(value = {"", "list"})
//    public String list(AgreementProductQuery query, Model model) {
//        this.setPageTitle(model, "产品列表");
//        DomainPage<AgreementProduct> domainPage = agreementProductService.queryAgreementProductPagesByConditions(query.asQuery(ConditionOrderByQuery.class));
//
//        //产品类型下拉框的处理
//        List<Option> productTypeOptions = OptionsUtil.addAllToEnumTypes("产品类型", "", query.getProductType(), Product.Type.class);
//
//        //TODO 得到所有的saleAppList,并且包装一下
//        List<SaleApp> saleApps = new ArrayList<>(); //TODO 需要从数据库查出来
//        List<SaleAppView> saleAppViews = new ArrayList<>();
//        if (saleApps != null && !saleApps.isEmpty()){
//            saleApps.stream().forEach(saleApp -> saleAppViews.add(new SaleAppView(saleApp.getId(),saleApp.getName())) );
//        }
//
//        AgreementProductListView listView = new AgreementProductListView(domainPage, query);
//        model.addAttribute("page",listView);
//        model.addAttribute("pageHelper", new PageHelper(domainPage));
//        model.addAttribute("saleAppList", saleAppViews);
//        model.addAttribute("productTypeOptions", productTypeOptions);
//        return "/company/list";
//    }
//}
