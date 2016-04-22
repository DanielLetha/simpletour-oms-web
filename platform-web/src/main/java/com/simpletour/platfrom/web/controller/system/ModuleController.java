package com.simpletour.platfrom.web.controller.system;

import com.alibaba.fastjson.JSON;
import com.simpletour.commons.data.domain.DomainPage;
import com.simpletour.commons.data.exception.BaseSystemException;
import com.simpletour.dao.company.query.ModuleDaoQuery;
import com.simpletour.domain.company.Module;
import com.simpletour.platfrom.web.controller.support.BaseAction;
import com.simpletour.platfrom.web.controller.support.BaseController;
import com.simpletour.platfrom.web.controller.support.BaseDataResponse;
import com.simpletour.platfrom.web.controller.support.PageHelper;
import com.simpletour.platfrom.web.enums.FormModeType;
import com.simpletour.platfrom.web.form.system.ModuleForm;
import com.simpletour.platfrom.web.query.system.ModuleQuery;
import com.simpletour.platfrom.web.view.module.ModuleQueryListView;
import com.simpletour.service.company.IModuleService;
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
 * Author：XuHui/xuhui@simpletour.com
 * Brief：
 * Date: 2016/4/5
 * Time: 10:15
 */
@Controller
@RequestMapping("module")
public class ModuleController extends BaseController {
    @Autowired
    private IModuleService moduleService;

    private static String LIST = "/module/list";

    @RequestMapping(value = {"", "list"})
    public String list(ModuleQuery query, Model model) {
        this.setPageTitle(model, "模块列表");
        DomainPage pages = moduleService.findModulePage((ModuleDaoQuery) query.asQuery(ModuleDaoQuery.class));
        model.addAttribute("page", pages);
        model.addAttribute("pageHelper", new PageHelper(pages));
        model.addAttribute("query", query);
        return "/system/module/list";
    }

    @ResponseBody
    @RequestMapping(value = "select", method = RequestMethod.POST)
    public BaseDataResponse select(@RequestBody ModuleQuery query) {
        List<Module> modules = moduleService.findModuleList((ModuleDaoQuery) query.asQuery(ModuleDaoQuery.class));

        List<ModuleForm> moduleForms = modules.stream()
                .map(tmp -> new ModuleForm((Module) tmp)).collect(toList());
        return BaseDataResponse.ok().data(moduleForms);

    }

    @ResponseBody
    @RequestMapping(value = "modules", method = RequestMethod.POST)
    public BaseDataResponse listModules(@RequestBody ModuleQuery query) {
        try {
            List<Module> modules = moduleService.findModuleList((ModuleDaoQuery) query.asQuery(ModuleDaoQuery.class));
            return BaseDataResponse.ok().data(JSON.toJSON(new ModuleQueryListView(modules)));
        } catch (BaseSystemException e) {
            return BaseDataResponse.fail().data(e.getMessage());
        }
    }

    @RequestMapping("add")
    public String add(Model model) {
        this.setPageTitle(model, "添加模块");
        this.enableGoBack(model);
        ModuleForm viewForm = new ModuleForm();
        model.addAttribute("viewForm", viewForm);
        return "/system/module/form";
    }

    @RequestMapping(value = "add", method = RequestMethod.POST)
    @ResponseBody
    public BaseDataResponse add(@RequestBody @Valid ModuleForm form, BindingResult bindingResult) {
        form.setMode(FormModeType.ADD.getValue());
        if (bindingResult.hasErrors()) {
            return BaseDataResponse.validationFail().action(BaseAction.ADD_FAIL("模块", form.getName()), false);
        }
        Module module = form.as();
        try {
            moduleService.addModule(module);
            return BaseDataResponse.ok().action(BaseAction.ADD_SUCCESS("模块", form.getName(), LIST), Boolean.TRUE);
        } catch (BaseSystemException e) {
            return BaseDataResponse.fail().msg(BaseAction.ADD_FAIL("模块").getTitle()).detail(e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
            return BaseDataResponse.fail().msg(BaseAction.ADD_FAIL("模块").getTitle()).detail("数据库异常");
        }
    }

    @RequestMapping("edit/{id}")
    public String edit(@PathVariable Long id, Model model) {
        Optional<Module> module = moduleService.getModuleById(id);
        if (!module.isPresent()) {
            return this.error();
        }
        ModuleForm moduleForm = new ModuleForm(module.get());
        model.addAttribute("viewForm", moduleForm);
        this.setPageTitle(model, moduleForm.getName() + " 模块 - 编辑");
        this.enableGoBack(model);
        return "/system/module/form";
    }

    @RequestMapping(value = "edit", method = RequestMethod.POST)
    @ResponseBody
    public BaseDataResponse edit(@RequestBody @Valid ModuleForm form, BindingResult bindingResult) {
        form.setMode(FormModeType.UPDATE.getValue());
        if (bindingResult.hasErrors()) {
            return BaseDataResponse.validationFail().action(BaseAction.EDIT_FAIL("模块", form.getName()), false);
        }
        try {
            Module module = form.as();
            moduleService.updateModule(module);
            return BaseDataResponse.ok().action(BaseAction.EDIT_SUCCESS("模块", form.getName(), LIST), true);
        } catch (BaseSystemException e) {
            return BaseDataResponse.fail().msg(BaseAction.EDIT_FAIL("模块").getTitle()).detail(e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
            return BaseDataResponse.fail().msg(BaseAction.EDIT_FAIL("模块").getTitle()).detail("数据库异常");
        }
    }

    @RequestMapping(value = "delete/{id}", method = RequestMethod.POST)
    @ResponseBody
    public BaseDataResponse delete(@PathVariable Long id) {
        try {
            moduleService.deleteModule(id);
            return BaseDataResponse.ok().action(BaseAction.DEL_SUCCESS("模块", "模块", LIST), true);
        } catch (BaseSystemException e) {
            return BaseDataResponse.fail().msg(BaseAction.DEL_FAIL("模块").getTitle()).detail(e.getMessage());
        } catch (Exception e) {
            return BaseDataResponse.fail().msg(BaseAction.DEL_FAIL("模块").getTitle()).detail("数据库异常");
        }
    }

//    @RequestMapping(value = "permission/delete/{id}",method = RequestMethod.POST)
//    @ResponseBody
//    public BaseDataResponse deletePermission(@PathVariable Long id){
//        return BaseDataResponse.ok().action(BaseAction.DEL_SUCCESS("功能","功能",LIST),false);
//    }
}
