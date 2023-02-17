package com.newland.cloud.utils;

import java.util.Base64;

/**
 * base64 工具
 * Author: leell
 * Date: 2023/2/8 00:01:00
 */
public class Base64Utils {
    public static String encodeToString(String str){
        return Base64.getEncoder().encodeToString(str.getBytes());
    }
    public static String decodeToString(String str){
        return new String(Base64.getDecoder().decode(str));
    }
}
