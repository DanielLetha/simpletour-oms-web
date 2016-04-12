package com.simpletour.company.web.shiro;

import com.google.common.base.Objects;
import com.simpletour.service.system.IEmployeeService;
import com.simpletour.service.system.IRoleService;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.Serializable;

/**
 * Created by zt on 2015/11/20.
 */
@Component
public class ShiroDbRealm extends AuthorizingRealm {

    @Autowired
    protected IEmployeeService employeeService;
    @Autowired
    protected IRoleService roleService;

    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
//        ShiroUser shiroUser = (ShiroUser) principals.getPrimaryPrincipal();
//
//        Optional<Employee> user = employeeService.getEmployeeByJobNo(shiroUser.jobNO);
//        SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
//        info.addRole(user.get().getRole().getCode());
//        Optional<Role> role = roleService.getById(user.get().getRole().getId());
//        if (role.isPresent() && role.get().getPermissionList() != null && role.get().getPermissionList().size() > 0) {
//            for (Permission permission : role.get().getPermissionList()) {
//                // 由于Permission采用状态删除，需要保证SimpleAuthorizationInfo中加入的Permission是有效状态
//                if (permission != null && !permission.getDel()) info.addStringPermission(permission.getCode());
//            }
//        }
//        return info;
        return null;
    }

    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authcToken) throws AuthenticationException {
//        UsernamePasswordToken token = (UsernamePasswordToken) authcToken;
//        Optional<Employee> user = employeeService.getEmployeeByJobNo(Integer.valueOf(token.getUsername()));
//
//        if (user.isPresent() && user.get().getStatus().equals(Employee.Status.inservice)) {
//            token.setPassword(new Md5Hash(token.getPassword(), user.get().getSalt()).toBase64().toCharArray());
//            if (!Arrays.equals(token.getPassword(), user.get().getPasswd().toCharArray())) {
//                throw new AuthenticationException();
//            }
//            return new SimpleAuthenticationInfo(new ShiroUser(user.get().getId(), user.get().getJobNo(), user.get().getName(),
//                    user.get().getCompany() != null ? user.get().getCompany().getId() : null), user.get().getPasswd(), user.get().getName());
//        } else {
//            return null;
//        }
        return null;
    }

    public void setEmployeeService(IEmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    public void setRoleService(IRoleService roleService) {
        this.roleService = roleService;
    }

    /**
     * 自定义Authentication对象，使得Subject除了携带用户的登录名外还可以携带更多信息.
     */
    public static class ShiroUser implements Serializable {
        private static final long serialVersionUID = -1373760761780840081L;
        public Long id;
        public Integer jobNO;
        public String name;
        public Long companyId;

        public ShiroUser(Long id, Integer jobNO, String name, Long companyId) {
            this.id = id;
            this.jobNO = jobNO;
            this.name = name;
            this.companyId = companyId;
        }

        public String getName() {
            return name;
        }

        public Long getCompanyId() {
            return companyId;
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
