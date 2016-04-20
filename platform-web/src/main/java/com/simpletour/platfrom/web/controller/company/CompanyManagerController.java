package com.simpletour.platfrom.web.controller.company;

import com.simpletour.commons.data.dao.query.ConditionOrderByQuery;
import com.simpletour.commons.data.domain.DomainPage;
import com.simpletour.domain.company.Employee;
import com.simpletour.platfrom.web.controller.support.BaseController;
import com.simpletour.platfrom.web.controller.support.PageHelper;
import com.simpletour.platfrom.web.query.company.CompanyManagerQuery;
import com.simpletour.service.company.IEmployeeService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;

/**
 * Author:  wangLin
 * Mail  :  wl@simpletour.com
 * Date  :  2016/4/11.
 * Remark: 公司管理员
 */
@Controller
@RequestMapping("/company/manager")
public class CompanyManagerController extends BaseController{

    private static final String DOMAIN = "公司管理员";

    @Resource
    private IEmployeeService employeeService;

    @RequestMapping(value = {"", "list"})
    public String list(CompanyManagerQuery query, Model model) {
        this.setPageTitle(model, "公司管理员列表");
        DomainPage<Employee> page = employeeService.queryEmployeesPagesByConditions(query.asQuery(ConditionOrderByQuery.class));

        model.addAttribute("page",page);
        model.addAttribute("query", query);
        model.addAttribute("pageHelper", new PageHelper(page));
        return "/company/manager/list";
    }

}
