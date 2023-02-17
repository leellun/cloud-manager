package com.newland.cloud.auth.common;

/**
 * tokentype
 * Author: leell
 * Date: 2023/2/15 13:32:46
 */
public class TokenType {
    public static final GrantType JWT = new GrantType("jwt");
    public static final GrantType JWT_REFRESH_TOKEN = new GrantType("jwt_refresh_token");
    private final String value;

    public TokenType(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
