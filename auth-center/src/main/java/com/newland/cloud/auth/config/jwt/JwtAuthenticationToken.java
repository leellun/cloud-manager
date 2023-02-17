package com.newland.cloud.auth.config.jwt;

import com.newland.cloud.auth.common.GrantType;
import org.springframework.lang.Nullable;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.SpringSecurityCoreVersion;
import org.springframework.security.oauth2.core.AuthorizationGrantType;
import org.springframework.security.oauth2.server.authorization.authentication.OAuth2AuthorizationGrantAuthenticationToken;

import java.util.*;

/**
 * 密码验证
 *
 * @author leell
 */
public class JwtAuthenticationToken extends OAuth2AuthorizationGrantAuthenticationToken {

    private static final long serialVersionUID = SpringSecurityCoreVersion.SERIAL_VERSION_UID;
    private final Set<String> scopes;
    private final String username;
    private final String password;

    /**
     * Constructs a {@code JwtAuthenticationToken} using the provided parameters.
     */
    public JwtAuthenticationToken(Authentication clientPrincipal, @Nullable Set<String> scopes, @Nullable Map<String, Object> additionalParameters, @Nullable String username,
                                  @Nullable String password) {
        super(new AuthorizationGrantType(GrantType.JWT.getValue()), clientPrincipal, additionalParameters);
        this.scopes = scopes;
        this.username = username;
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public Set<String> getScopes() {
        return scopes;
    }
}