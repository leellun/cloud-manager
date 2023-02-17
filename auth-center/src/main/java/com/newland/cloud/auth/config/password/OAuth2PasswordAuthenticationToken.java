package com.newland.cloud.auth.config.password;

import org.springframework.lang.Nullable;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.core.AuthorizationGrantType;
import org.springframework.security.oauth2.server.authorization.authentication.OAuth2AuthorizationGrantAuthenticationToken;
import org.springframework.util.Assert;

import java.util.Collections;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * 密码验证
 * @author leell
 */
public class OAuth2PasswordAuthenticationToken extends OAuth2AuthorizationGrantAuthenticationToken {

    private final Set<String> scopes;
    private final String username;
    private final String password;

    public OAuth2PasswordAuthenticationToken(Authentication clientPrincipal,
                                             @Nullable Set<String> scopes,
                                             @Nullable Map<String, Object> additionalParameters,
                                             @Nullable String username,
                                             @Nullable String password) {
        super(AuthorizationGrantType.PASSWORD, clientPrincipal, additionalParameters);
        Assert.notNull(clientPrincipal, "clientPrincipal cannot be null");
        this.scopes = Collections.unmodifiableSet((scopes != null && scopes.size() > 0) ? new HashSet<>(scopes) : Collections.emptySet());
        this.username = username;
        this.password = password;
    }

    public Set<String> getScopes() {
        return scopes;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }
}