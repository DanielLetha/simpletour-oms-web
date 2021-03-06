package com.simpletour.company.web.controller.system;

import com.simpletour.company.web.form.system.LoginForm;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.validation.Valid;
import java.util.stream.Collectors;


/**
 * Created by mario on 2016/4/12.
 */
@Controller
public class LoginController {
    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public String login(Model model) {
        // TODO: 当前版本没有登录鉴权功能，这里先不获取当前登录用户信息
//        Subject currentUser = SecurityUtils.getSubject();
//        if (!currentUser.isAuthenticated() && currentUser.isRemembered()) {
//            return "redirect:/home";
//        }
        model.addAttribute("loginForm", new LoginForm());
        return "login";
    }

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public String authenticate(@Valid LoginForm loginForm, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("loginError", bindingResult.getAllErrors().stream().map(objectError -> objectError.getDefaultMessage()).collect(Collectors.toList()));
            return "login";
        }
        UsernamePasswordToken token = new UsernamePasswordToken(loginForm.getUserName(), loginForm.getPassword());
        token.setRememberMe(Boolean.valueOf(loginForm.getRememberMe()));
        Subject currentUser = SecurityUtils.getSubject();
        try {
            currentUser.login(token);
        } catch (AuthenticationException e) {
            model.addAttribute("errorInfo", "用户名或密码错误");
            return "login";
        }
        return "redirect:/home";
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
    public String logout() {
        Subject currentUser = SecurityUtils.getSubject();
        if (currentUser.isAuthenticated()) {
            currentUser.logout();
        }
        return "/login";
    }
}
