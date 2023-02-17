package com.newland.cloud.system.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

/**
 * 登录dto
 * Author: leell
 * Date: 2022/12/6 15:09:37
 */
@Data
@Schema(description = "账户注册信息")
public class LoginDTO {

    @NotEmpty(message = "请输入用户名")
    @Schema(description = "用户名")
    private String username;
    @NotEmpty(message = "请输入密码")
    @Schema(description = "密码")
    private String password;

}
