package com.newland.cloud.system.model.dto;

import lombok.Data;

/**
 * 修改密码的 Vo 类
 */
@Data
public class UserPassVO {

    private String oldPass;

    private String newPass;
}
