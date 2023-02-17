package com.newland.cloud.auth.common;

/**
 * 获取granttype
 * Author: leell
 * Date: 2023/2/15 12:59:24
 */
public class GrantType {
    public static final GrantType JWT = new GrantType("jwt");
    public static final GrantType JWT_REFRESH_TOKEN = new GrantType("jwt_refresh_token");
    private final String value;

    public GrantType(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
