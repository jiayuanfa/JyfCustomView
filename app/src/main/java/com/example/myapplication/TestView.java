package com.example.myapplication;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.Nullable;

/**
 * 自定义View
 */
public class TestView extends View {

    /**
     * 圆的半径
     */
    private static final float RADIUS = 50f;

    /**
     * 初始化画笔，并打开抗锯齿
     * 意思就是画的图形不会带毛刺
     */
    private Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);

    public TestView(Context context) {
        super(context);
    }

    /**
     * 加入此构造方法以防止部分机型打开直接闪退
     * @param context
     * @param attrs
     */
    public TestView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    /**
     * 所有需要绘制的代码都写在这个方法里面
     * @param canvas
     */
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        /**
         * 首先来画一条线
         * 1：参数：起点、终点、画笔
         * 2：Paint控制风格
         */
        canvas.drawLine(100, 100, 200, 300, paint);

        /**
         * 画出一个圆
         * 圆心x,y以及半径、画笔
         * 这里单位是px，但是我们绘制的时候，可以使用方法把dp转化使用
         */
        canvas.drawCircle(getWidth() / 2f, getHeight() / 2f, Utils.dp2px(RADIUS, getContext()), paint);
    }
}
