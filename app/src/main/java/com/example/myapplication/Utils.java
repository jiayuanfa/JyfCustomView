package com.example.myapplication;

import android.util.TypedValue;
import android.view.View;

/**
 * 工具类
 */
public class Utils {

    /**
     * dp 转化为 px
     * @param dp
     * @param view
     * @return
     */
    public static float dp2px(float dp, View view) {
        return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, view.getResources().getDisplayMetrics());
    }
}
