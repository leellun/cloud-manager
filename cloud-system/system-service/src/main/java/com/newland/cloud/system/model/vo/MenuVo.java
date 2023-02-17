package com.newland.cloud.system.model.vo;

import com.newland.cloud.system.entity.SysMenu;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * 构建前端路由时用到
 */
@Schema(description = "前端路由")
@Data
public class MenuVo extends SysMenu {
    private String parentName;
}
