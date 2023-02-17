package com.newland.cloud.security.config;

import com.newland.cloud.security.AuthenticationFilter;
import com.newland.cloud.security.handler.LoginUrlAuthenticationEntryPoint;
import com.newland.cloud.security.handler.NewlandAccessDeniedHandler;
import com.newland.cloud.security.properties.HttpItem;
import com.newland.cloud.security.properties.SecurityProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

/**
 * 资源服务配置
 */
@Configuration
@EnableWebSecurity
@EnableConfigurationProperties(SecurityProperties.class)
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class WebSecurityConfig {
    @Autowired
    private SecurityProperties securityProperties;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests(customer -> customer.requestMatchers(HttpMethod.OPTIONS, "/swagger-ui.html", "/webjars/**", "/swagger-resources/**", "/v2/*", "/csrf", "/").permitAll())
                .addFilterBefore(new AuthenticationFilter(), UsernamePasswordAuthenticationFilter.class);
        if (securityProperties.getPermitItems() != null && securityProperties.getPermitItems().length > 0) {
            for (HttpItem httpItem : securityProperties.getPermitItems()) {
                if (httpItem.getMethod() == null) {
                    http.authorizeHttpRequests().requestMatchers(httpItem.getUrl()).permitAll();
                } else {
                    http.authorizeHttpRequests().requestMatchers(HttpMethod.valueOf(httpItem.getMethod()), httpItem.getUrl()).permitAll();
                }
            }
        }
        http.authorizeHttpRequests().anyRequest().authenticated();
        http.exceptionHandling()
                .accessDeniedHandler(new NewlandAccessDeniedHandler())
                .authenticationEntryPoint(new LoginUrlAuthenticationEntryPoint())
                .and().headers().cacheControl().disable();
        http.csrf().disable();
        return http.build();
    }

}
