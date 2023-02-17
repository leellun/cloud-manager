package com.newland.cloud.model;

import com.newland.cloud.enumeration.ErrorCode;
import com.newland.cloud.enumeration.ResultCode;

/**
 * Author: leell
 * Date: 2023/2/2 20:52:03
 */
public class ErrorEntity implements ErrorCode {
    private int code;
    private String desc;

    public ErrorEntity(int code, String desc) {
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

    public static ErrorEntity error(String desc) {
        return new ErrorEntity(ResultCode.DATA_ERROR.getCode(), desc);
    }
}
