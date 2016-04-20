package com.simpletour.company.web.controller.company;

import com.alibaba.fastjson.JSON;
import com.simpletour.commons.data.domain.DomainPage;
import com.simpletour.commons.data.exception.BaseSystemException;
import com.simpletour.company.web.controller.support.*;
import com.simpletour.company.web.enums.FormModeType;
import com.simpletour.company.web.form.company.CompanyForm;
import com.simpletour.company.web.form.company.RoleForm;
import com.simpletour.company.web.query.company.RoleQueryConditions;
import com.simpletour.domain.company.Company;
import com.simpletour.domain.company.Role;
import com.simpletour.service.company.ICompanyService;
import com.simpletour.service.company.IModuleService;
import com.simpletour.service.company.IRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

/**
 * 文件描述：公司角色权限关系前端控制器
 * 创建人员：石广路（shiguanglu@simpletour.com）
 * 创建日期：2016-4-11 17:07
 * 备注说明：null
 * @since 2.0-SNAPSHOT
 */
@Controller
@RequestMapping("/company/role")
public class RoleController extends BaseController {
    //private static final Logger LOGGER = LoggerFactory.getLogger(RoleController.class);

    private static final String DOMAIN = "角色";

    private static final String MAPPING_URL = "/company/role/";

    @Autowired
    private IRoleService roleService;

    @Autowired
    private ICompanyService companyService;

    @Autowired
    private IModuleService moduleService;

    @RequestMapping(value = {"", "list"})
    public String list(RoleQueryConditions query, Model model) {
        System.out.println("enter list");

        // TODO: 暂时先将租户ID写死
        //TokenStorage.setLocalTokenWithCompanyId(0L, COMPANY_ID);

        setPageTitle(model, "角色列表");

        DomainPage<Role> page = roleService.getRolesPages(query.asRoleQuery());
        model.addAttribute("page", page);
        model.addAttribute("query", query);
        model.addAttribute("pageHelper", new PageHelper(page));

        System.out.println("end list");

        return MAPPING_URL + "list";
    }

    @ResponseBody
    @RequestMapping(value = "select", method = RequestMethod.POST)
    public BaseDataResponse select(@RequestBody RoleQueryConditions query) {
        System.out.println("enter select");

        // TODO: 暂时先将租户ID写死
        //TokenStorage.setLocalTokenWithCompanyId(0L, COMPANY_ID);

        DomainPage<Role> page = roleService.getRolesPages(query.asRoleQuery());
        if (null == page || page.getDomains().isEmpty()) {
            return BaseDataResponse.noData();
        } else {
            return BaseDataResponse.ok().data(page);
        }
    }

    @RequestMapping(value = "add", method = RequestMethod.GET)
    public String add(Model model) {
        System.out.println("enter add role page by http-get");
        
        setPageTitle(model, "添加角色");
        enableGoBack(model);

        // TODO: 暂时先将租户ID写死
        //TokenStorage.setLocalTokenWithCompanyId(0L, COMPANY_ID);

        RoleForm roleForm = new RoleForm();

        Optional<Company> company = companyService.getCompanyById(TokenStorage.COMPANY_ID);
        if (!isPresentAndNotDel(company)) {
            Company em = new Company();
            roleForm.setCompany(em);
            model.addAttribute("viewForm", roleForm);
            System.out.println("get list failed by get");
            return MAPPING_URL + "form";
        }

        Company com = company.get();
        CompanyForm companyForm = CompanyForm.toCompanyForm(com);

        roleForm.setCompany(com);
        roleForm.setScopes(companyForm.getScopes());
        model.addAttribute("viewForm", roleForm);

        System.out.println("end list by get");
        return MAPPING_URL + "form";
    }

    @ResponseBody
    @RequestMapping(value = "add", method = RequestMethod.POST)
    public BaseDataResponse add(@RequestBody @Valid RoleForm form, BindingResult bindingResult) {
        System.out.println("enter add role page by http-post");

        if (bindingResult.hasErrors()) {
            return BaseDataResponse.validationFail().action(BaseAction.ADD_FAIL(DOMAIN, form.getName()), false);
        }

        // TODO: 暂时先将租户ID写死
        //TokenStorage.setLocalTokenWithCompanyId(0L, COMPANY_ID);

        Optional<Company> company = companyService.getCompanyById(TokenStorage.COMPANY_ID);
        if (!isPresentAndNotDel(company)) {
            System.out.println("add role failed by post");
            return BaseDataResponse.fail().msg(BaseAction.ADD_FAIL(DOMAIN).getTitle()).detail("公司不存在，无法进行此项操作");
        }

        form.setMode(FormModeType.ADD.getValue());
        form.setCompany(company.get());

        Role role = form.convert2Role(moduleService);

        try {
            roleService.addRole(role);
        } catch (BaseSystemException e) {
            return BaseDataResponse.fail().msg(BaseAction.ADD_FAIL(DOMAIN).getTitle()).detail(e.getMessage());
        }

        return BaseDataResponse.ok().action(BaseAction.ADD_SUCCESS(DOMAIN, form.getName(), MAPPING_URL + "list"), true);
    }

    @RequestMapping(value = "edit/{id}", method = RequestMethod.GET)
    public String edit(@PathVariable Long id, Model model) {
        System.out.println("enter edit role page by http-get");
        setPageTitle(model, "编辑角色");
        enableGoBack(model);

        // TODO: 暂时先将租户ID写死
        //TokenStorage.setLocalTokenWithCompanyId(0L, COMPANY_ID);

        RoleForm viewForm = new RoleForm();

        Optional<Role> role = roleService.getRoleById(id);
        if (!isPresentAndNotDel(role)) {
            Company em = new Company();
            viewForm.setCompany(em);
            model.addAttribute("viewForm", viewForm);
            System.out.println("get list failed by get");
            return MAPPING_URL + "form";
        }

        model.addAttribute("viewForm", viewForm.convert2RoleForm(role.get()));

        return MAPPING_URL + "form";
    }

    @ResponseBody
    @RequestMapping(value = "edit", method = RequestMethod.POST)
    public BaseDataResponse edit(@RequestBody @Valid RoleForm form, BindingResult bindingResult) {
        System.out.println("enter edit role page by http-post");
        form.setMode(FormModeType.UPDATE.getValue());

        if (bindingResult.hasErrors()) {
            return BaseDataResponse.validationFail().action(BaseAction.EDIT_FAIL(DOMAIN, form.getName()), false);
        }

        // TODO: 暂时先将租户ID写死
        //TokenStorage.setLocalTokenWithCompanyId(0L, COMPANY_ID);

        Optional<Role> optional = roleService.getRoleById(form.getId());
        if (!isPresentAndNotDel(optional)) {
            return BaseDataResponse.fail().msg(BaseAction.EDIT_FAIL(DOMAIN).getTitle()).detail("角色不存在");
        }

        Role role = optional.get();
        form.setType(role.getType());
        form.setCompany(role.getCompany());
        form.setVersion(role.getVersion());

        role = form.convert2Role(moduleService);

        try {
            roleService.updateRole(role);
            return BaseDataResponse.ok().action(BaseAction.EDIT_SUCCESS(DOMAIN, form.getName(), MAPPING_URL + "list"), true);
        } catch (BaseSystemException e) {
            return BaseDataResponse.fail().msg(BaseAction.EDIT_FAIL(DOMAIN).getTitle()).detail(e.getMessage());
        }
    }

    @RequestMapping(value = "delete/{id}", method = RequestMethod.GET)
    public String delete(@PathVariable Long id, HttpServletResponse response) {
        System.out.println("enter delete role page by http-get");

        Map<String, String> data = new HashMap<>(3);
        data.put("jumpUrl", MAPPING_URL + "list");

        // TODO: 暂时先将租户ID写死
        //TokenStorage.setLocalTokenWithCompanyId(0L, COMPANY_ID);

        Optional<Role> role = roleService.getRoleById(id);
        if (!isPresentAndNotDel(role)) {
            data.put("code", "-1");
            data.put("msg", "角色不存在");
        } else {
            try {
                roleService.deleteRoleById(id);
                data.put("code", "0");
                data.put("msg", "删除角色成功");
            } catch (BaseSystemException e) {
                data.put("code", "1");
                data.put("msg", "删除角色失败");
            }
        }

        outputResponse(response, JSON.toJSONString(data));

        return MAPPING_URL + "list";
    }

    private void outputResponse(HttpServletResponse response, String msg) {
        PrintWriter writer = null;

        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/json");

        try {
            writer = response.getWriter();
            writer.write(msg);
            writer.flush();
        } catch (IOException exc) {
            exc.printStackTrace();
        } finally {
            if (null != writer) {
                writer.close();
            }
        }
    }
}
