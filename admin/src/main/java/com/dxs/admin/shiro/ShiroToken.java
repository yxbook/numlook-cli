package com.dxs.admin.shiro;

import org.apache.shiro.authc.AuthenticationToken;

/**
 * Created by Mr.Dxs on 2018/8/5.
 */
public class ShiroToken implements AuthenticationToken {

    /**
     * principal\credentials 此时都返回 account
     *
     */
    private String principal;       // account + token(客户端通过 header 传递的 token)


    public ShiroToken(String principal) {
        this.principal = principal;
    }

    @Override
    public Object getPrincipal() {
        return this.principal;
    }

    @Override
    public Object getCredentials() {
        // 获取account
        String account = this.principal.split(":")[0];
        return account;
    }

}
