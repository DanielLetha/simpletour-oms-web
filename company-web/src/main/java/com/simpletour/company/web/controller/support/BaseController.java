package com.simpletour.company.web.controller.support;

import com.simpletour.common.core.domain.CanLogicDelDomain;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.NoSuchMessageException;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;

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

    protected final void reject(Errors errors, String field, String key) {
        errors.rejectValue(field, key, getMessage(key));
    }

    protected String success(String title, String content, String jumpUrl, Model model) {
        model.addAttribute("title", title);
        model.addAttribute("content", content);
        if (jumpUrl != null)
            model.addAttribute("jumpUrl", jumpUrl);
        return "/admin/adminSuccess";
    }

    protected String success(String title, String content, List<BaseAction> actions, String jumpUrl, Model model) {
        model.addAttribute("title", title);
        model.addAttribute("content", content);
        if (actions != null && actions.size() > 0)
            model.addAttribute("actions", actions);
        if (jumpUrl != null && !jumpUrl.equals(""))
            model.addAttribute("jumpUrl", jumpUrl);
        return "/admin/adminSuccess";
    }

    protected String success(String content, String jumpUrl, Model model) {
        model.addAttribute("title", "操作成功");
        model.addAttribute("content", content);
        if (jumpUrl != null)
            model.addAttribute("jumpUrl", jumpUrl);
        return "/admin/adminSuccess";
    }

    protected int error(String title, Model model) {
        model.addAttribute("errorTitle", title);
        model.addAttribute("errorContent", "3秒后将自动关闭");
        return 0;
    }

    protected int error(String title, String content, Model model) {
        model.addAttribute("errorTitle", title);
        model.addAttribute("errorContent", content);
        return 0;
    }

    protected int error(Model model) {
        model.addAttribute("errorTitle", "操作失败");
        model.addAttribute("errorContent", "3秒后将自动关闭");
        return 0;
    }

    protected String notFound() {
        return "/adminNotFound";
    }

    protected void setPageTitle(Model model, String title) {
        model.addAttribute("pageTitle", title);
    }

    protected void enableGoBack(Model model) {
        model.addAttribute("pageGoBack", true);
    }

    protected <T extends CanLogicDelDomain> boolean isPresentAndNotDel(Optional<T> delDomain) {
        if (delDomain.isPresent() && delDomain.get().getDel() != null && !delDomain.get().getDel()) {
            return true;
        } else {
            return false;
        }
    }

    protected <T extends CanLogicDelDomain> boolean isPresentAndNotDel(T delDomain) {
        if (delDomain != null && delDomain.getDel() != null && !delDomain.getDel()) {
            return true;
        } else {
            return false;
        }
    }
}
