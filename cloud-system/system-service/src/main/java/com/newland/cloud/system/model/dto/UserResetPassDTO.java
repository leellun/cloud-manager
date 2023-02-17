package com.newland.cloud.system.model.dto;

import lombok.Data;

/**
 * <p>
 * 修改密码入参
 * </p>
 */
@Data
public class UserResetPassDTO {
    private String username;
    private String newPass;
}
