package com.simpletour.company.web.controller.agreement;

import com.simpletour.commons.data.dao.query.ConditionOrderByQuery;
import com.simpletour.commons.data.domain.DomainPage;
import com.simpletour.commons.data.exception.BaseSystemException;
import com.simpletour.company.web.controller.support.BaseAction;
import com.simpletour.company.web.controller.support.BaseController;
import com.simpletour.company.web.controller.support.BaseDataResponse;
import com.simpletour.company.web.controller.support.PageHelper;
import com.simpletour.company.web.enums.FormModeType;
import com.simpletour.company.web.form.agreement.AgreementForm;
import com.simpletour.company.web.query.agreement.AgreementQuery;
import com.simpletour.company.web.util.OptionsUtil;
import com.simpletour.domain.sale.Agreement;
import com.simpletour.domain.sale.SaleApp;
import com.simpletour.service.sale.IAgreementService;
import com.simpletour.service.sale.ISaleAppService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

import static java.util.stream.Collectors.toList;

/**
 * Created by YuanYuan/yuanyuan@simpletour.com on 2016/4/20.
 *
 * @since 2.0-SNAPSHOT
 */
@Controller
@RequestMapping("agreement")
public class AgreementController extends BaseController {
    private static final String DOMAIN = "销售协议";
    private static final String LIST = "/agreement/list";

    @Autowired
    IAgreementService agreementService;

    @Autowired
    ISaleAppService saleAppService;

    @RequestMapping({"", "list"})
    public String list(AgreementQuery query, Model model) {
        this.setPageTitle(model, "销售协议列表");
        DomainPage pages = agreementService.findAgreementByQuery(query.asQuery(ConditionOrderByQuery.class));
        model.addAttribute("page", pages);
        model.addAttribute("pageHelper", new PageHelper(pages));
        model.addAttribute("status", OptionsUtil.getBooleanOption("协议状态", "启用", "禁用", query.isEnabled()));
        model.addAttribute("query", query);
        return "/agreement/list";
    }

    @ResponseBody
    @RequestMapping(value = "select", method = RequestMethod.POST)
    public BaseDataResponse select(@RequestBody AgreementQuery query) {
        List<Agreement> modules = agreementService.getAgreementListByQuery(query.asQuery(ConditionOrderByQuery.class));
        if (modules == null || modules.isEmpty()) {
            return BaseDataResponse.noData();
        } else {
            List<AgreementForm> agreementForms = modules.stream()
                    .map(AgreementForm::new).collect(toList());
            return BaseDataResponse.ok().data(agreementForms);
        }
    }

    @RequestMapping(value = "add", method = RequestMethod.GET)
    public String add(Model model) {
        this.setPageTitle(model, "添加销售协议");
        this.enableGoBack(model);
        AgreementForm viewForm = new AgreementForm();
        model.addAttribute("viewForm", viewForm);
        model.addAttribute("status", OptionsUtil.getBooleanOption("启用", "禁用", Boolean.TRUE));
        return "/agreement/form";
    }

    @ResponseBody
    @RequestMapping(value = "add", method = RequestMethod.POST)
    public BaseDataResponse add(@RequestBody @Valid AgreementForm form, BindingResult bindingResult) {
        form.setMode(FormModeType.ADD.getValue());
        if (bindingResult.hasErrors()) {
            return BaseDataResponse.validationFail().action(BaseAction.ADD_FAIL(DOMAIN, form.getAppName()), false);
        }
        try {
            Agreement agreement = form.as();
            Optional<SaleApp> app = saleAppService.getSaleAppById(agreement.getSaleApp().getId());
            app.ifPresent(agreement::setSaleApp);
            agreementService.addAgreement(agreement);
            return BaseDataResponse.ok().action(BaseAction.ADD_SUCCESS(DOMAIN, form.getAppName(), LIST), Boolean.TRUE);
        } catch (BaseSystemException e) {
            return BaseDataResponse.fail().msg(BaseAction.ADD_FAIL(DOMAIN).getTitle()).detail(e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
            return BaseDataResponse.fail().msg(BaseAction.ADD_FAIL(DOMAIN).getTitle()).detail("数据库异常");
        }
    }

    @RequestMapping(value = "edit/{id}", method = RequestMethod.GET)
    public String edit(@PathVariable Long id, Model model) {
        Optional<Agreement> module = agreementService.getAgreementById(id);
        if (!module.isPresent()) {
            return this.error();
        }
        AgreementForm agreementForm = new AgreementForm(module.get());
        model.addAttribute("viewForm", agreementForm);
        model.addAttribute("status", OptionsUtil.getBooleanOption("启用", "禁用", agreementForm.isEnabled()));
        this.setPageTitle(model, agreementForm.getAppName() + " 销售协议 - 编辑");
        this.enableGoBack(model);
        return "/agreement/form";
    }

    @ResponseBody
    @RequestMapping(value = "edit", method = RequestMethod.POST)
    public BaseDataResponse edit(@RequestBody @Valid AgreementForm form, BindingResult bindingResult) {
        form.setMode(FormModeType.UPDATE.getValue());
        if (bindingResult.hasErrors()) {
            return BaseDataResponse.validationFail().action(BaseAction.EDIT_FAIL(DOMAIN, form.getAppName()), false);
        }
        try {
            Agreement agreement = form.as();
            Optional<SaleApp> app = saleAppService.getSaleAppById(agreement.getSaleApp().getId());
            app.ifPresent(agreement::setSaleApp);
            agreementService.updateAgreement(agreement);
            return BaseDataResponse.ok().action(BaseAction.EDIT_SUCCESS(DOMAIN, form.getAppName(), LIST), true);
        } catch (BaseSystemException e) {
            return BaseDataResponse.fail().msg(BaseAction.EDIT_FAIL(DOMAIN).getTitle()).detail(e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
            return BaseDataResponse.fail().msg(BaseAction.EDIT_FAIL(DOMAIN).getTitle()).detail("数据库异常");
        }
    }

    @ResponseBody
    @RequestMapping(value = "edit/status/{id}", method = RequestMethod.POST)
    public BaseDataResponse editStatus(@PathVariable Long id) {
        try {
            Optional<Agreement> agreement = agreementService.getAgreementById(id);
            agreement.get().setEnabled(!agreement.get().isEnabled());
            agreementService.updateAgreement(agreement.get());
            return BaseDataResponse.ok().msg("状态更新成功").data(agreement.get().isEnabled() ? "启用" : "禁用");
        } catch (BaseSystemException e) {
            return BaseDataResponse.fail().msg("状态更新失败").detail(e.getMessage());
        } catch (Exception e) {
            return BaseDataResponse.fail().msg("状态更新失败").detail("数据库异常");
        }
    }
}
