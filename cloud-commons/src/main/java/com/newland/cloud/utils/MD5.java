package com.newland.cloud.utils;

import java.security.MessageDigest;

/**
 * Author: leell
 * Date: 2022/8/10 19:39:16
 */
public final class MD5 {
    private static final char hexChars[] = {'0', '1', '2', '3', '4', '5', '6', '7', '8',
            '9', 'a', 'b', 'c', 'd', 'e', 'f'};

    public static String encrypt(String strStr) {
        try {
            byte[] bytes = strStr.getBytes();
            MessageDigest digest = MessageDigest.getInstance("MD5");
            digest.update(bytes);
            bytes=digest.digest();
            char[] chars = new char[bytes.length * 2];
            for (int i = 0; i < bytes.length; i++) {
                chars[2 * i] = hexChars[bytes[i] >>> 4 & 0xf];
                chars[2 * i + 1] = hexChars[bytes[i] & 0xf];
            }
            return new String(chars);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("MD5加密错处!!+" + e);
        }
    }
}
