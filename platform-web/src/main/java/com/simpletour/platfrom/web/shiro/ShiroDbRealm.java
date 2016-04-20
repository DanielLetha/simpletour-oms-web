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
     * ��֤��¼
     * @param authcToken
     * @return
     * @throws AuthenticationException
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authcToken) throws AuthenticationException {
        UsernamePasswordToken token = (UsernamePasswordToken) authcToken;
        Optional<Administrator> adminOpt = administratorService.findAdminByJobNo(token.getUsername());
        if (!adminOpt.isPresent()) {
            throw new AuthenticationException("�˻�������");
        }
        if (!Administrator.Status.inservice.equals(adminOpt.get().getStatus())) {
            throw new AuthenticationException("��ǰ����Ա�Ѿ���ְ");
        }
        char[] newPassword = new Md5Hash(token.getPassword(), adminOpt.get().getSalt()).toBase64().toCharArray();
        if (!Arrays.equals(newPassword, adminOpt.get().getPassword().toCharArray())) {
            throw new AuthenticationException("��¼ʧ�ܣ������û���������");
        }
        token.setPassword(newPassword);
        Administrator admin = adminOpt.get();
        return new SimpleAuthenticationInfo(new ShiroUser(admin.getId(), admin.getJobNo(), admin.getName())
                , admin.getPassword(), admin.getName());
    }

    /**
     * Ȩ�޳�ʼ������Ϊ��ǰϵͳ������Ȩ�ޣ�������ȫȨ�޵�
     * @param principals
     * @return
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        return new SimpleAuthorizationInfo();
    }

    /**
     * �Զ���Authentication����ʹ��Subject����Я���û��ĵ�¼���⻹����Я��������Ϣ.
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
         * �������������ΪĬ�ϵ�<shiro:principal/>���.
         */
        @Override
        public String toString() {
            return name;
        }

        /**
         * ����hashCode,ֻ����jobNo;
         */
        @Override
        public int hashCode() {
            return Objects.hashCode(jobNO);
        }

        /**
         * ����equals,ֻ����jobNo;
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