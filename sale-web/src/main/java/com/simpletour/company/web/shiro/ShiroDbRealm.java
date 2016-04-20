package com.simpletour.company.web.shiro;

import com.simpletour.domain.company.Employee;
import com.simpletour.service.company.IEmployeeService;
import com.simpletour.service.company.IRoleService;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationException;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.crypto.hash.Md5Hash;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Arrays;
import java.util.Optional;

/**
 * Created by mario on 2016/4/12.
 */
@Component
public class ShiroDbRealm extends AuthorizingRealm {

    @Resource
    private IEmployeeService employeeService;

    @Resource
    private IRoleService roleService;

    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        ShiroUser shiroUser = (ShiroUser) principals.getPrimaryPrincipal();
        Optional<Employee> user = employeeService.queryEmployeeByJobNo(shiroUser.getJobNo());
        if (!user.isPresent()) throw new AuthorizationException();
        return new SimpleAuthorizationInfo();
    }

    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authcToken) throws AuthenticationException {
        UsernamePasswordToken token = (UsernamePasswordToken) authcToken;
        Optional<Employee> employeeOptional = employeeService.queryEmployeeByJobNo(Integer.valueOf(token.getUsername()));
        if (employeeOptional.isPresent() && !employeeOptional.get().getDel()) {
            token.setPassword(new Md5Hash(token.getPassword(), employeeOptional.get().getSalt()).toBase64().toCharArray());
            if (!Arrays.equals(token.getPassword(), employeeOptional.get().getPasswd().toCharArray())) {
                throw new AuthenticationException("账户和密码错误");
            }
            return new SimpleAuthenticationInfo(new ShiroUser(employeeOptional.get().getId(), employeeOptional.get().getJobNo(), employeeOptional.get().getName(),
                    employeeOptional.get().getCompany() != null ? employeeOptional.get().getCompany().getId() : null), employeeOptional.get().getPasswd(), getName());
        }
        throw new AuthenticationException("登录失败");
    }
}
