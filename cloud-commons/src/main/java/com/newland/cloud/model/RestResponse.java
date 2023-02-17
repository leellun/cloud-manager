

package com.newland.cloud.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.newland.cloud.enumeration.ResultCode;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.ToString;

/**
 * 响应体
 *
 * @author leell
 */
@Data
@ToString
@JsonInclude(Include.NON_NULL)
@Schema(description = "响应通用参数包装")
public class RestResponse<T> {

    @Schema(description = "响应错误编码,200为正常")
    private Integer code;
    @Schema(description = "响应错误信息")
    private String message;

    @Schema(description = "响应内容")
    private T data;

    public RestResponse() {
    }

    public RestResponse(Integer code, String msg) {
        this.code = code;
        this.message = msg;
    }

    public RestResponse(Integer code, T data) {
        this.code = code;
        this.data = data;
    }

    public RestResponse(Integer code, String msg, T data) {
        this.code = code;
        this.message = msg;
        this.data = data;
    }

    public static <T> RestResponse ok(T data) {
        return new RestResponse<>(ResultCode.SUCCESS.getCode(), data);
    }

    public static <T> RestResponse ok(String msg, T data) {
        return new RestResponse<>(ResultCode.SUCCESS.getCode(), msg, data);
    }

    public static <T> RestResponse<T> success() {
        RestResponse<T> response = new RestResponse<T>();
        response.setCode(ResultCode.SUCCESS.getCode());
        return response;
    }

    public static <T> RestResponse<T> success(String message) {
        return new RestResponse<>(ResultCode.SUCCESS.getCode(), message);
    }

    public static <T> RestResponse<T> success(T data) {
        return new RestResponse<>(ResultCode.SUCCESS.getCode(), data);
    }

    public static <T> RestResponse<T> error(String msg) {
        RestResponse<T> response = new RestResponse<T>();
        response.setCode(-2);
        response.setMessage(msg);
        return response;
    }

    public static <T> RestResponse<T> error(int code, String msg) {
        RestResponse<T> response = new RestResponse<T>();
        response.setCode(code);
        response.setMessage(msg);
        return response;
    }

}
