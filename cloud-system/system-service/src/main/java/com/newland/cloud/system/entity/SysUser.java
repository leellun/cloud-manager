package com.newland.cloud.system.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import com.newland.cloud.model.BaseEntity;
import com.newland.cloud.validator.Insert;
import com.newland.cloud.validator.Update;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * <p>
 * 系统用户
 * </p>
 *
 * @author leellun
 * @since 2023-01-14
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Schema(description = "系统用户")
public class SysUser extends BaseEntity implements Serializable {

    private static final long serialVersionUID = 1L;
    @NotNull(message = "部门名称不能为空",groups = {Insert.class, Update.class})
    @JsonSerialize(using= ToStringSerializer.class)
    @Schema(description =  "部门名称")
    private Long deptId;
    @JsonSerialize(using= ToStringSerializer.class)
    @Schema(description =  "岗位id")
    private Long jobId;
    @Schema(description =  "用户名")
    private String username;
    @Schema(description =  "真实姓名")
    private String realName;

    @Schema(description =  "昵称")
    private String nickName;

    /**
     * 性别
     *
     * @see com.newland.cloud.system.enums.GenderEnum
     */
    @Schema(description =  "性别 1男 0女")
    private Integer gender;

    @Pattern(regexp = "^\\d{11}$",message ="手机号码格式不正确",groups = {Insert.class, Update.class})
    @Schema(description =  "手机号码")
    private String phone;

    @Schema(description =  "邮箱")
    private String email;

    @Schema(description =  "头像地址")
    private String avatar;

    @Schema(description =  "密码")
    private String password;

    @Schema(description =  "状态：1启用、0禁用")
    private Integer enabled;

    @Schema(description =  "是否可以删除：1可以、0不可以")
    private Integer canDeleted;

    /**
     * @see com.newland.cloud.enumeration.BasicEnum
     */
    @Schema(description =  "是否必须修改密码：1是、0否")
    private Integer mustResetPwd;

    @Schema(description =  "密码连续错误次数")
    private Integer pwdFailsCount;

    @Schema(description =  "密码错误锁定时间")
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private LocalDateTime failLockTime;

    @Schema(description =  "修改密码的时间")
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private LocalDateTime pwdResetTime;

    @Schema(description =  "最后一次登陆时间")
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private LocalDateTime lastLoginTime;

}
