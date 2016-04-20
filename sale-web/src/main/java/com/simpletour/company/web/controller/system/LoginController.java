package com.simpletour.company.web.controller.system;

import com.simpletour.company.web.controller.support.BaseDataResponse;
import com.simpletour.company.web.form.system.LoginForm;
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

import javax.validation.Valid;


/**
 * Created by mario on 2016/4/12.
 */
@Controller
public class LoginController {
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
