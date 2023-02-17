package com.newland.cloud.system.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * <p>
 * 角色菜单关联
 * </p>
 *
 * @author leellun
 * @since 2023-01-14
 */
@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
@Schema(description="角色菜单关联")
public class SysRoleMenu implements Serializable {

    private static final long serialVersionUID = 1L;

    @Schema(description =  "主键")
      @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @Schema(description =  "菜单ID")
    private Long menuId;

    @Schema(description =  "角色ID")
    private Long roleId;

    public SysRoleMenu(Long roleId,Long menuId){
        this.roleId=roleId;
        this.menuId=menuId;
    }
}
