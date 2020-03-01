package com.example.myapplication;

import android.content.res.Resources;
import android.util.TypedValue;

/**
 * 工具类
 */
public class Utils {

    /**
     * dp 转化为 px
     * @param dp
     * 为什么需要一个参数为context ?
     * 通过getResources().getDisplayMetrics()方法我们得知，这句话的意思是跟显示相关的信息放在资源文件里面
     * 所以，这里也可以改成 Resources.system().getDisplayMetrics 获取跟系统资源相关的信息
     * 那么这两者获取资源有什么区别呢？区别就是后者不使用任何的对象就可以拿到
     * 资源信息包含什么？我们自定义的字符串、图片等，以及手机像素等都放在里面
     * @return
     */
    public static float dp2px(float dp) {
        return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, Resources.getSystem().getDisplayMetrics());
    }
}
