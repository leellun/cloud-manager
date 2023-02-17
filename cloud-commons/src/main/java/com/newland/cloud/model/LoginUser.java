package com.newland.cloud.model;

import lombok.Data;

import java.util.List;

/**
 * 当前登录用户
 */
@Data
public class LoginUser {
    private Long departmentId;
    private String username;
    private String mobile;
    private Long userId;
    private List<String> authorities;
}
