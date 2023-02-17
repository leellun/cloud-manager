package com.newland.cloud.system.model.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

/**
 * <p>
 * 角色-用户
 * </p>
 *
 */
@Data
public class UserRoleDTO {
    @NotNull(message = "角色不能为空")
    private Long roleId;
    @NotNull(message = "用户不能为空")
    private Long userId;
}
