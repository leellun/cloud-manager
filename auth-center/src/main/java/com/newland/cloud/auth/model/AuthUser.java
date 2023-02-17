package com.newland.cloud.auth.model;

import com.newland.cloud.model.LoginUser;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;

import java.util.ArrayList;
import java.util.Collection;

/**
 * 认证auth用户
 * Author: leell
 * Date: 2023/2/16 00:03:18
 */
public class AuthUser extends User {
    private LoginUser loginUser;

    public AuthUser(LoginUser loginUser) {
        super(loginUser.getUsername(), "", new ArrayList<>());
        this.loginUser = loginUser;
    }

    public AuthUser(String username, String password, Collection<? extends GrantedAuthority> authorities) {
        super(username, password, authorities);
    }

    public AuthUser(String username, String password, LoginUser loginUser) {
        super(username, password, AuthorityUtils.createAuthorityList());
        this.loginUser = loginUser;
    }

    public AuthUser(String username, String password, boolean enabled, boolean accountNonExpired, boolean credentialsNonExpired, boolean accountNonLocked, Collection<? extends GrantedAuthority> authorities) {
        super(username, password, enabled, accountNonExpired, credentialsNonExpired, accountNonLocked, authorities);
    }

    public void setLoginUser(LoginUser loginUser) {
        this.loginUser = loginUser;
    }

    public LoginUser getLoginUser() {
        return loginUser;
    }
}
