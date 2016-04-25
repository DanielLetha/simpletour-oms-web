package com.simpletour.company.web.controller.sale;

import com.simpletour.commons.data.dao.IBaseDao;
import com.simpletour.commons.data.domain.DomainPage;
import com.simpletour.commons.data.exception.BaseSystemException;
import com.simpletour.company.web.controller.support.BaseAction;
import com.simpletour.company.web.controller.support.BaseController;
import com.simpletour.company.web.controller.support.BaseDataResponse;
import com.simpletour.company.web.controller.support.PageHelper;
import com.simpletour.company.web.enums.FormModeType;
import com.simpletour.company.web.form.sale.SaleAppForm;
import com.simpletour.company.web.query.sale.SaleAppQuery;
import com.simpletour.domain.sale.SaleApp;

import com.simpletour.service.sale.ISaleAppService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Optional;

/**
 * @Brief :  销售端
 * @Author: liangfei/liangfei@simpletour.com
 * @Date :  2016/4/8 15:03
 * @Since ： ${VERSION}
 * @Remark: ${Remark}
 */
@Controller
@RequestMapping("/sale/")
public class SaleAppController extends BaseController {
    private static final String DOMAIN = "销售端";
    private static final String LIST_URL = "/sale/list";

    @Autowired
    private ISaleAppService saleAppService;

    @RequestMapping(value = {"", "list"})
    public String list(SaleAppQuery query, Model model) {
        this.setPageTitle(model, "销售端列表");
        DomainPage<SaleApp> pages = saleAppService.querySaleAppPagesByConditions(query.asMap(), "id", IBaseDao.SortBy.DESC, query.getIndex(), query.getSize(), true);
        model.addAttribute("page", pages);
        model.addAttribute("pageHelper", new PageHelper(pages));
        model.addAttribute("query", query);
        return "/sale/list";
    }

    @RequestMapping(value = "add", method = RequestMethod.GET)
    public String add(Model model) {
        this.setPageTitle(model, "添加销售端");
        this.enableGoBack(model);
        SaleAppForm viewForm = new SaleAppForm();
        model.addAttribute("viewForm", viewForm);
        return "/sale/form";
    }

    @ResponseBody
    @RequestMapping(value = "add", method = RequestMethod.POST)
    public BaseDataResponse add(@RequestBody @Valid SaleAppForm form, BindingResult bindingResult, Model model) {
        form.setMode(FormModeType.ADD.getValue());
        this.setPageTitle(model, "添加销售端");
        this.enableGoBack(model);
        if (bindingResult.hasErrors()) {
            return BaseDataResponse.validationFail().msg(BaseAction.ADD_FAIL(DOMAIN).getTitle()).detail(bindingResult.getAllErrors().get(0).getDefaultMessage()) ;
        }
        try {
            saleAppService.addSaleApp(form.as());
        } catch (BaseSystemException e) {
            return BaseDataResponse.fail().msg(BaseAction.ADD_FAIL(DOMAIN).getTitle()).detail(e.getMessage());
        }
        return BaseDataResponse.ok().action(BaseAction.ADD_SUCCESS(DOMAIN, form.getName(), LIST_URL), true);
    }

    @RequestMapping(value = "edit/{id}", method = RequestMethod.GET)
    public String edit(@PathVariable Integer id, Model model) {
        this.setPageTitle(model, "修改销售端");
        this.enableGoBack(model);
        Optional<SaleApp> saleAppOptional = saleAppService.getSaleAppById(id);
        if (saleAppOptional.isPresent()) {
            SaleAppForm viewForm = new SaleAppForm(saleAppOptional.get());
            model.addAttribute("viewForm", viewForm);
        }
        return "sale/form";
    }

    @ResponseBody
    @RequestMapping(value = "edit", method = RequestMethod.POST)
    public BaseDataResponse edit(@RequestBody @Valid SaleAppForm form, BindingResult bindingResult, Model model) {
        this.setPageTitle(model, "修改销售端");
        this.enableGoBack(model);
        form.setMode(FormModeType.UPDATE.getValue());
        if (bindingResult.hasErrors()) {
            return BaseDataResponse.validationFail().msg(BaseAction.ADD_FAIL(DOMAIN).getTitle()).detail(bindingResult.getAllErrors().get(0).getDefaultMessage()) ;
        }
        try {
            saleAppService.updateSaleApp(form.as());
        } catch (BaseSystemException e) {
            return BaseDataResponse.fail().msg(BaseAction.EDIT_FAIL(DOMAIN).getTitle()).detail(e.getMessage());
        }
        return BaseDataResponse.ok().action(BaseAction.EDIT_SUCCESS(DOMAIN, form.getName(), LIST_URL), true);

    }

    @ResponseBody
    @RequestMapping(value = "delete/{id}", method = RequestMethod.POST)
    public BaseDataResponse delete(@PathVariable Integer id) {
        Optional<SaleApp> saleAppOptional = saleAppService.getSaleAppById(id);
        if (!saleAppOptional.isPresent()) {
            return BaseDataResponse.fail().action(BaseAction.OBJECT_NOTFOUND(), false);
        }
        try {
            saleAppService.deleteSaleApp(Long.valueOf(id));
        } catch (BaseSystemException e) {
            return BaseDataResponse.fail().msg(BaseAction.DEL_FAIL(DOMAIN).getTitle()).detail(e.getMessage());
        }
        return BaseDataResponse.ok().action(BaseAction.DEL_SUCCESS(DOMAIN, saleAppOptional.get().getName(), LIST_URL), true);

    }


}
