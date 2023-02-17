package com.newland.cloud.system.model.dto;

import com.newland.mybatis.page.PageEntity;
import lombok.Data;

/**
 * 菜单查询
 * Author: leell
 * Date: 2023/1/10 13:14:37
 */
@Data
public class MenuQueryDTO extends PageEntity {
    private String blurry;


    private Boolean pidIsNull;

    private Long pid;

    private Integer type;
}
