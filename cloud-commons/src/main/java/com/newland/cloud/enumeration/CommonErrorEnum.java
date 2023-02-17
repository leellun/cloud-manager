package com.newland.cloud.enumeration;

/**
 * Author: leell
 * Date: 2023/2/2 20:42:48
 */
public enum CommonErrorEnum implements ErrorCode {
    DELETE_ERROR(2001,"删除失败"),
    OPERATION_FAIL(2002,"操作失败"),
    ;
    private Integer code;
    private String desc;

    CommonErrorEnum(Integer code, String desc) {
        this.code = code;
        this.desc = desc;
    }
    @Override
    public int getCode() {
        return code;
    }

    @Override
    public String getDesc() {
        return desc;
    }
}
