package com.simpletour.platfrom.web.controller.system;

import com.simpletour.commons.data.domain.DomainPage;
import com.simpletour.commons.data.exception.BaseSystemException;
import com.simpletour.dao.company.query.ScopeTemplateDaoQuery;
import com.simpletour.domain.company.ScopeTemplate;
import com.simpletour.platfrom.web.controller.support.BaseAction;
import com.simpletour.platfrom.web.controller.support.BaseController;
import com.simpletour.platfrom.web.controller.support.BaseDataResponse;
import com.simpletour.platfrom.web.controller.support.PageHelper;
import com.simpletour.platfrom.web.enums.FormModeType;
import com.simpletour.platfrom.web.form.system.ScopeTemplateForm;
import com.simpletour.platfrom.web.query.system.ScopeTemplateQuery;
import com.simpletour.platfrom.web.view.BaseListView;
import com.simpletour.platfrom.web.view.scope.ScopeTemplateQueryListView;
import com.simpletour.service.company.IScopeTemplateService;
import org.omg.CORBA.SystemException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * User: XuHui/xuhui@simpletour.com
 * Date: 2016/4/11
 * Time: 9:46
 */
@Controller
@RequestMapping("/scope")
public class ScopeTemplateController extends BaseController {
    @Autowired
    private IScopeTemplateService scopeTemplateService;

    private static String LIST="/scope";

    @RequestMapping(value = {"","list"})
    public String list(ScopeTemplateQuery query, Model model){
        this.setPageTitle(model,"权限范围列表");
        DomainPage pages=scopeTemplateService.findScopeTemplatePage((ScopeTemplateDaoQuery) query.asQuery(ScopeTemplateDaoQuery.class));
        BaseListView listView = new BaseListView(pages);
        listView.setSubViews((List) pages.getDomains().stream().map(tmp->new ScopeTemplateForm((ScopeTemplate) tmp)).collect(Collectors.toList()));
        model.addAttribute("page",listView);
        model.addAttribute("pageHelper",new PageHelper(pages));
        model.addAttribute("query",query);
        return "/system/scope/list";
    }

    @ResponseBody
    @RequestMapping(value = "select",method = RequestMethod.POST)
    public BaseDataResponse select(@RequestBody ScopeTemplateQuery query){
        DomainPage page=scopeTemplateService.findScopeTemplatePage((ScopeTemplateDaoQuery) query.asQuery(ScopeTemplateDaoQuery.class));
        if(page==null||page.getDomains()==null||page.getDomains().isEmpty()){
            return BaseDataResponse.noData();
        }else{
            List<ScopeTemplateForm> scopeTemplateForms= (List<ScopeTemplateForm>) scopeTemplateService.findScopeTemplatePage((ScopeTemplateDaoQuery) query.asQuery(ScopeTemplateDaoQuery.class))
                    .getDomains().stream().map(tmp->new ScopeTemplateForm((ScopeTemplate) tmp));
            return BaseDataResponse.ok().data(page.getDomains());
        }
    }

    /**
     * 权限范围的select,添加公司的时候使用，返回的数据需要封装
     * @param query
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "scopes", method = RequestMethod.POST)
    public BaseDataResponse listScopes(@RequestBody ScopeTemplateQuery query) {
        DomainPage page = scopeTemplateService.findScopeTemplatePage((ScopeTemplateDaoQuery) query.asQuery(ScopeTemplateDaoQuery.class));
        if (page==null||page.getDomains()==null||page.getDomains().isEmpty()) {
            return BaseDataResponse.noData();
        } else {
            ScopeTemplateQueryListView templateQueryListView = new ScopeTemplateQueryListView(page.getDomains());
            return BaseDataResponse.ok().data(templateQueryListView.getScopeQueryList());
        }
    }

    @RequestMapping("add")
    public String add(Model model) {
        this.setPageTitle(model, "添加权限列表");
        this.enableGoBack(model);
        ScopeTemplateForm form = new ScopeTemplateForm();
        model.addAttribute("viewForm", form);
        return "/system/scope/form";
    }

    @RequestMapping(value = "add", method = RequestMethod.POST)
    @ResponseBody
    public BaseDataResponse add(@RequestBody @Valid ScopeTemplateForm form, BindingResult bindingResult) {
        form.setMode(FormModeType.ADD.getValue());
        if(bindingResult.hasErrors())
            return BaseDataResponse.validationFail().action(BaseAction.ADD_FAIL("权限范围",form.getName()),false);
        ScopeTemplate scopeTemplate=form.as();
        try {
            scopeTemplateService.addScopeTemplate(scopeTemplate);
            return BaseDataResponse.ok().action(BaseAction.ADD_SUCCESS("权限范围",form.getName(),LIST),Boolean.TRUE);
        } catch (BaseSystemException e) {
            return BaseDataResponse.fail().msg(BaseAction.ADD_FAIL("权限范围").getTitle()).detail(e.getMessage());
        }catch (Exception e){
            e.printStackTrace();
            return BaseDataResponse.fail().msg(BaseAction.ADD_FAIL("权限范围").getTitle()).detail("数据库异常");
        }
    }

    @RequestMapping("edit/{id}")
    public String edit(@PathVariable Long id,Model model){
        Optional<ScopeTemplate> scopeTemplate=scopeTemplateService.getScopeTemplateById(id);
        if(!scopeTemplate.isPresent())
            return this.error();
        ScopeTemplateForm scopeTemplateForm=new ScopeTemplateForm(scopeTemplate.get());
        model.addAttribute("viewForm",scopeTemplateForm);
        this.setPageTitle(model,scopeTemplateForm.getName()+" 权限范围 - 编辑");
        this.enableGoBack(model);
        return "/system/scope/form";
    }

    @RequestMapping(value = "edit",method = RequestMethod.POST)
    @ResponseBody
    public BaseDataResponse edit(@RequestBody @Valid ScopeTemplateForm form,BindingResult bindingResult){
        form.setMode(FormModeType.UPDATE.getValue());
        if (bindingResult.hasErrors()) {
            return BaseDataResponse.validationFail().action(BaseAction.EDIT_FAIL("权限范围",form.getName()),false);
        }
        try {
            ScopeTemplate scopeTemplate = form.as();
            scopeTemplateService.updateScopeTemplate(scopeTemplate);
            return BaseDataResponse.ok().action(BaseAction.EDIT_SUCCESS("权限范围", form.getName(), LIST), true);
        } catch (BaseSystemException e) {
            return BaseDataResponse.fail().msg(BaseAction.EDIT_FAIL("权限范围").getTitle()).detail(e.getMessage());
        } catch (SystemException e) {
            e.printStackTrace();
            return BaseDataResponse.fail().msg(BaseAction.EDIT_FAIL("权限范围").getTitle()).detail("数据库异常");
        }
    }

    @RequestMapping(value = "delete/{id}",method = RequestMethod.POST)
    @ResponseBody
    public BaseDataResponse delete(@PathVariable Long id){
        try{
            scopeTemplateService.deleteScopeTemplate(id);
            return BaseDataResponse.ok().action(BaseAction.DEL_SUCCESS("权限范围","权限范围",LIST),true);
        }catch (BaseSystemException e){
            return BaseDataResponse.fail().msg(BaseAction.DEL_FAIL("权限范围").getTitle()).detail(e.getMessage());
        }catch (Exception e){
            e.printStackTrace();
            return BaseDataResponse.fail().msg(BaseAction.DEL_FAIL("权限范围").getTitle()).detail("数据库异常");
        }
    }
}
