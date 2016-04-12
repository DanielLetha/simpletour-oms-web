package com.simpletour.company.web.controller.system;

import com.simpletour.common.core.domain.DomainPage;
import com.simpletour.common.core.exception.BaseSystemException;
import com.simpletour.company.web.controller.support.BaseAction;
import com.simpletour.company.web.controller.support.BaseController;
import com.simpletour.company.web.controller.support.BaseDataResponse;
import com.simpletour.company.web.controller.support.PageHelper;
import com.simpletour.company.web.enums.FormModeType;
import com.simpletour.company.web.form.system.EmployeeForm;
import com.simpletour.company.web.query.system.EmployeeQuery;
import com.simpletour.domain.system.Company;
import com.simpletour.domain.system.Employee;
import com.simpletour.domain.system.Role;
import com.simpletour.service.system.ICompanyService;
import com.simpletour.service.system.IEmployeeService;
import com.simpletour.service.system.IRoleService;
import org.apache.log4j.Logger;
import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Optional;

/**
 * Created by Mario on 2016/4/11.
 */
@Controller
@RequestMapping("/system/employee/")
public class EmployeeController extends BaseController {

    private static final Logger logger = Logger.getLogger(EmployeeController.class);

    private static final String DOMAIN = "人员";
    private static final String LIST_URL = "/system/employee/list";

    @Autowired
    private ICompanyService companyService;

    @Autowired
    private IRoleService roleService;

    @Autowired
    private IEmployeeService employeeService;

    @RequestMapping(value = "add", method = RequestMethod.GET)
    @RequiresPermissions(value = {"employee_add"})
    public String add(Model model) {
        this.setPageTitle(model, "添加人员信息");
        this.enableGoBack(model);
        EmployeeForm employeeForm = new EmployeeForm();
        model.addAttribute("viewForm", employeeForm);
        return "/system/employee/form";
    }

    @ResponseBody
    @RequiresPermissions(value = {"employee_add"})
    @RequestMapping(value = "add", method = RequestMethod.POST)
    public BaseDataResponse add(@RequestBody @Valid EmployeeForm employeeForm, BindingResult bindingResult, Model model) {
        employeeForm.setMode(FormModeType.ADD.getValue());
        this.setPageTitle(model, "添加人员信息");
        this.enableGoBack(model);
        if (bindingResult.hasErrors()) {
            return BaseDataResponse.validationFail().action(BaseAction.ADD_FAIL(DOMAIN, employeeForm.getName()), false);
        }
        try {
            Employee employee = employeeForm.as();
            Optional<Company> companyOptional = companyService.getCompanyById(employeeForm.getCompanyId());
            companyOptional.ifPresent(company -> employee.setCompany(company));
            Optional<Role> roleOptional = roleService.getRoleById(employeeForm.getRoleId());
            roleOptional.ifPresent(role -> employee.setRole(role));
            employeeService.addEmployee(employee);
        } catch (BaseSystemException e) {
            return BaseDataResponse.fail().msg(BaseAction.ADD_FAIL(DOMAIN).getTitle()).detail(e.getMessage());
        }
        return BaseDataResponse.ok().action(BaseAction.ADD_SUCCESS(DOMAIN, employeeForm.getName(), LIST_URL), true);
    }

    @RequiresPermissions(value = {"employee_list"})
    @RequestMapping(value = {"", "list"})
    public String list(EmployeeQuery employeeQuery, Model model) {
        this.setPageTitle(model, "人员信息列表");

        DomainPage<Employee> domainPage = employeeService.queryEmployeesPagesByConditions(employeeQuery.asConditionQuery());
        model.addAttribute("query", employeeQuery);
        model.addAttribute("page", domainPage);
        model.addAttribute("pageHelper", new PageHelper(domainPage));
        return "/system/employee/list";
    }

    @RequiresPermissions(value = {"employee_edit", "employee_detail", "employee_delete"}, logical = Logical.OR)
    @RequestMapping(value = "edit/{id}", method = RequestMethod.GET)
    public String edit(@PathVariable Long id, Model model) {
        this.setPageTitle(model, "编辑人员信息");
        this.enableGoBack(model);
        Optional<Employee> employeeOptional = employeeService.queryEmployeeById(id);
        if (employeeOptional.isPresent()) {
            EmployeeForm employeeForm = new EmployeeForm(employeeOptional.get());
            model.addAttribute("viewForm", employeeForm);
            return "/system/employee/form";
        }
        return this.notFound();
    }

    @ResponseBody
    @RequiresPermissions("employee_edit")
    @RequestMapping(value = "edit", method = RequestMethod.POST)
    public BaseDataResponse edit(@RequestBody @Valid EmployeeForm employeeForm, BindingResult bindingResult, Model model) {
        employeeForm.setMode(FormModeType.UPDATE.getValue());
        this.setPageTitle(model, "编辑人员信息");
        this.enableGoBack(model);
        if (bindingResult.hasErrors()) {
            return BaseDataResponse.validationFail().action(BaseAction.ADD_FAIL(DOMAIN, employeeForm.getName()), false);
        }
        try {
            Employee employee = employeeForm.as();
            Optional<Company> companyOptional = companyService.getCompanyById(employeeForm.getCompanyId());
            companyOptional.ifPresent(company -> employee.setCompany(company));
            Optional<Role> roleOptional = roleService.getRoleById(employeeForm.getRoleId());
            roleOptional.ifPresent(role -> employee.setRole(role));
            employeeService.updateEmployee(employee);
        } catch (BaseSystemException e) {
            return BaseDataResponse.fail().msg(BaseAction.EDIT_FAIL(DOMAIN).getTitle()).detail(e.getMessage());
        }
        return BaseDataResponse.ok().action(BaseAction.EDIT_SUCCESS(DOMAIN, employeeForm.getName(), LIST_URL), true);
    }

    @ResponseBody
    @RequiresPermissions(value = {"employee_delete"})
    @RequestMapping(value = "delete/{id}")
    public BaseDataResponse delete(@PathVariable Long id) {
        Optional<Employee> employeeOptional;
        try {
            employeeOptional = employeeService.queryEmployeeById(id);
            if (!employeeOptional.isPresent()) {
                return BaseDataResponse.fail().action(BaseAction.OBJECT_NOTFOUND(), false);
            }
        } catch (BaseSystemException e) {
            return BaseDataResponse.fail().msg(BaseAction.DEL_FAIL(DOMAIN).getTitle()).detail(e.getMessage());
        }
        try {
            employeeService.deleteEmployee(id);
        } catch (BaseSystemException e) {
            return BaseDataResponse.fail().msg(BaseAction.DEL_FAIL(DOMAIN).getTitle()).detail(e.getMessage());
        }
        return BaseDataResponse.ok().action(BaseAction.DEL_SUCCESS(DOMAIN, employeeOptional.get().getName(), LIST_URL), true);
    }

    @RequestMapping(value = "/password", method = RequestMethod.GET)
    public String password(Model model) {
//        Optional<Employee> employee = employeeService.getEmployeeById(getCurrentUser().id);
//        if (!employee.isPresent()) {
//            new BaseSystemException("用户不存在");
//        }
//        // 不显示用户信息
//        model.addAttribute("viewForm", new EmployeeForm());
        return "/password";
    }


    @ResponseBody
    @RequestMapping(value = "/test", method = RequestMethod.GET)
    public String test() {
        return "mario is good";
    }

}
