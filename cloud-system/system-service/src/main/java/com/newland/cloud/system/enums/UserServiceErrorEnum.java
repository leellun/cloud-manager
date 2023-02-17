package com.newland.cloud.system.enums;

import com.newland.cloud.enumeration.ErrorCode;

/**
 * 1000系列错误
 * 服务模块错误
 * Author: leell
 * Date: 2022/12/6 23:57:22
 */
public enum UserServiceErrorEnum implements ErrorCode {
    USER_NOT_EXIST(1001,"用户不存在！"),
    USER_PASSWORD_ERROR(1002,"用户密码错误"),
    USER_FORBIDDEN(1003,"用户未启用"),
    USER_EXIST(1004,"该用户名用户已存在！"),
    USER_PHONE_EXIST(1018,"手机号对应用户已存在！"),
    USER_EMAIL_EXIST(1019,"邮箱对应用户已存在！"),
    USER_DELETE_FAIL(1005,"删除用户失败！"),
    USER_OLD_PASSWORD_ERROR(1006,"用户旧密码错误"),
    DEPARTMENT_NOT_EXIST(1007,"当前部门不存在"),
    DEPARTMENT_EXIST(1021,"当前部门存在"),
    DEPARTMENT_PARENT_NOT_EXIST(1022,"父部门不存在"),
    DEPARTMENT_DELETE_FAIL(1008,"部门删除失败"),
    JOB_EXIST(1009,"该岗位已存在！"),
    JOB_NOT_EXIST(1010,"该岗位不存在！"),
    JOB_DELETE_FAIL(1011,"删除岗位失败！"),
    MENU_NOT_EXIST(1012,"该菜单不存在！"),
    MENU_DELETE_FAIL(1013,"菜单删除失败！"),
    MENU_PARENT_NOT_EXIST(1023,"父菜单不存在"),
    ROLE_NOT_EXIST(1014,"角色不存在！"),
    ROLE_NAME_EXIST(1015,"该名称角色存在！"),
    ROLE_CODE_EXIST(1016,"该编码角色存在！"),
    ROLE_DELETE_FAIL(1017,"角色删除失败！"),
    ROLE_NOT_SELECT(1020,"角色未选择！"),
    ;


    private Integer code;
    private String desc;

    UserServiceErrorEnum(Integer code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    @Override
    public int getCode() {
        return this.code;
    }

    @Override
    public String getDesc() {
        return this.desc;
    }
}
