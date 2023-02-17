package com.newland.cloud.security.enumeration;

public enum SecurityErrorCode  {
    ACCESS_DENIED(1403,"没有权限");

    private int code;
    private String desc;

    public int getCode() {
        return code;
    }

    public String getDesc() {
        return desc;
    }

    private SecurityErrorCode(int code, String desc) {
        this.code = code;
        this.desc = desc;
    }
}
