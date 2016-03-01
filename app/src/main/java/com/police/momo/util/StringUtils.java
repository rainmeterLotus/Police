package com.police.momo.util;

import android.text.TextUtils;

/**
 * 作者： momo on 2015/10/17.
 * 邮箱：wangzhonglimomo@163.com
 */
public class StringUtils {
    public static String[] spitString(String string) {
        if (TextUtils.isEmpty(string)) {
            return new String[0];
        }
        return string.split("；");
    }
}
