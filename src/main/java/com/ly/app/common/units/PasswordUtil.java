package com.ly.app.common.units;

import cn.hutool.crypto.digest.DigestUtil;

import java.nio.charset.StandardCharsets;

/**
 * @author 刘燚
 * @version v1.0.0
 * @Description TODO
 * @createDate：2025/5/31 17:20
 * @email liuyia2022@163.com
 */
public class PasswordUtil {
    public static String encryptMd5(String password, String salt)  {
        String text = salt + password + salt;
        return DigestUtil.md5Hex(text.getBytes(StandardCharsets.UTF_8));
    }
    public static String encryptMd5(String password)  {
        return DigestUtil.md5Hex(password.getBytes(StandardCharsets.UTF_8));
    }
}
