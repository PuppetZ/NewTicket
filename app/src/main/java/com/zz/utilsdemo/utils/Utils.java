package com.zz.utilsdemo.utils;

import android.content.Context;
import android.content.SharedPreferences;

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
}
