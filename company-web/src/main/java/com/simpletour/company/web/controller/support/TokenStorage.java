package com.simpletour.company.web.controller.support;

import com.simpletour.common.security.token.ThreadLocalToken;
import com.simpletour.common.security.token.Token;

/**
 * 文件描述：Token存储类
 * 文件版本：1.0
 * 创建人员：石广路
 * 创建日期：2016/4/14 20:49
 * 备注说明：在未增加登录验证功能之前，新增的用以存储当前登录用户租户信息的临时解决方案
 */
public final class TokenStorage {
    public static final long COMPANY_ID = 4;

    public static void setLocalTokenWithCompanyId(Long uid, Long companyId) {
        ThreadLocalToken.setToken(new Token("1", "1", null == uid ? null : uid.toString(), "1", null == companyId ? null : companyId.toString(), "1", Token.ClientType.BROWSER) {
            @Override
            public String toCipherString() {
                return null;
            }
        });
    }

    public static void setLocalTokenWithCompanyId(Long uid) {
        setLocalTokenWithCompanyId(uid, COMPANY_ID);
    }
}
