package com.simpletour.company.web.controller.system;

import com.simpletour.commons.data.dao.query.ConditionOrderByQuery;
import com.simpletour.commons.data.dao.query.condition.AndConditionSet;
import com.simpletour.commons.data.domain.DomainPage;
import com.simpletour.commons.data.exception.BaseSystemException;
import com.simpletour.commons.util.PasswordUtil;
import com.simpletour.domain.company.Employee;
import com.simpletour.service.company.IEmployeeService;
import com.simpletour.service.sms.ISMSService;
import com.simpletour.sms.core.SMSTemplateEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.task.TaskExecutor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.*;

/**
 * 文件描述：找回密码前端控制层
 * 创建人员：石广路（shiguanglu@simpletour.com）
 * 创建日期：2016-4-12
 * 备注说明：null
 * @since 2.0-SNAPSHOT
 */
@Controller
@SessionAttributes(names = {"mobile", "code", "employee"})
public class FindPasswordController {
    @Autowired
    private IEmployeeService employeeService;

    @Autowired
    private ISMSService smsService;

    @Resource
    private TaskExecutor taskExecutor;

    @RequestMapping(value = "/findpass", method = RequestMethod.GET)
    public String findpass() {
        System.out.println("enter findpass page");
        return "findpass";
    }

    @RequestMapping(value = "/getcode", method = RequestMethod.POST)
    public void getcode(@RequestParam("mobile") String mobile, ModelMap model, HttpServletResponse response) {
        System.out.println("getcode: " + mobile);
        if (null == mobile || mobile.isEmpty()) {
            outputResponse(response, "请正确填写手机号码");
            return;
        }

        AndConditionSet andConditionSet = new AndConditionSet();
        andConditionSet.addCondition("c.mobile", mobile);

        ConditionOrderByQuery conditionOrderByQuery = new ConditionOrderByQuery();
        conditionOrderByQuery.setCondition(andConditionSet);
        conditionOrderByQuery.setPageIndex(1);
        conditionOrderByQuery.setPageSize(1);

        DomainPage<Employee> domainPage = employeeService.queryEmployeesPagesByConditions(conditionOrderByQuery);
        List<Employee> employeeList;
        if (0L == domainPage.getDomainTotalCount() || null == (employeeList = domainPage.getDomains()) || employeeList.isEmpty()) {
            outputResponse(response, "无法查找到关联此手机号的用户");
            return;
        }

        List<String> moblies = new ArrayList<>(1);
        moblies.add(mobile);
        int code = (int)((Math.random() * 9 + 1) * 100000);
        smsService.send(taskExecutor, moblies, SMSTemplateEnum.SIGNUPVERIFYCODE.getKey(), code + ",10");

        model.addAttribute("mobile", mobile);
        model.addAttribute("code", code);
        model.addAttribute("employee", employeeList.get(0));

        System.out.println("getcode: " + (mobile + code));
    }

    @RequestMapping(value = "/modify", method = RequestMethod.POST)
    public String modify(@RequestParam("mobile") String mobile, @RequestParam("code") String code, @RequestParam("new_password") String newPassword,
                         @RequestParam("confirm_password") String confirmPassword, ModelMap model, SessionStatus sessionStatus, HttpServletResponse response) {
        System.out.println("getcode: " + mobile + ", " + code + ", " + newPassword + ", " + confirmPassword);

        if (null == mobile || null == code || mobile.isEmpty() || code.isEmpty()) {
            outputResponse(response, "手机号码和验证码不能为空，请重新输入");
            return "findpass";
        }

        String savedMobile = model.get("mobile").toString();
        String savedCode = model.get("code").toString();
        if (!mobile.equals(savedMobile) || !code.equals(savedCode) || null == model.get("employee")) {
            outputResponse(response, "请先验证您绑定的手机号码");
            return "findpass";
        }

        if (!newPassword.equals(confirmPassword)) {
            outputResponse(response, "新密码与确认密码不一致，请重新输入");
            return "findpass";
        }

        Employee savedEmployee = (Employee)model.get("employee");
        String oldPass = savedEmployee.getPasswd();
        String newSalt = PasswordUtil.generateSalt();
        String newPass = PasswordUtil.getMd5Password(newPassword, newSalt);

        String url = "redirect:/login";
        Optional<Employee> optional = Optional.empty();
        try {
            optional = employeeService.changePwdForEmployee(savedEmployee.getId(), newPass, newSalt);
        } catch (BaseSystemException e) {
            url = "findpass";
        } finally {
            if (!optional.isPresent() || (null != oldPass && oldPass.equals(optional.get().getPasswd()))) {
                outputResponse(response, "新密码设置失败，请重新设置");
                return url;
            }
        }

        sessionStatus.setComplete();

        return url;
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
