package com.newland.cloud.system.entity;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import com.newland.cloud.model.BaseEntity;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 系统菜单
 * </p>
 *
 * @author leellun
 * @since 2023-01-14
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Schema(description="系统菜单")
public class SysMenu extends BaseEntity {

    private static final long serialVersionUID = 1L;

    @Schema(description =  "上级菜单ID")
    @JsonSerialize(using= ToStringSerializer.class)
    private Long pid;

    @Schema(description =  "子菜单数目")
    private Integer subCount;

    /**
     * @see com.newland.cloud.system.enums.MenuTypeEnum
     */
    @Schema(description =  "菜单类型")
    private Integer type;

    @Schema(description =  "菜单标题")
    private String title;

    @Schema(description =  "组件名称")
    private String name;

    @Schema(description =  "组件")
    private String component;

    @Schema(description =  "排序")
    private Integer menuSort;

    @Schema(description =  "图标")
    private String icon;

    @Schema(description =  "链接地址")
    private String path;


    @Schema(description =  "是否外链")
    private Integer target;

    /**
     * 1 启用 0 禁用
     * @see com.newland.cloud.enumeration.BasicEnum
     */
    @Schema(description =  "启用状态")
    private Integer enabled;
    /**
     * 1 缓存 0 不缓存
     * @see com.newland.cloud.enumeration.BasicEnum
     */
    @Schema(description =  "缓存")
    private Integer keepAlive;

    /**
     * 1 隐藏 0不隐藏
     * @see com.newland.cloud.enumeration.BasicEnum
     */
    @Schema(description =  "隐藏")
    private Integer hidden;

    @Schema(description =  "权限")
    private String permission;

}
