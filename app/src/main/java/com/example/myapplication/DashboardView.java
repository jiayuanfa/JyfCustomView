package com.example.myapplication;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.Nullable;

/**
 * 仪表盘View
 * 1：我们需要知道仪表盘的弧度所处的位置，也就是外部的矩形空间怎么计算
 * 2：需要知道仪表盘上刻度的起始角度与结束角度
 * 3：
 */
public class DashboardView extends View {

    private static final float RADIUS = Utils.dp2px(150f);

    private static final float STROKE_WIDTH = Utils.dp2px(3f);

    /**
     * 开口角度
     */
    private static final float OPEN_ANGLE = 120;

    private Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);

    public DashboardView(Context context) {
        super(context);
    }

    public DashboardView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    /**
     * 设置画笔属性
     */
    {
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(STROKE_WIDTH);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        /**
         * 画仪表盘的弧线
         * 1：确定弧线所在的外部正方形的区域的上下左右位置
         * 2：确定弧线的角度，比如240°
         * 3：确定开口角度120
         * 4：计算出开始角度与划过的角度
         * 5：useCenter：此参数是告诉系统你是要画扇形，还是弧线，我们要画弧线，所以设置为false
         * 6：因为需要画一条线，所以需要把画笔设置为画线
         */
        canvas.drawArc(getWidth() / 2f - RADIUS, getHeight() / 2f - RADIUS, getWidth() / 2f +RADIUS, getHeight() / 2f + RADIUS, 90 + OPEN_ANGLE / 2, 360 - OPEN_ANGLE, false, paint);
    }
}
