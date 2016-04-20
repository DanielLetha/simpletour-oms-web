package com.simpletour.platfrom.web.controller.support;

import com.simpletour.commons.data.domain.LogicalDeletableDomain;
import com.simpletour.platfrom.web.shiro.ShiroDbRealm;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.NoSuchMessageException;
import org.springframework.ui.Model;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Created by zt on 2015-11-19.
 */
public class BaseController {

    @Autowired
    MessageSource messageSource;

    public MessageSource getMessageSource() {
        return messageSource;
    }

    protected final String getMessage(String key) {
        try {
            return messageSource.getMessage(key, null, null);
        } catch (NoSuchMessageException e) {
            return "";
        }
    }

    /**
     * 获取当前用户信息
     *
     * @return
     */
    public ShiroDbRealm.ShiroUser getCurrentUser() {
        Subject subject = SecurityUtils.getSubject();
        if (subject != null) {
            Object object = subject.getPrincipal();
            if (object != null) {
                return (ShiroDbRealm.ShiroUser) object;
            }
        }
        return null;
    }

    /**
     * added by liangfei at 2015.12.8
     * 增加获取当前登录人权限的方法，避免在每个controller中重复
     *
     * @param module
     * @return
     */
    public List<String> getPermissionList(String module) {
        Subject s = SecurityUtils.getSubject();
        List<String> permList = new ArrayList<String>();
        if (s.isPermitted(module + "_add")) {
            permList.add("add");
        }
        if (s.isPermitted(module + "_edit")) {
            permList.add("edit");
        }
        if (s.isPermitted(module + "_detail")) {
            permList.add("detail");
        }
        if (s.isPermitted(module + "_delete")) {
            permList.add("delete");
        }
        return permList;
    }

    protected String error() {
        return "/error";
    }

    protected void setPageTitle(Model model, String title) {
        model.addAttribute("pageTitle", title);
    }

    protected void enableGoBack(Model model) {
        model.addAttribute("pageGoBack", true);
    }

    protected <T extends LogicalDeletableDomain> boolean isPresentAndNotDel(Optional<T> delDomain) {
        if (delDomain.isPresent() && delDomain.get().getDel() != null && !delDomain.get().getDel()) {
            return true;
        } else {
            return false;
        }
    }

    protected <T extends LogicalDeletableDomain> boolean isPresentAndNotDel(T delDomain) {
        if (delDomain != null && delDomain.getDel() != null && !delDomain.getDel()) {
            return true;
        } else {
            return false;
        }
    }
}
