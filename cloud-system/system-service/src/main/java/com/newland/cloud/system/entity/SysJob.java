package com.newland.cloud.system.entity;

import com.newland.cloud.model.BaseEntity;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 岗位
 * </p>
 *
 * @author leellun
 * @since 2023-01-14
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Schema(description="岗位")
public class SysJob extends BaseEntity {

    private static final long serialVersionUID = 1L;

    @Schema(description =  "岗位名称")
    private String name;

    /**
     * @see com.newland.cloud.enumeration.BasicEnum
     */
    @Schema(description =  "岗位状态")
    private Integer enabled;

    @Schema(description =  "排序")
    private Integer jobSort;

}
