package com.simpletour.company.web.aop;

import com.simpletour.commons.data.util.ThreadLocalUtil;
import com.simpletour.company.web.shiro.ShiroUser;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.aspectj.lang.JoinPoint;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by zt on 2016/1/13.
 */
public class MutitenantAspect {

    private static Logger logger = LoggerFactory.getLogger(MutitenantAspect.class);

    private void doBefore(JoinPoint joinPoint) {
        // 读取登录的用户
        Subject subject = SecurityUtils.getSubject();
        if (subject == null) {
            return;
        }
        ShiroUser user = (ShiroUser) subject.getPrincipal();
        if (user == null || user.getCompanyId() == null) {
            return;
        }
        Long companyId = null;
        if (user != null && user.getCompanyId() != null) {
            companyId = user.getCompanyId();
        }
        ThreadLocalUtil.setTenantId(companyId);
    }
}
