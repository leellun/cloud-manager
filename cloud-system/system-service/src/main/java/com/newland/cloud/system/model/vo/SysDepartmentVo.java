package com.newland.cloud.system.model.vo;

import com.newland.cloud.system.entity.SysDepartment;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 上级部门名称
 * Author: leell
 * Date: 2023/1/28 18:49:34
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class SysDepartmentVo extends SysDepartment {
    private String parentName;
}
