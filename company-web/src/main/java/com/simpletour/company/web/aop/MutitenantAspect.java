package com.simpletour.company.web.aop;

import com.simpletour.common.security.token.EncryptedToken;
import com.simpletour.common.security.token.Token;
import com.simpletour.company.web.shiro.ShiroDbRealm;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.aspectj.lang.JoinPoint;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by zt on 2016/1/13.
 */
public class MutitenantAspect {

    private static Logger logger = LoggerFactory.getLogger(MutitenantAspect.class);

    private void doBefore(JoinPoint joinPoint) {
        // 读取登录的用户
        Subject subject= SecurityUtils.getSubject();
        if (subject == null) {
            return;
        }
        ShiroDbRealm.ShiroUser user = (ShiroDbRealm.ShiroUser) subject.getPrincipal();
        if (user == null || user.getCompanyId() == null) {
            return;
        }
        String uid = null;
        if (user != null && user.jobNO != null) {
            uid = user.jobNO.toString();
        }
        String aid = null;
        if (user != null && user.id != null) {
            aid = user.id.toString();
        }
        String companyId = null;
        if (user != null && user.companyId != null) {
            companyId = user.companyId.toString();
        }
        String clientId = null;
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        // 解析请求头信息
        clientId = request.getRemoteAddr();
        new EncryptedToken(uid, aid, companyId, clientId, Token.ClientType.BROWSER);
    }
}
