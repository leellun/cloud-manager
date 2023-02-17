package com.newland.cloud.system.enums;

public enum MenuTypeEnum {
    MENU(1,"菜单"),
    BUTTON(2,"按钮"),
    ;

    private Integer key;
    private String desc;
    MenuTypeEnum(Integer key,String desc){
        this.key=key;
        this.desc=desc;
    }

    public Integer getKey() {
        return key;
    }

    public String getDesc() {
        return desc;
    }
}
