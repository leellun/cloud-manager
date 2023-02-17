package com.newland.cloud.auth.config.jwt;

import com.newland.cloud.auth.common.AuthConstant;
import com.newland.cloud.auth.common.TokenType;
import com.newland.cloud.auth.model.AuthUser;
import com.newland.cloud.constant.Constant;
import com.newland.cloud.model.LoginUser;
import com.newland.redis.utils.RedisUtil;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;
import org.springframework.security.oauth2.core.ClaimAccessor;
import org.springframework.security.oauth2.core.OAuth2AccessToken;
import org.springframework.security.oauth2.core.endpoint.OAuth2ParameterNames;
import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.JwtEncoderParameters;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClient;
import org.springframework.security.oauth2.server.authorization.token.OAuth2TokenContext;
import org.springframework.security.oauth2.server.authorization.token.OAuth2TokenGenerator;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.time.Instant;
import java.util.Collections;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

/**
 * token构建起
 *
 * @author leellun
 */
public class JwtAccessTokenGenerator implements OAuth2TokenGenerator<OAuth2AccessToken> {
    private final JwtEncoder jwtEncoder;
    private final RedisUtil redisUtil;

    public JwtAccessTokenGenerator(@NonNull JwtEncoder jwtEncoder, RedisUtil redisUtil) {
        this.jwtEncoder = jwtEncoder;
        this.redisUtil = redisUtil;
    }

    @Nullable
    @Override
    public OAuth2AccessToken generate(OAuth2TokenContext context) {
        if (!TokenType.JWT.getValue().equals(context.getTokenType().getValue())) {
            return null;
        }
        RegisteredClient registeredClient = context.getRegisteredClient();
        Instant issuedAt = Instant.now();
        Instant expiresAt = issuedAt.plus(registeredClient.getTokenSettings().getAccessTokenTimeToLive());

        JwtClaimsSet.Builder claimsBuilder = JwtClaimsSet.builder();
        if (context.getAuthorizationServerContext() != null && StringUtils.hasText(context.getAuthorizationServerContext().getIssuer())) {
            claimsBuilder.issuer(context.getAuthorizationServerContext().getIssuer());
        }
        long time = 0;
        if (registeredClient.getTokenSettings().getAccessTokenTimeToLive() != null) {
            time = registeredClient.getTokenSettings().getAccessTokenTimeToLive().toSeconds();
        }
        if (registeredClient.getTokenSettings().getRefreshTokenTimeToLive() != null) {
            time = Math.max(time, registeredClient.getTokenSettings().getAccessTokenTimeToLive().toSeconds());
        }
        AuthUser authUser = (AuthUser) context.getPrincipal().getPrincipal();
        LoginUser loginUser = authUser.getLoginUser();
        redisUtil.set(Constant.USER_LOGIN_INFO + loginUser.getUserId(), loginUser, time);
        claimsBuilder
                .subject(context.getPrincipal().getName())
                .claim(AuthConstant.PRINCIPAL, loginUser.getUserId())
                .audience(Collections.singletonList(registeredClient.getClientId()))
                .issuedAt(issuedAt)
                .expiresAt(expiresAt)
                .notBefore(issuedAt)
                .id(UUID.randomUUID().toString());
        if (!CollectionUtils.isEmpty(context.getAuthorizedScopes())) {
            claimsBuilder.claim(OAuth2ParameterNames.SCOPE, context.getAuthorizedScopes());
        }
        this.jwtEncoder.encode(JwtEncoderParameters.from(claimsBuilder.build()));
        JwtClaimsSet jwtClaimsSet = claimsBuilder.build();
        return new OAuth2AccessTokenClaims(OAuth2AccessToken.TokenType.BEARER,
                this.jwtEncoder.encode(JwtEncoderParameters.from(jwtClaimsSet)).getTokenValue(), jwtClaimsSet.getIssuedAt(), jwtClaimsSet.getExpiresAt(),
                context.getAuthorizedScopes(), jwtClaimsSet.getClaims());
    }

    private static final class OAuth2AccessTokenClaims extends OAuth2AccessToken implements ClaimAccessor {
        private final Map<String, Object> claims;

        private OAuth2AccessTokenClaims(TokenType tokenType, String tokenValue, Instant issuedAt, Instant expiresAt,
                                        Set<String> scopes, Map<String, Object> claims) {
            super(tokenType, tokenValue, issuedAt, expiresAt, scopes);
            this.claims = claims;
        }

        @Override
        public Map<String, Object> getClaims() {
            return this.claims;
        }

    }

}
