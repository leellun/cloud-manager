package com.newland.cloud.auth.model;

import lombok.Data;

/**
 * /oauth2/token傳參
 * Author: leell
 * Date: 2023/2/17 20:05:03
 */
@Data
public class OAuth2TokenParam {
    /**
     * clientid
     */
    private String client_id;
    /**
     * secret
     */
    private String client_secret;
    /**
     * 授权类型
     */
    private String grant_type;
    /**
     * 用户名
     */
    private String username;
    /**
     * 密码
     */
    private String password;
}
