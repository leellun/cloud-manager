package com.newland.cloud.gateway.filter;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson2.JSON;
import com.newland.cloud.constant.AuthConstant;
import com.newland.cloud.constant.Constant;
import com.newland.cloud.model.LoginUser;
import com.newland.cloud.utils.Base64Utils;
import com.newland.redis.utils.RedisUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;
import org.springframework.util.StringUtils;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.nio.charset.StandardCharsets;
import java.util.Map;

/**
 * Author: leell
 * Date: 2023/2/16 15:01:43
 */
@Slf4j
@Component
public class GatewayFilter implements GlobalFilter, Ordered {

    @Autowired
    private RedisUtil redisUtil;
    @Autowired
    private JwtDecoder jwtDecoder;

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        String requestUrl = exchange.getRequest().getPath().value();
        AntPathMatcher pathMatcher = new AntPathMatcher();
        System.out.println(requestUrl);
        //2 检查token是否存在
        String token = getToken(exchange);
        if (!StringUtils.hasText(token)) {
            return chain.filter(exchange);
        } else {
            try {
                Jwt jwt = jwtDecoder.decode(token);
                Long userId = jwt.getClaim(AuthConstant.PRINCIPAL);
                //获取用户权限
                LoginUser loginUser = redisUtil.get(Constant.USER_LOGIN_INFO + userId);
                if (loginUser != null) {
                    org.springframework.http.server.reactive.ServerHttpRequest tokenRequest = exchange.getRequest().mutate().header("json-token", Base64Utils.encodeToString(JSON.toJSONString(loginUser))).build();
                    ServerWebExchange build = exchange.mutate().request(tokenRequest).build();
                    return chain.filter(build);
                } else {
                    return chain.filter(exchange);
                }
            } catch (Exception e) {
                log.info("无效的token: {}", token);
                return chain.filter(exchange);
            }
        }
    }


    /**
     * 获取token
     */
    private String getToken(ServerWebExchange exchange) {
        String tokenStr = exchange.getRequest().getHeaders().getFirst("Authorization");
        if (!StringUtils.hasText(tokenStr)) {
            return null;
        }
        String token = tokenStr.split(" ")[1];
        if (!StringUtils.hasText(token)) {
            return null;
        }
        return token;
    }


    /**
     * 无效的token
     */
    private Mono<Void> invalidTokenMono(ServerWebExchange exchange) {
        JSONObject json = new JSONObject();
        json.put("status", HttpStatus.UNAUTHORIZED.value());
        json.put("data", "无效的token");
        return buildReturnMono(json, exchange);
    }

    private Mono<Void> buildReturnMono(JSONObject json, ServerWebExchange exchange) {
        ServerHttpResponse response = exchange.getResponse();
        byte[] bits = json.toJSONString().getBytes(StandardCharsets.UTF_8);
        DataBuffer buffer = response.bufferFactory().wrap(bits);
        response.setStatusCode(HttpStatus.UNAUTHORIZED);
        //指定编码，否则在浏览器中会中文乱码
        response.getHeaders().add("Content-Type", "text/plain;charset=UTF-8");
        return response.writeWith(Mono.just(buffer));
    }


    @Override
    public int getOrder() {
        return 0;
    }
}
