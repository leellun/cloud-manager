package com.newland.cloud.system.enums;

/**
 * 性别
 */
public enum GenderEnum {
    MALE(1,"男"),
    FEMALE(0,"女")
    ;
    private Integer key;
    private String desc;
    GenderEnum(Integer key,String desc){
        this.key=key;
        this.desc=desc;
    }

    public String getDesc() {
        return desc;
    }

    public Integer getKey() {
        return key;
    }
}
