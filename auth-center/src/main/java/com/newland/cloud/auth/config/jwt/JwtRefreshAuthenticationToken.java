package com.newland.cloud.auth.config.jwt;

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
 * jwt刷新token
 * Author: leell
 * Date: 2023/2/15 15:40:57
 */
public class JwtRefreshAuthenticationToken extends OAuth2AuthorizationGrantAuthenticationToken {
    private final String refreshToken;
    private final Set<String> scopes;

    /**
     * Constructs an {@code OAuth2RefreshTokenAuthenticationToken} using the provided parameters.
     *
     * @param refreshToken the refresh token
     * @param clientPrincipal the authenticated client principal
     * @param scopes the requested scope(s)
     * @param additionalParameters the additional parameters
     */
    public JwtRefreshAuthenticationToken(String refreshToken, Authentication clientPrincipal,
                                                 @Nullable Set<String> scopes, @Nullable Map<String, Object> additionalParameters) {
        super(AuthorizationGrantType.REFRESH_TOKEN, clientPrincipal, additionalParameters);
        Assert.hasText(refreshToken, "refreshToken cannot be empty");
        this.refreshToken = refreshToken;
        this.scopes = Collections.unmodifiableSet(
                scopes != null ? new HashSet<>(scopes) : Collections.emptySet());
    }

    /**
     * Returns the refresh token.
     *
     * @return the refresh token
     */
    public String getRefreshToken() {
        return this.refreshToken;
    }

    /**
     * Returns the requested scope(s).
     *
     * @return the requested scope(s), or an empty {@code Set} if not available
     */
    public Set<String> getScopes() {
        return this.scopes;
    }
}
