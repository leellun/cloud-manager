package com.newland.cloud.system.model.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * 重置密码
 * Author: leell
 * Date: 2022/12/7 00:59:05
 */
@Data
@Schema(description="修改密码对象")
public class ResetPwdDto {
    @Schema(description = "密码")
    private String password;
    @Schema(description = "新密码")
    private String newPassword;
}
