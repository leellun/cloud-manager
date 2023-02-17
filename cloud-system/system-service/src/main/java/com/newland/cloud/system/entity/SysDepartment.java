package com.newland.cloud.system.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import com.newland.cloud.model.BaseEntity;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

/**
 * <p>
 * 部门
 * </p>
 *
 * @author leellun
 * @since 2023-01-14
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Schema(description="部门")
public class SysDepartment extends BaseEntity {

    private static final long serialVersionUID = 1L;
    @JsonSerialize(using= ToStringSerializer.class)
    @Schema(description =  "上级部门")
    private Long pid;

    @Schema(description =  "子部门数目")
    private Integer subCount;

    @Schema(description =  "名称")
    private String name;

    @Schema(description =  "排序")
    private Integer deptSort;

    /**
     * 1 启用 0 未启用
     * @see com.newland.cloud.enumeration.BasicEnum
     */
    @Schema(description =  "状态")
    private Integer enabled;

    @TableField(exist = false)
    private List<SysDepartment> children;
}
