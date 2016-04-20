package com.simpletour.platfrom.web.controller.company;


import com.simpletour.commons.data.domain.DomainPage;
import com.simpletour.commons.data.exception.BaseSystemException;
import com.simpletour.dao.company.query.CompanyDaoQuery;
import com.simpletour.domain.company.Company;
import com.simpletour.domain.company.Permission;
import com.simpletour.platfrom.web.controller.support.BaseAction;
import com.simpletour.platfrom.web.controller.support.BaseController;
import com.simpletour.platfrom.web.controller.support.BaseDataResponse;
import com.simpletour.platfrom.web.controller.support.PageHelper;
import com.simpletour.platfrom.web.enums.FormModeType;
import com.simpletour.platfrom.web.form.company.CompanyForm;
import com.simpletour.platfrom.web.query.company.CompanyQuery;
import com.simpletour.platfrom.web.view.company.CompanyListView;
import com.simpletour.platfrom.web.view.company.CompanyPermissionView;
import com.simpletour.service.company.ICompanyService;
import com.simpletour.service.company.IModuleService;
import com.simpletour.service.company.IScopeTemplateService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
/**
 * Author:  wangLin
 * Mail  :  wl@simpletour.com
 * Date  :  2016/4/8.
 * Remark:
 */
@Controller
@RequestMapping("/company/")
public class CompanyController extends BaseController {
    private static final String DOMAIN = "公司";
    private static final String LIST_URL = "/company/list";

    @Resource
    private ICompanyService companyService;

    @Resource
    private IModuleService moduleService;

    @Resource
    private IScopeTemplateService scopeTemplateService;

    @RequestMapping(value = "add", method = RequestMethod.GET)
    public String add(Model model) {
        this.setPageTitle(model, "公司添加");
        this.enableGoBack(model);
        CompanyForm companyForm = new CompanyForm();
        model.addAttribute("viewForm", companyForm);
        return "/company/form";
    }

    @ResponseBody
    @RequestMapping(value = "add", method = RequestMethod.POST)
    public BaseDataResponse add(@RequestBody @Valid CompanyForm form, BindingResult bindingResult) {
        form.setMode(FormModeType.ADD.getValue());
        if (bindingResult.hasErrors()) {
            return BaseDataResponse.validationFail().action(BaseAction.ADD_FAIL(DOMAIN, form.getName()), false);
        }
        try {
            Company company = form.as();
            companyService.addCompany(company);
        } catch (BaseSystemException e) {
            return BaseDataResponse.fail().msg(BaseAction.ADD_FAIL(DOMAIN).getTitle()).detail(e.getMessage());
        }
        return BaseDataResponse.ok().action(BaseAction.ADD_SUCCESS(DOMAIN, form.getName(), LIST_URL), true);
    }


    @RequestMapping(value = "edit/{id}", method = RequestMethod.GET)
    public String edit(@PathVariable Long id, Model model) {
        this.setPageTitle(model, "公司编辑");
        this.enableGoBack(model);
        Optional<Company> company = companyService.getCompanyById(id);
        if (isPresentAndNotDel(company)) {
            CompanyForm companyForm = CompanyForm.toCompanyForm(company.get());
            model.addAttribute("viewForm", companyForm);
            return "/company/form";
        }
        return this.error();
    }

    @ResponseBody
    @RequestMapping(value = "edit", method = RequestMethod.POST)
    public BaseDataResponse edit(@RequestBody @Valid CompanyForm form, BindingResult bindingResult) {
        form.setMode(FormModeType.UPDATE.getValue());
        if (bindingResult.hasErrors()) {
            return BaseDataResponse.ok().action(BaseAction.EDIT_FAIL(DOMAIN, form.getName()), false);
        }
        try {
            Company company = form.as();
            companyService.updateCompany(company);
        } catch (BaseSystemException e) {
            return BaseDataResponse.fail().msg(BaseAction.EDIT_FAIL(DOMAIN).getTitle()).detail(e.getMessage());
        }
        return BaseDataResponse.ok().action(BaseAction.EDIT_SUCCESS(DOMAIN, form.getName(), LIST_URL), true);
    }

    @RequestMapping(value = {"", "list"})
    public String list(CompanyQuery query, Model model) {
        this.setPageTitle(model, "公司列表");
        DomainPage<Company> domainPage = companyService.getCompanyPagesByQuery((CompanyDaoQuery) query.asQuery(CompanyDaoQuery.class));

        //得到所有的permissionList,并且包装一下
        List<Permission> permissionList = moduleService.getAllPermissions();
        List<CompanyPermissionView> companyPermissionViews = new ArrayList<>();
        if (permissionList != null && !permissionList.isEmpty()){
            permissionList.stream().forEach(permission -> companyPermissionViews.add(new CompanyPermissionView(permission.getId(),permission.getName())) );
        }
        CompanyListView listView = new CompanyListView(domainPage, query);
        model.addAttribute("page",listView);
        model.addAttribute("pageHelper", new PageHelper(domainPage));
        model.addAttribute("companyPermissionList", companyPermissionViews);
        return "/company/list";
    }

    @ResponseBody
    @RequestMapping(value = "delete/{id}", method = RequestMethod.POST)
    public BaseDataResponse delete(@PathVariable Long id) {
        Optional<Company> company = companyService.getCompanyById(id);
        if (!isPresentAndNotDel(company)) {
            return BaseDataResponse.fail().action(BaseAction.OBJECT_NOTFOUND(), false);
        }
        try {
            companyService.deleteCompany(id);
        } catch (BaseSystemException e) {
            return BaseDataResponse.fail().msg(BaseAction.DEL_FAIL(DOMAIN).getTitle()).detail(e.getMessage());
        }

        return BaseDataResponse.ok().action(BaseAction.DEL_SUCCESS(DOMAIN, company.get().getName(), LIST_URL), true);

    }
}
