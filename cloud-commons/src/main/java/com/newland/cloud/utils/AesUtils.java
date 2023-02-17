package com.newland.cloud.utils;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

/**
 * AES 算法 对称加密
 */
public class AesUtils {
    static final String KEY_ALGORITHM = "AES";
    static final String CIPHER_ALGORITHM_ECB = "AES/ECB/PKCS5Padding";
    static final String CIPHER_ALGORITHM_CBC = "AES/CBC/PKCS5Padding";
    static final String CIPHER_ALGORITHM_CBC_NoPadding = "AES/CBC/NoPadding";
    static final String IV = "1234567812345678";
    static final String SECRET_KEY = "0123456701234567";

    /**
     * 使用AES 算法 加密，默认模式 AES/CBC/PKCS5Padding
     */
    public static String encrypt(String str) throws RuntimeException {
        try {
            Cipher cipher = Cipher.getInstance(CIPHER_ALGORITHM_CBC);
            //KeyGenerator 生成aes算法密钥
            SecretKeySpec secretKey = new SecretKeySpec(SECRET_KEY.getBytes("ASCII"), KEY_ALGORITHM);
//使用加密模式初始化 密钥
            cipher.init(Cipher.ENCRYPT_MODE, secretKey, new IvParameterSpec(IV.getBytes()));
            //按单部分操作加密或解密数据，或者结束一个多部分操作。
            byte[] encrypt = cipher.doFinal(str.getBytes());
            return byte2hex(encrypt);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static String decrypt(String encrypt) throws RuntimeException {
        try {
            Cipher cipher = Cipher.getInstance(CIPHER_ALGORITHM_CBC);
            //KeyGenerator 生成aes算法密钥
            SecretKeySpec secretKey = new SecretKeySpec(SECRET_KEY.getBytes("ASCII"), KEY_ALGORITHM);
            //使用解密模式初始化 密钥
            cipher.init(Cipher.DECRYPT_MODE, secretKey, new IvParameterSpec(IV.getBytes()));
            byte[] decrypt = cipher.doFinal(hex2byte(encrypt));
            return new String(decrypt);
        } catch (Exception e) {
            throw new RuntimeException("解密失败");
        }
    }

    public static byte[] hex2byte(String strhex) {
        if (strhex == null) {
            return null;
        }
        int l = strhex.length();
        if (l % 2 == 1) {
            return null;
        }
        byte[] b = new byte[l / 2];
        for (int i = 0; i != l / 2; i++) {
            b[i] = (byte) Integer.parseInt(strhex.substring(i * 2, i * 2 + 2),
                    16);
        }
        return b;
    }

    public static String byte2hex(byte[] b) {
        String hs = "";
        String stmp = "";
        for (int n = 0; n < b.length; n++) {
            stmp = (Integer.toHexString(b[n] & 0XFF));
            if (stmp.length() == 1) {
                hs = hs + "0" + stmp;
            } else {
                hs = hs + stmp;
            }
        }
        return hs.toUpperCase();
    }

    public static void main(String[] args) {
        String str = encrypt("123456");
        System.out.println(str);
        System.out.println(MD5.encrypt("123456"));
    }
}
