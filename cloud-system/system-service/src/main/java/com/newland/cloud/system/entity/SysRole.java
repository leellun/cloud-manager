package com.newland.cloud.system.entity;

import com.newland.cloud.model.BaseEntity;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 角色表
 * </p>
 *
 * @author leellun
 * @since 2023-01-14
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Schema(description="角色表")
public class SysRole extends BaseEntity {

    private static final long serialVersionUID = 1L;

    @Schema(description =  "名称")
    private String name;

    @Schema(description =  "编码")
    private String code;

    /**
     * @see com.newland.cloud.enumeration.BasicEnum
     */
    @Schema(description =  "状态")
    private Integer enabled;
    @Schema(description =  "角色级别")
    private Integer level;

    @Schema(description =  "描述")
    private String description;

}
