/*
 * Copyright 2020-2022 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.newland.cloud.auth.config;

import com.newland.cloud.auth.config.core.FormIdentityLoginConfigurer;
import com.newland.cloud.auth.config.filter.AuthExceptionTranslationFilter;
import com.newland.cloud.auth.config.handler.AuthAuthenticationFailureHandler;
import com.newland.cloud.auth.config.handler.AuthAuthenticationSuccessHandler;
import com.newland.cloud.auth.config.jwt.JwtAuthenticationConverter;
import com.newland.cloud.auth.config.jwt.JwtAuthenticationProvider;
import com.newland.cloud.auth.config.jwt.JwtRefreshAuthenticationConverter;
import com.newland.cloud.auth.config.jwt.JwtRefreshAuthenticationProvider;
import com.newland.cloud.auth.config.password.OAuth2PasswordAuthenticationConverter;
import com.newland.cloud.auth.config.password.OAuth2PasswordAuthenticationProvider;
import com.newland.redis.utils.RedisUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.server.authorization.JdbcOAuth2AuthorizationService;
import org.springframework.security.oauth2.server.authorization.OAuth2AuthorizationService;
import org.springframework.security.oauth2.server.authorization.client.JdbcRegisteredClientRepository;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClientRepository;
import org.springframework.security.oauth2.server.authorization.config.annotation.web.configurers.OAuth2AuthorizationServerConfigurer;
import org.springframework.security.oauth2.server.authorization.settings.AuthorizationServerSettings;
import org.springframework.security.oauth2.server.authorization.token.OAuth2TokenGenerator;
import org.springframework.security.oauth2.server.authorization.web.authentication.*;
import org.springframework.security.web.DefaultSecurityFilterChain;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.ExceptionTranslationFilter;
import org.springframework.security.web.authentication.AuthenticationConverter;
import org.springframework.security.web.util.matcher.RequestMatcher;

import java.util.Arrays;

import static org.springframework.security.config.Customizer.withDefaults;

/**
 * @author leellun
 */
@Configuration(proxyBeanMethods = false)
public class AuthorizationServerConfig {
    @Autowired
    private AuthenticationProvider authDaoAuthenticationProvider;

    @Bean
    public OAuth2AuthorizationService authorizationService(JdbcTemplate jdbcTemplate, RegisteredClientRepository registeredClientRepository) {
        return new JdbcOAuth2AuthorizationService(jdbcTemplate, registeredClientRepository);
    }

    @Bean
    public RegisteredClientRepository registeredClientRepository(JdbcTemplate jdbcTemplate) {
        JdbcRegisteredClientRepository registeredClientRepository = new JdbcRegisteredClientRepository(jdbcTemplate);
        return registeredClientRepository;
    }

    @Bean
    public SecurityFilterChain defaultSecurityFilterChain(HttpSecurity http) throws Exception {
        http.authorizeHttpRequests(authorize -> authorize.anyRequest().authenticated())
                .formLogin(withDefaults());
        return http.build();
    }

    @Bean
    @Order(Ordered.HIGHEST_PRECEDENCE)
    public SecurityFilterChain authorizationServerSecurityFilterChain(HttpSecurity http, OAuth2AuthorizationService authorizationService) throws Exception {
        OAuth2AuthorizationServerConfigurer authorizationServerConfigurer = new OAuth2AuthorizationServerConfigurer();

        http.apply(authorizationServerConfigurer.tokenEndpoint((tokenEndpoint) -> {
            tokenEndpoint.accessTokenRequestConverter(accessTokenRequestConverter())
                    .accessTokenResponseHandler(new AuthAuthenticationSuccessHandler())
                    .errorResponseHandler(new AuthAuthenticationFailureHandler());
        }).clientAuthentication(oAuth2ClientAuthenticationConfigurer ->
                oAuth2ClientAuthenticationConfigurer.errorResponseHandler(new AuthAuthenticationFailureHandler())));
        RequestMatcher endpointsMatcher = authorizationServerConfigurer.getEndpointsMatcher();

        DefaultSecurityFilterChain securityFilterChain = http.securityMatcher(endpointsMatcher)
                .addFilterAfter(new AuthExceptionTranslationFilter(), ExceptionTranslationFilter.class)
                .authorizeRequests(authorizeRequests -> authorizeRequests.anyRequest().authenticated())
                .apply(authorizationServerConfigurer.authorizationService(authorizationService)
                        .authorizationServerSettings(AuthorizationServerSettings.builder().build()))
                // 授权码登录的登录页个性化
                .and().apply(new FormIdentityLoginConfigurer()).and().build();

        // 注入自定义授权模式实现
        addCustomOAuth2GrantAuthenticationProvider(http, authorizationService);
        return securityFilterChain;
    }

    /**
     * 令牌生成规则实现 </br>
     * client:username:uuid
     *
     * @return OAuth2TokenGenerator
     */

    /**
     * request -> xToken 注入请求转换器
     *
     * @return DelegatingAuthenticationConverter
     */
    private AuthenticationConverter accessTokenRequestConverter() {
        return new DelegatingAuthenticationConverter(Arrays.asList(
                new OAuth2PasswordAuthenticationConverter(),
                new JwtAuthenticationConverter(),
                new JwtRefreshAuthenticationConverter(),
                new OAuth2RefreshTokenAuthenticationConverter(),
                new OAuth2ClientCredentialsAuthenticationConverter(),
                new OAuth2AuthorizationCodeAuthenticationConverter(),
                new OAuth2AuthorizationCodeRequestAuthenticationConverter()));
    }

    @Autowired
    private OAuth2TokenGenerator oAuth2TokenGenerator;
    @Autowired
    private JwtDecoder jwtDecoder;

    /**
     * 注入授权模式实现提供方
     * <p>
     * 1. 密码模式 </br>
     * 2. 短信登录 </br>
     */
    @SuppressWarnings("unchecked")
    private void addCustomOAuth2GrantAuthenticationProvider(HttpSecurity http, OAuth2AuthorizationService authorizationService) {
        AuthenticationManager authenticationManager = http.getSharedObject(AuthenticationManager.class);
        OAuth2PasswordAuthenticationProvider oAuth2PasswordAuthenticationProvider = new OAuth2PasswordAuthenticationProvider(authenticationManager, authorizationService, oAuth2TokenGenerator);
        JwtAuthenticationProvider jwtAuthenticationProvider = new JwtAuthenticationProvider(
                authenticationManager, oAuth2TokenGenerator);
        JwtRefreshAuthenticationProvider jwtRefreshAuthenticationProvider = new JwtRefreshAuthenticationProvider(authenticationManager, oAuth2TokenGenerator, jwtDecoder);

        http.authenticationProvider(authDaoAuthenticationProvider);
        // 处理 用户密码 token方式登录
        http.authenticationProvider(oAuth2PasswordAuthenticationProvider);
        // 处理 jwt
        http.authenticationProvider(jwtAuthenticationProvider);
        http.authenticationProvider(jwtRefreshAuthenticationProvider);
    }
}
