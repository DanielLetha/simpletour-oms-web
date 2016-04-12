package com.simpletour.company.web.aop;

import com.alibaba.fastjson.JSON;
import com.simpletour.company.web.form.support.BaseForm;
import com.simpletour.company.web.query.support.Query;
import com.simpletour.company.web.shiro.ShiroDbRealm;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by zt on 2015/12/18.
 */
public class ControllerLogAspect {

    private static Logger logger = LoggerFactory.getLogger(ControllerLogAspect.class);
    private long startTimeMillis = 0L; // 开始时间
    private long endTimeMillis = 0L; // 结束时间
    private String requestIP = null ; // 请求源IP
    private String requestURI = null ; // 请求地址
    private String inputParam = null ; // 传入参数
    private String userStr = null ; // 用户信息

    private void doBefore(JoinPoint joinPoint) {
        // 读取登录的用户
        Subject Subject= SecurityUtils.getSubject();
        if (Subject != null) {
            ShiroDbRealm.ShiroUser user = (ShiroDbRealm.ShiroUser) Subject.getPrincipal();
            if (user != null ){
                userStr = user.getName();
            } else {
                userStr = "unlogin_user";
            }
        }
        // 获取传入参数
        Object[] args = joinPoint.getArgs();
        if (args != null && args.length >= 1){
            for (Object o :args) {
                if (o != null && (o instanceof BaseForm || o instanceof Query)) {
                    inputParam = JSON.toJSONString(o);
                    break;
                }
            }
        }
        if (inputParam == null) {
            inputParam = "no_input_param";
        }
    }

    private Object doAround(ProceedingJoinPoint pjp) throws Throwable {

        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        // 解析请求头信息
        requestIP = request.getRemoteAddr();
        requestURI = request.getRequestURI();
        startTimeMillis = System.currentTimeMillis(); // 记录方法开始执行的时间
        Object retVal = pjp.proceed();
        endTimeMillis = System.currentTimeMillis(); // 记录方法执行完成的时间
        printLog();
        return retVal;
    }

    private void printLog() {
        StringBuffer sb = new StringBuffer();
        sb.append(userStr).append(" ");
        sb.append(requestIP).append(" ");
        sb.append(requestURI).append(" ");
        sb.append(inputParam).append(" ");
        sb.append(endTimeMillis - startTimeMillis).append("ms");
        logger.info(sb.toString());
    }
}
