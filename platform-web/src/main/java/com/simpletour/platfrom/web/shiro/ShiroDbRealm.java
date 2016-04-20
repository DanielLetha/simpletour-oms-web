package com.simpletour.platfrom.web.shiro;


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
import java.io.Serializable;
import java.util.Arrays;
import java.util.Objects;
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
        ShiroDbRealm.ShiroUser shiroUser = (ShiroDbRealm.ShiroUser) principals.getPrimaryPrincipal();
        Optional<Employee> user = employeeService.queryEmployeeByJobNo(Integer.parseInt(shiroUser.getJobNO()));
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
            return new SimpleAuthenticationInfo(new ShiroUser(employeeOptional.get().getId()
                    , String.valueOf(employeeOptional.get().getJobNo()), employeeOptional.get().getName())
                    , employeeOptional.get().getPasswd(), getName());
        }
        throw new AuthenticationException("登录失败");
    }

    /**
     * 自定义Authentication对象，使得Subject除了携带用户的登录名外还可以携带更多信息.
     */
    public static class ShiroUser implements Serializable {
        private static final long serialVersionUID = -1373760761780840081L;
        private Long id;
        private String jobNO;
        private String name;

        public ShiroUser(Long id, String jobNO, String name) {
            this.id = id;
            this.jobNO = jobNO;
            this.name = name;
        }

        public String getName() {
            return name;
        }

        public Long getId() {
            return id;
        }

        public String getJobNO() {
            return jobNO;
        }

        /**
         * 本函数输出将作为默认的<shiro:principal/>输出.
         */
        @Override
        public String toString() {
            return name;
        }

        /**
         * 重载hashCode,只计算jobNo;
         */
        @Override
        public int hashCode() {
            return Objects.hashCode(jobNO);
        }

        /**
         * 重载equals,只计算jobNo;
         */
        @Override
        public boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            if (obj == null) {
                return false;
            }
            if (getClass() != obj.getClass()) {
                return false;
            }
            ShiroUser other = (ShiroUser) obj;
            if (jobNO == null) {
                if (other.jobNO != null) {
                    return false;
                }
            } else if (!jobNO.equals(other.jobNO)) {
                return false;
            }
            return true;
        }
    }
}
