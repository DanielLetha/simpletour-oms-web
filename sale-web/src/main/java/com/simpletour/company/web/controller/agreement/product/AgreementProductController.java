package com.simpletour.company.web.controller.agreement.product;

import com.alibaba.fastjson.JSON;
import com.simpletour.commons.data.dao.query.ConditionOrderByQuery;
import com.simpletour.commons.data.domain.DomainPage;
import com.simpletour.commons.data.exception.BaseSystemException;
import com.simpletour.company.web.controller.support.BaseController;
import com.simpletour.company.web.controller.support.BaseDataResponse;
import com.simpletour.company.web.controller.support.PageHelper;
import com.simpletour.company.web.enums.Option;
import com.simpletour.company.web.form.agreement.product.ProductListForm;
import com.simpletour.company.web.query.agreement.AgreementProductQuery;
import com.simpletour.company.web.query.agreement.ProductQuery;
import com.simpletour.company.web.query.sale.SaleAppQuery;
import com.simpletour.company.web.util.OptionsUtil;
import com.simpletour.company.web.view.agreement.AgreementProductListView;
import com.simpletour.company.web.view.agreement.ProductQueryListView;
import com.simpletour.company.web.view.agreement.SaleAppView;
import com.simpletour.domain.product.Product;
import com.simpletour.domain.sale.Agreement;
import com.simpletour.domain.sale.AgreementProduct;
import com.simpletour.domain.sale.SaleApp;
import com.simpletour.service.product.IProductService;
import com.simpletour.service.sale.IAgreementProductService;
import com.simpletour.service.sale.IAgreementService;
import com.simpletour.service.sale.ISaleAppService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Author:  wangLin
 * Mail  :  wl@simpletour.com
 * Date  :  2016/4/21.
 * Remark:
 */
@Controller
@RequestMapping("/agreement/product")
public class AgreementProductController extends BaseController {

    private static final String DOMAIN = "销售协议-产品";
    private static final String LIST_URL = "/agreement/product/list";

    @Resource
    private IAgreementProductService agreementProductService;

    @Resource
    private ISaleAppService saleAppService;

    @Resource
    private IAgreementService agreementService;

    @Resource
    private IProductService productService;

    /**
     * 该方法主要用于在销售协议对产品列表的操作(添加和编辑)
     */
    @RequestMapping(value = "/products/{id}", method = RequestMethod.GET)
    public String addProducts4Agreement(@PathVariable Long id, Model model) {
        this.setPageTitle(model, "销售协议-产品列表");
        this.enableGoBack(model);
        //产品类型下拉
        List<Option> productTypeOptions = OptionsUtil.addAllToEnumTypes("产品类型", "","", Product.Type.class);
        //产品状态下拉
        List<Option> statusOptions = OptionsUtil.getBooleanOption("状态", "上线", "下线", null);
        Optional<Agreement> agreement = agreementService.getAgreementById(id);
        if (agreement.isPresent()) {
            ProductListForm productListForm = new ProductListForm(agreement.get());
            model.addAttribute("productListForm", productListForm);
            model.addAttribute("productTypeOptions", productTypeOptions);
            model.addAttribute("statusOptions", statusOptions);
            return "/agreement/product/add";
        }
        return this.error();
    }

    @ResponseBody
    @RequestMapping(value = "/products", method = RequestMethod.POST)
    public BaseDataResponse listProducts(@RequestBody ProductQuery query) {
        try {
            DomainPage<Product> page = productService.getProductByConditionPage(query.asConditison(), query.getIndex(), query.getSize());
            return BaseDataResponse.ok().data(new ProductQueryListView(page.getDomains()).getList());
        } catch (BaseSystemException e) {
            return BaseDataResponse.fail().data(e.getMessage());
        }
    }

    @RequestMapping(value = {"", "/list"})
    public String list(AgreementProductQuery query, Model model) {
        this.setPageTitle(model, "产品列表");
        DomainPage<AgreementProduct> domainPage = agreementProductService.queryAgreementProductPagesByConditions(query.asQuery(ConditionOrderByQuery.class));

        //产品类型下拉框的处理
        List<Option> productTypeOptions = OptionsUtil.addAllToEnumTypes("产品类型", "", query.getProductType(), Product.Type.class);

        //得到所有的saleAppList,并且包装一下
        List<SaleApp> saleApps = saleAppService.querySaleAppList(new SaleAppQuery().asQuery(ConditionOrderByQuery.class));
        List<SaleAppView> saleAppViews = new ArrayList<>();
        if (saleApps != null && !saleApps.isEmpty()){
            saleApps.stream().forEach(saleApp -> saleAppViews.add(new SaleAppView(saleApp.getId(),saleApp.getName())) );
        }

        AgreementProductListView listView = new AgreementProductListView(domainPage, query);
        model.addAttribute("page",listView);
        model.addAttribute("pageHelper", new PageHelper(domainPage));
        model.addAttribute("saleAppViews", saleAppViews);
        model.addAttribute("productTypeOptions", productTypeOptions);
        return "/agreement/product/list";
    }
}
