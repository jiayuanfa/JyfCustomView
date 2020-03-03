package com.example.myapplication;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.RectF;
import android.graphics.Xfermode;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.Nullable;

/**
 * 头像
 */
public class AvatorView extends View {

    private static final float WIDTH = Utils.dp2px(200);
    private static final float OFFSET = Utils.dp2px(20);

    Bitmap bitmap;
    Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);

    /**
     * 转换器
     */
    Xfermode xfermode = new PorterDuffXfermode(PorterDuff.Mode.SRC_IN);
    /**
     * 需要离屏缓冲的区域
     */
    RectF bounds = new RectF();

    public AvatorView(Context context) {
        super(context);
    }

    public AvatorView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    {
        bitmap = getAvatar((int) WIDTH);
        bounds.set(OFFSET, OFFSET, OFFSET + WIDTH, OFFSET + WIDTH);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        /**
         * 外层套上一个黑边
         */
        float radius = WIDTH / 2;
        canvas.drawCircle(OFFSET + radius, OFFSET + radius, radius + Utils.dp2px(10), paint);

        /**
         * 通过此方法，拿出来一块离屏缓冲，其实就是一层虚无缥缈的区域
         */
        int saved = canvas.saveLayer(bounds, paint);

        /**
         * 裁剪圆形头像
         * 1：画一个圆形
         * 2：再画一个头像位图
         * 3：圆形离屏缓冲
         * 4：使用Xfermode转换
         * 5：套圈
         */
        canvas.drawCircle(OFFSET + radius, OFFSET + radius, radius, paint);

        paint.setXfermode(xfermode);
        canvas.drawBitmap(bitmap, OFFSET, OFFSET, paint);
        paint.setXfermode(null);

        /**
         * 恢复离屏缓冲区域
         */
        canvas.restoreToCount(saved);
    }

    /**
     * 把一张图片转化为位图
     * @param width
     * @return
     */
    Bitmap getAvatar(int width) {
        BitmapFactory.Options options = new BitmapFactory.Options();

        /**
         * 解析两次为了性能，搞出来我们需要的尺寸
         */
        options.inJustDecodeBounds = true;  // 把边界解析出来，知道图片多大
        BitmapFactory.decodeResource(getResources(), R.drawable.android_robot, options);
        options.inJustDecodeBounds = false; // 关掉

        options.inDensity = options.outWidth;
        options.inTargetDensity = width;
        return BitmapFactory.decodeResource(getResources(), R.drawable.android_robot);
    }
}
