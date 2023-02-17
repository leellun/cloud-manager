package com.newland.cloud.system.model.dto;

import com.newland.mybatis.page.PageEntity;
import lombok.Data;

/**
 * 下属用户
 * Author: leell
 * Date: 2023/1/10 13:24:11
 */
@Data
public class LevelRoleDTO extends PageEntity {
    private Integer roleId;
    private String blurry;
}
