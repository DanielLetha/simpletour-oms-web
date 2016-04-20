package com.simpletour.platfrom.web.shiro;

import com.simpletour.domain.company.Administrator;
import com.simpletour.service.company.IAdministratorService;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.crypto.hash.Md5Hash;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;

import javax.annotation.Resource;
import java.io.Serializable;
import java.util.Arrays;
import java.util.Objects;
import java.util.Optional;

/**
 * Created by zt on 2015/11/20.
 */
public class ShiroDbRealm extends AuthorizingRealm {

    @Resource
    private IAdministratorService administratorService;

    /**
     * 验证登录
     * @param authcToken
     * @return
     * @throws AuthenticationException
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authcToken) throws AuthenticationException {
        UsernamePasswordToken token = (UsernamePasswordToken) authcToken;
        Optional<Administrator> adminOpt = administratorService.findAdminByJobNo(token.getUsername());
        if (!adminOpt.isPresent()) {
            throw new AuthenticationException("账户不存在");
        }
        if (!Administrator.Status.inservice.equals(adminOpt.get().getStatus())) {
            throw new AuthenticationException("当前管理员已经离职");
        }
        char[] newPassword = new Md5Hash(token.getPassword(), adminOpt.get().getSalt()).toBase64().toCharArray();
        if (!Arrays.equals(newPassword, adminOpt.get().getPassword().toCharArray())) {
            throw new AuthenticationException("登录失败，请检查用户名与密码");
        }
        token.setPassword(newPassword);
        Administrator admin = adminOpt.get();
        return new SimpleAuthenticationInfo(new ShiroUser(admin.getId(), admin.getJobNo(), admin.getName())
                , admin.getPassword(), admin.getName());
    }

    /**
     * 权限初始化，因为当前系统不存在权限，所以是全权限的
     * @param principals
     * @return
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        return new SimpleAuthorizationInfo();
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