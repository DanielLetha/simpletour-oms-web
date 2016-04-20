package com.simpletour.company.web.controller.system;

import com.simpletour.common.core.exception.BaseSystemException;
import com.simpletour.company.web.controller.support.BaseController;
import com.simpletour.company.web.controller.support.BaseDataResponse;
import com.simpletour.company.web.form.system.LoginForm;
import com.simpletour.company.web.form.system.ModifyPasswordForm;
import com.simpletour.company.web.util.PasswordUtil;
import com.simpletour.domain.company.Employee;
import com.simpletour.service.company.IEmployeeService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.util.Optional;


/**
 * Created by mario on 2016/4/12.
 */
@Controller
public class LoginController extends BaseController {

    @Resource
    private IEmployeeService employeeService;

    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public String login(Model model) {
        Subject currentUser = SecurityUtils.getSubject();
        if (currentUser.isRemembered() || (!currentUser.isRemembered() && currentUser.isAuthenticated())){
            return "redirect:/home";
        }
        model.addAttribute("loginForm", new LoginForm());
        return "login";
    }

    @ResponseBody
    @RequestMapping(value = "login", method = RequestMethod.POST)
    public BaseDataResponse login(@RequestBody @Valid LoginForm loginForm, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return BaseDataResponse.fail().msg("请检查输入");
        }
        UsernamePasswordToken token = new UsernamePasswordToken(loginForm.getUsername()
                , loginForm.getPassword(), loginForm.getRememberMe());
        Subject currentUser = SecurityUtils.getSubject();
        try {
            currentUser.login(token);
        } catch (AuthenticationException e) {
            return BaseDataResponse.fail().msg(e.getMessage());
        }
        if (currentUser.isAuthenticated()){
            return BaseDataResponse.ok().jumpUrl("/home");
        }
        return BaseDataResponse.fail().msg("网络异常");
    }

    @RequestMapping(value = "/password", method = RequestMethod.GET)
    public String password() {
        return "/password";
    }

    @ResponseBody
    @RequestMapping(value = "password/modify", method = RequestMethod.POST)
    public BaseDataResponse modifyPassword(@RequestBody @Valid ModifyPasswordForm modifyPasswordForm, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return BaseDataResponse.fail().msg("请检查输入");
        }
        Integer jobNo = getCurrentUser().getJobNo();
        String oldPasswd = modifyPasswordForm.getOldPassword();
        String newPasswd = modifyPasswordForm.getNewPassword();
        Optional<Employee> employeeOptional = employeeService.queryEmployeeByJobNo(jobNo);
        if (!employeeOptional.isPresent()) {
            return BaseDataResponse.fail().msg("账户不存在");
        }
        String oldEncodePasswd = PasswordUtil.getMd5Password(oldPasswd, employeeOptional.get().getSalt());
        if (!oldEncodePasswd.equals(employeeOptional.get().getPasswd())) {
            return BaseDataResponse.fail().msg("原密码不正确");
        }
        Employee employee = employeeOptional.get();
        String newEncodePasswd = PasswordUtil.getMd5Password(newPasswd, employeeOptional.get().getSalt());
        try {
            employee.setPasswd(newEncodePasswd);
            employeeService.updateEmployee(employee);
        } catch (BaseSystemException e) {
            return BaseDataResponse.fail().msg(e.getMessage());
        }
        return BaseDataResponse.ok();
    }

    @RequestMapping(value = "/home", method = RequestMethod.GET)
    public String home() {
        return "home";
    }

    @RequestMapping(value = "/welcome")
    public String welcome() {
        return "/welcome";
    }

    @RequestMapping(value = "/logout")
    public String logout(){
        Subject currentUser = SecurityUtils.getSubject();
        if (currentUser.isAuthenticated() || currentUser.isRemembered()){
            currentUser.logout();
        }
        return "/login";
    }
}
