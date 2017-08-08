package com.zz.utilsdemo.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.text.TextUtils;

import java.security.MessageDigest;

/**
 * Created by zhangjing on 2015/8/4.
 * Utils  杂 的
 */

public class Utils {


    /**
     * 判断是否第一次运行
     *
     * @param context
     * @return true 第一次   false  不是第一次
     */
    public static boolean isFirst(Context context) {
        SharedPreferences sp = context.getSharedPreferences("isFirst", Context.MODE_PRIVATE);
        boolean run = sp.getBoolean("run", true);
        if (run) {
            sp.edit().putBoolean("run", false).commit();
        }
        return run;
    }


    /**
     * MD5 加密
     * @param string   str
     * @return         加密后的str
     */
    public static String md5(String string) {
        if (TextUtils.isEmpty(string)) {
            return "";
        }
        MessageDigest messageDigest = null;
        StringBuffer md5StrBuff = new StringBuffer();
        try {
            messageDigest = MessageDigest.getInstance("MD5");
            messageDigest.reset();
            messageDigest.update(string.getBytes("UTF-8"));

            byte[] byteArray = messageDigest.digest();
            for (int i = 0; i < byteArray.length; i++) {
                if (Integer.toHexString(0xFF & byteArray[i]).length() == 1)
                    md5StrBuff.append("0").append(Integer.toHexString(0xFF & byteArray[i]));
                else md5StrBuff.append(Integer.toHexString(0xFF & byteArray[i]));
            }
        } catch (Exception e) {
            throw new RuntimeException();
        }
        return md5StrBuff.toString().toUpperCase();//字母大写
    }
}
