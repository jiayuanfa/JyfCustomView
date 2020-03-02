package com.example.myapplication;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
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
    private static final float RADIUS = Utils.dp2px(50f);

    /**
     * 初始化画笔，并打开抗锯齿
     * 意思就是画的图形不会带毛刺
     */
    private Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);

    /**
     * 使用Path来画图
     */
    private Path path = new Path();

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
     * 重写这个方法是为了，每次有图形需要计算的时候，就重新画
     * 而不是每次都画
     * @param w
     * @param h
     * @param oldw
     * @param oldh
     */
    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);

        /**
         * 设置重叠的地方的填充规则
         * EVEN_ODD 镂空
         * WINDING 如果都是顺时针，那么可以成为重叠图形
         * 那么到底应该选择哪个呢？
         * 这是设计师要关注的，如果接到一个设计，需要镂空，我们就要知道怎么做
         */
        path.setFillType(Path.FillType.WINDING);

        /**
         * 画一个path
         * CW: clockwise 顺时针
         * CCW: counter-clockwise 逆时针
         */
        path.addCircle(getWidth() / 2f, getHeight() / 2f, RADIUS, Path.Direction.CW);

        /**
         * 在圆的下面，画一个正方形
         * 逆时针
         */
        path.addRect(getWidth() / 2f - RADIUS, getHeight() / 2f, getWidth() / 2f + RADIUS, getHeight() / 2f + RADIUS * 2, Path.Direction.CW);
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
//        canvas.drawCircle(getWidth() / 2f, getHeight() / 2f, RADIUS, paint);

        canvas.drawPath(path, paint);
    }
}
