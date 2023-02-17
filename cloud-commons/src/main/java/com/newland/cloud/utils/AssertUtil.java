package com.newland.cloud.utils;

import com.newland.cloud.enumeration.ErrorCode;
import com.newland.cloud.exception.BusinessException;

import java.util.Arrays;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

/**
 * assert工具类
 * Author: leell
 * Date: 2022/12/6 17:44:59
 */
public class AssertUtil {
    /**
     * 断言 expression 是否为false
     * 如果true,  则抛出BusinessException异常
     *
     * @param expression
     * @param errorCode
     */
    public static void isFalse(boolean expression, ErrorCode errorCode) {
        cn.hutool.core.lang.Assert.isFalse(expression, () -> new BusinessException(errorCode.getCode(), errorCode.getDesc()));
    }

    /**
     * 断言 expression 是否为: true
     * 如果为false, 则抛出BusinessException异常
     *
     * @param expression
     * @param errorCode
     */
    public static void isTrue(boolean expression, ErrorCode errorCode) {
        cn.hutool.core.lang.Assert.isTrue(expression, () -> new BusinessException(errorCode.getCode(), errorCode.getDesc()));
    }

    public static void isNotTrue(boolean expression, ErrorCode errorCode) {
        cn.hutool.core.lang.Assert.isTrue(!expression, () -> new BusinessException(errorCode.getCode(), errorCode.getDesc()));
    }


    /**
     * 断言对象必须为 null {@code null} ，如果不为{@code null} 抛出指定类型异常
     *
     * @param object
     * @param errorCode
     * @return
     */
    public static <T> void isNull(T object, ErrorCode errorCode) {
        cn.hutool.core.lang.Assert.isNull(object, () -> new BusinessException(errorCode.getCode(), errorCode.getDesc()));
    }


    /**
     * 断言对象是否不为{@code null} ，如果为{@code null} 抛出指定类型异常
     *
     * @param object
     * @param errorCode
     * @param <T>
     * @return
     */
    public static <T> T notNull(T object, ErrorCode errorCode) {
        return cn.hutool.core.lang.Assert.notNull(object, () -> new BusinessException(errorCode.getCode(), errorCode.getDesc()));
    }

    /**
     * 断言字符串不为空字符串 ，如果空字符串抛出指定类型异常
     *
     * @param str
     * @param errorCode
     * @return
     */
    public static String notBlank(String str, ErrorCode errorCode) {
        return cn.hutool.core.lang.Assert.notBlank(str, () -> new BusinessException(errorCode.getCode(), errorCode.getDesc()));
    }

    /**
     * params是否包含t，如果不包含抛出异常
     *
     * @param t
     * @param errorCode
     * @param params
     * @param <T>
     */
    public static <T> void includeItem(T t, T[] params, ErrorCode errorCode) {
        isTrue(Arrays.stream(params).collect(Collectors.toList()).contains(t), errorCode);
    }

    /**
     * 正则检查
     *
     * @param str       源字符串
     * @param regx      正则表达式
     * @param errorCode
     * @param <T>
     */
    public static <T> void checkRegx(String str, String regx, ErrorCode errorCode) {
        Pattern pattern = Pattern.compile(regx);
        isTrue(pattern.matcher(str).find(), errorCode);
    }

    /**
     * 断言value是否在min与max之间，如果不在则抛出异常
     *
     * @param value
     * @param min
     * @param max
     * @param errorCode
     * @return
     */
    public static int checkBetween(int value, int min, int max, ErrorCode errorCode) {
        if (value >= min && value <= max) {
            return value;
        } else {
            throw new BusinessException(errorCode.getCode(),errorCode.getDesc());
        }
    }
}
