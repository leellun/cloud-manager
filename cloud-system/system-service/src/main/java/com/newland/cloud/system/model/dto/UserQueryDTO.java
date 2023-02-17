package com.newland.cloud.system.model.dto;

import com.newland.mybatis.page.PageEntity;
import lombok.Data;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * 用户查询
 * Author: leell
 * Date: 2023/1/10 13:27:40
 */
@Data
public class UserQueryDTO extends PageEntity {
    private Set<Long> deptIds = new HashSet<>();
    private String blurry;
    private Integer enabled;
    private List<String> gmtCreate;
}
