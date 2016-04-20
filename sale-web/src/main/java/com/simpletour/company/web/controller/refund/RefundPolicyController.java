package com.simpletour.company.web.controller.refund;

import com.simpletour.commons.data.domain.DomainPage;
import com.simpletour.commons.data.dao.query.ConditionOrderByQuery;
import com.simpletour.commons.data.exception.BaseSystemException;
import com.simpletour.company.web.controller.support.BaseAction;
import com.simpletour.company.web.controller.support.BaseController;
import com.simpletour.company.web.controller.support.BaseDataResponse;
import com.simpletour.company.web.controller.support.PageHelper;
import com.simpletour.company.web.enums.FormModeType;
import com.simpletour.company.web.form.refund.RefundPolicyForm;
import com.simpletour.company.web.query.refund.RefundPolicyQuery;
import com.simpletour.domain.sale.RefundPolicy;
import com.simpletour.service.sale.IRefundPolicyService;
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
 * User: XuHui/xuhui@simpletour.com
 * Date: 2016/4/20
 * Time: 11:59
 */
@Controller
@RequestMapping("refundPolicy")
public class RefundPolicyController extends BaseController{
    @Autowired
    private IRefundPolicyService refundPolicyService;

    private static String LIST = "/refundPolicy/list";

    @RequestMapping(value = {"", "list"})
    public String list(RefundPolicyQuery query, Model model) {
        this.setPageTitle(model, "规则模板列表");
        DomainPage pages = refundPolicyService.findRefundPolicyPage(query.asQuery(ConditionOrderByQuery.class));
        model.addAttribute("page", pages);
        model.addAttribute("pageHelper", new PageHelper(pages));
        model.addAttribute("query", query);
        return "/refund/list";
    }

    @ResponseBody
    @RequestMapping(value = "select", method = RequestMethod.POST)
    public BaseDataResponse select(@RequestBody RefundPolicyQuery query) {
        List<RefundPolicy> modules = refundPolicyService.findRefundPolicyList(query.asQuery(ConditionOrderByQuery.class));
        if (modules == null || modules.isEmpty()) {
            return BaseDataResponse.noData();
        } else {
            List<RefundPolicyForm> refundPolicyForms = modules.stream()
                    .map(tmp -> new RefundPolicyForm((RefundPolicy) tmp)).collect(toList());
            return BaseDataResponse.ok().data(refundPolicyForms);
        }
    }

    @RequestMapping("add")
    public String add(Model model) {
        this.setPageTitle(model, "添加规则模板");
        this.enableGoBack(model);
        RefundPolicyForm viewForm = new RefundPolicyForm();
        model.addAttribute("viewForm", viewForm);
        return "/refund/form";
    }

    @RequestMapping(value = "add", method = RequestMethod.POST)
    @ResponseBody
    public BaseDataResponse add(@RequestBody @Valid RefundPolicyForm form, BindingResult bindingResult) {
        form.setMode(FormModeType.ADD.getValue());
        if (bindingResult.hasErrors()) {
            return BaseDataResponse.validationFail().action(BaseAction.ADD_FAIL("规则模板", form.getName()), false);
        }
        RefundPolicy refundPolicy = form.as();
        try {
            refundPolicyService.addRefundPolicy(refundPolicy);
            return BaseDataResponse.ok().action(BaseAction.ADD_SUCCESS("规则模板", form.getName(), LIST), Boolean.TRUE);
        } catch (BaseSystemException e) {
            return BaseDataResponse.fail().msg(BaseAction.ADD_FAIL("规则模板").getTitle()).detail(e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
            return BaseDataResponse.fail().msg(BaseAction.ADD_FAIL("规则模板").getTitle()).detail("数据库异常");
        }
    }

    @RequestMapping("edit/{id}")
    public String edit(@PathVariable Long id, Model model) {
        Optional<RefundPolicy> module = refundPolicyService.getRefundPolicyById(id);
        if (!module.isPresent()) {
            return this.error();
        }
        RefundPolicyForm refundPolicyForm = new RefundPolicyForm(module.get());
        model.addAttribute("viewForm", refundPolicyForm);
        this.setPageTitle(model, refundPolicyForm.getName() + " 规则模板 - 编辑");
        this.enableGoBack(model);
        return "/refund/form";
    }

    @RequestMapping(value = "edit", method = RequestMethod.POST)
    @ResponseBody
    public BaseDataResponse edit(@RequestBody @Valid RefundPolicyForm form, BindingResult bindingResult) {
        form.setMode(FormModeType.UPDATE.getValue());
        if (bindingResult.hasErrors()) {
            return BaseDataResponse.validationFail().action(BaseAction.EDIT_FAIL("规则模板", form.getName()), false);
        }
        try {
            RefundPolicy refundPolicy = form.as();
            refundPolicyService.updateRefundPolicy(refundPolicy);
            return BaseDataResponse.ok().action(BaseAction.EDIT_SUCCESS("规则模板", form.getName(), LIST), true);
        } catch (BaseSystemException e) {
            return BaseDataResponse.fail().msg(BaseAction.EDIT_FAIL("规则模板").getTitle()).detail(e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
            return BaseDataResponse.fail().msg(BaseAction.EDIT_FAIL("规则模板").getTitle()).detail("数据库异常");
        }
    }

    @RequestMapping(value = "delete/{id}", method = RequestMethod.POST)
    @ResponseBody
    public BaseDataResponse delete(@PathVariable Long id) {
        try {
            refundPolicyService.deleteRefundPolicy(id);
            return BaseDataResponse.ok().action(BaseAction.DEL_SUCCESS("规则模板", "规则模板", LIST), true);
        } catch (BaseSystemException e) {
            return BaseDataResponse.fail().msg(BaseAction.DEL_FAIL("规则模板").getTitle()).detail(e.getMessage());
        } catch (Exception e) {
            return BaseDataResponse.fail().msg(BaseAction.DEL_FAIL("规则模板").getTitle()).detail("数据库异常");
        }
    }

}
