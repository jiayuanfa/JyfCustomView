package com.example.myapplication;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PathDashPathEffect;
import android.graphics.PathEffect;
import android.graphics.PathMeasure;
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
     * 用来绘制刻度的Path
     */
    private Path dash = new Path();
    private PathEffect pathEffect;

    /**
     * 测量PathMeasure
     */
    private PathMeasure pathMeasure;
    /**
     * 弧线的Path，测量用
     */
    private Path path = new Path();

    /**
     * 开口角度
     */
    private static final float OPEN_ANGLE = 120;
    private Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);

    /**
     * 指针线的长度
     */
    private static final float POINTER_LENGTH = Utils.dp2px(120f);

    /**
     * 构造方法
     * @param context
     */
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

        dash.addRect(0, 0, Utils.dp2px(2), Utils.dp2px(10), Path.Direction.CCW);
    }

    /**
     * 在这里对PathEffect进行初始化
     * @param w
     * @param h
     * @param oldw
     * @param oldh
     */
    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        path.reset();
        path.addArc(getWidth() / 2f - RADIUS, getHeight() / 2f - RADIUS, getWidth() / 2f +RADIUS, getHeight() / 2f + RADIUS, 90 + OPEN_ANGLE / 2, 360 - OPEN_ANGLE);
        /**
         * forceClosed 当我画出一个path，测量长度要不要计算封闭长度呢？
         * 我们这里肯定不需要
         */
        pathMeasure = new PathMeasure(path, false);
        // 设置刻度的间距等于path的长度/间距的个数
        /**
         * 画出来发现，最后一个刻度不完美，所以总长度要减去一个刻度的厚度
         */
        pathEffect = new PathDashPathEffect(dash, (pathMeasure.getLength() - Utils.dp2px(2)) / 20, 0, PathDashPathEffect.Style.ROTATE);
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
        canvas.drawArc(getWidth() / 2f - RADIUS, getHeight() / 2f - RADIUS, getWidth() / 2f + RADIUS, getHeight() / 2f + RADIUS, 90 + OPEN_ANGLE / 2, 360 - OPEN_ANGLE, false, paint);

        /**
         * 7: 绘制仪表盘的刻度，这里我们使用图形来画虚线，可是使用谷歌提供的一个类叫 PathDashPathEffect
         * 其实，刻度就是一个个小的长方形
         * 通过path画出来一个长方形
         * 写到PathDashPathEffect类中即可
         * 关于PathDashPathEffect的参数解释一下：advance表示每隔多少画一个，phase表示是否需要提前量（表示从哪里开始，我们这里要从头开始画，所以设置为0），最后一个是风格
         * 第二次表示画刻度，上面的是画弧线
         * 画刻度之前设置上去
         * 画刻度结束移除
         * 8：计算刻度之间的间隔，而不是写一个定值，怎么计算呢？根据弧线的长度来计算
         * 9：指针：难点在于线测朝向以及坐标
         * 首先，我们知道圆心坐标，所以就需要求线的终点坐标
         */
        paint.setPathEffect(pathEffect);
        canvas.drawArc(getWidth() / 2f - RADIUS, getHeight() / 2f - RADIUS, getWidth() / 2f +RADIUS, getHeight() / 2f + RADIUS, 90 + OPEN_ANGLE / 2, 360 - OPEN_ANGLE, false, paint);
        paint.setPathEffect(null);

        /**
         * 9：画指针
         * 起点：圆点
         * 终点：(长度*cos(正弦角度)，长度*sin(余弦角度))
         * 其实就是每个刻度的角度
         * 例如：如果是起始点刻度，则是150°，后面每增加一个刻度则增加（360-120/20 个度数
         * 我们这里先求第5个刻度的角度 即为 150 + (360-OPEN_ANGLE) / 20f * 5
         * 因为这里需要的是弧度，但是我们算出来的是角度，所以需要把弧度转成角度
         * 算出来指针长度之后 初始偏移与之相加即可
         */
        canvas.drawLine(getWidth() / 2f, getHeight() / 2f,
                getWidth() / 2f + POINTER_LENGTH * (float)Math.cos(getAngleFromMark(7)),
                 getHeight() / 2f + POINTER_LENGTH * (float) Math.sin(getAngleFromMark(7)), paint);
    }

    /**
     * 从刻度变成角度
     * Mac快捷键生成方法  选中代码 command + option + M
     * @param mark
     * @return
     */
    private double getAngleFromMark(int mark) {
        return Math.toRadians(90 + OPEN_ANGLE / 2 + (360 - OPEN_ANGLE) / 20f * mark);
    }
}
