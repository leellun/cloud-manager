package com.newland.cloud.system.model.vo;

import com.newland.cloud.system.entity.SysUser;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.List;

/**
 * 用户详情
 * Author: leell
 * Date: 2022/12/7 14:16:15
 */
@Data
@Schema(description = "用户返回对象")
public class SysUserItemVo extends SysUser {
    @Schema(description = "角色id")
    private List<String> roleIds;
    private String deptName;
}
