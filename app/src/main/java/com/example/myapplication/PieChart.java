package com.example.myapplication;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.Nullable;

/**
 * 饼状图
 */
public class PieChart extends View {

    private static final float RADIUS = Utils.dp2px(150f);

    /**
     * 偏移的距离
     */
    private static final float OFFSET_LENGTH = Utils.dp2px(20);

    private static final int OFFSET_INDEX = 0;

    /**
     * 圆的上下左右边界
     */
    RectF bounds = new RectF();
    private Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);

    /**
     * 角度数组
     * @param context
     */
    int[] ANGLES = {60, 100, 120, 80};

    /**
     * 颜色数组
     */
    int[] COLORS = {Color.parseColor("#448AFF"),
            Color.parseColor("#FF8F00"),
            Color.parseColor("#9575CD"),
            Color.parseColor("#00C853")};

    public PieChart(Context context) {
        super(context);
    }

    public PieChart(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    /**
     * 为什么要写在这里呢？
     * 每次View大小改变的后，圆形的中心位置是改变的 所以要写在这里啊
     * @param w
     * @param h
     * @param oldw
     * @param oldh
     */
    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        bounds.set(getWidth() / 2f - RADIUS, getHeight() / 2f - RADIUS, getWidth() / 2f + RADIUS, getHeight() / 2f + RADIUS);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        /**
         * 画一个扇形
         * 1：正方形和半径确定边界
         * 2：起始角度确定扇形大小
         * 3：实心
         * 4：涂色
         * 5：for循环添加所有饼图
         * 6：添加点击动画
         * 怎么增加动画呢？每次移动画布即可
         * 首先，要算出每个扇形，中线的角度，这个角度的计算方法等于每次偏移的(起始角度+偏移角度)/2
         * 然后通过正弦和余弦以及便宜的距离，重新计算原点
         * 移动画布：画之前保存，画之后恢复
         */

        // 记录下一个起始角度
        int currentAngle = 0;
        for (int i = 0; i < ANGLES.length; i++) {
            /**
             * 画之前，先保存画布
             */
            if (i == OFFSET_INDEX) {
                canvas.save();
                /**
                 * 通过正弦、余弦来计算偏移量，不用看正负，因为是自动的
                 */
                canvas.translate(OFFSET_LENGTH * (float) Math.cos(Math.toRadians(currentAngle + ANGLES[i] / 2f)),
                        OFFSET_LENGTH * (float) Math.sin(Math.toRadians(currentAngle + ANGLES[i] / 2f)));
            }
            paint.setColor(COLORS[i]);
            canvas.drawArc(bounds, currentAngle, ANGLES[i], true, paint);
            currentAngle += ANGLES[i];

            /**
             * 画之后，重置，以防止其他位置错误
             */
            if (i == OFFSET_INDEX) {
                canvas.restore();
            }
        }
    }
}
