package com.newland.cloud.system.model.dto;

import com.newland.mybatis.page.PageEntity;
import lombok.Data;

/**
 * 部门查询
 */
@Data
public class DeptQueryDTO extends PageEntity {

    private String name;

    private Integer enabled;
}
