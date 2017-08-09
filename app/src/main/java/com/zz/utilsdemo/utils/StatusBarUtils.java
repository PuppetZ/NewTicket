package com.zz.utilsdemo.utils;

import android.app.Activity;
import android.graphics.Color;
import android.os.Build;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

/**
 * Created by zhangjing on 2016/8/8.
 * 状态栏颜色  透明
 */

public class StatusBarUtils {

    /**
     * 给状态栏设置颜色
     *
     * @param activity activity
     */
    public static void setStatusBarColor(Activity activity) {
        //4.4以上
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {//5.0版本以上
                Window window = activity.getWindow();
                window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
                window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
                window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);//设置flag才能调用setStatusBarColor 来设置状态栏颜色
                window.setStatusBarColor(Color.parseColor("#2d8aff"));
            } else {
                Window window = activity.getWindow();
                // Translucent status bar
                window.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
/*
                View mStatusBarTintView = new View(activity);
                FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT, 50);
                params.gravity = Gravity.TOP;
                mStatusBarTintView.setLayoutParams(params);
                window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
                FrameLayout contentParent = (FrameLayout) window.getDecorView().findViewById(android.R.id.content);
                mStatusBarTintView.setBackgroundColor(Color.TRANSPARENT);
                contentParent.addView(mStatusBarTintView);*/
            }


        }


    }

   /* 可以在最外层的视图(getDecorView())上，添加一个视图view，领状态栏为透明的，在设置状态栏颜色时，就会把view的颜色展示
   这个是解决Drawlayout 抽屉拉出来时设置状态栏颜色的思路之一
   View mStatusBarTintView = new View(activity);
    FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT, 50);
    params.gravity =Gravity.TOP;
                mStatusBarTintView.setLayoutParams(params);
                window.getDecorView().

    setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN |View.SYSTEM_UI_FLAG_LAYOUT_STABLE);

    FrameLayout contentParent = (FrameLayout) window.getDecorView().findViewById(android.R.id.content);
                mStatusBarTintView.setBackgroundColor(Color.TRANSPARENT);
                contentParent.addView(mStatusBarTintView);*/


    /**
     * 给状态栏设置透明的
     *
     * @param activity activity
     */
    public static void setStatusBarTransparent(Activity activity) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {//4.4透明
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {//5.0 全透明实现
                Window window = activity.getWindow();
                window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
                window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);//设置全屏模式
                window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
                window.setStatusBarColor(Color.TRANSPARENT);//calculateStatusColor(Color.WHITE, (int) alphaValue)
            } else {
                Window window = activity.getWindow();
                // Translucent status bar
                window.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            }
        }
    }
}
