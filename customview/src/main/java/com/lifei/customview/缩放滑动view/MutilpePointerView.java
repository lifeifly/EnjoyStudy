package com.lifei.customview.缩放滑动view;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import org.jetbrains.annotations.NotNull;

public class MutilpePointerView extends View {

    private static final String TAG = "MutilpePointerView";
    private int currentPointerId;
    private float downX;
    private float downY;
    private float offsetX;
    private float offsetY;


    private Bitmap bitmap;

    private Paint mPaint;
    private float lastOffsetX;
    private float lastOffsetY;

    public MutilpePointerView(@NonNull @NotNull Context context) {
        this(context, null);
    }

    public MutilpePointerView(@NonNull @NotNull Context context, @Nullable @org.jetbrains.annotations.Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public MutilpePointerView(@NonNull @NotNull Context context, @Nullable @org.jetbrains.annotations.Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context) {
        mPaint = new Paint();
        mPaint.setAntiAlias(true);
    }

    public void setBitmap(Bitmap bitmap) {
        this.bitmap = bitmap;
        invalidate();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        if (null != bitmap) {
            canvas.translate(offsetX, offsetY);

            canvas.drawBitmap(bitmap, 0, 0, mPaint);
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getActionMasked()) {
            case MotionEvent.ACTION_DOWN:
                //记录当前按下的位置
                downX = event.getX();
                downY = event.getY();
                //第一根手指id一定是0
                currentPointerId = 0;
                //记录上次偏移量
                lastOffsetX = offsetX;
                lastOffsetY = offsetY;
                break;
            case MotionEvent.ACTION_MOVE:
                //ACTION_MOVE并没有跟踪具体哪个手指
                int curId = event.findPointerIndex(currentPointerId);

                //如果当前手指是最后指定的手指，就计算位置，实现位移
                offsetX = lastOffsetX + event.getX(curId) - downX;
                offsetY = lastOffsetY + event.getY(curId) - downY;
                invalidate();

                break;
            case MotionEvent.ACTION_POINTER_DOWN:
                //以后面按下的手指为准
                int curPointerIndex = event.getActionIndex();
                currentPointerId = event.getPointerId(curPointerIndex);
                //记录上次偏移量
                lastOffsetX = offsetX;
                lastOffsetY = offsetY;
                //记录当前按下位置
                downX = event.getX(curPointerIndex);
                downY = event.getY(curPointerIndex);
                break;
            case MotionEvent.ACTION_POINTER_UP:
                //当前手指
                int curPointerIndex1 = event.getActionIndex();
                int curPointerId1 = event.getPointerId(curPointerIndex1);

                //如果松开的是当前记录的手指
                if (curPointerId1 == currentPointerId) {
                    if (curPointerIndex1 == event.getPointerCount() - 1) {
                        //如果是最后一根手指，则前一个手指作为目标手指
                        curPointerIndex1 = event.getPointerCount() - 2;
                    } else {
                        //如果不是最后一根手指，则取后一根手指为目标手指
                        curPointerIndex1 = curPointerIndex1++;
                    }
                    //获取当前id
                    currentPointerId = event.getPointerId(curPointerIndex1);
                    //以当前目标手指位置为准
                    lastOffsetX = offsetX;
                    lastOffsetY = offsetY;
                    offsetX = event.getX(curPointerIndex1);
                    offsetY = event.getY(curPointerIndex1);
                }
                break;
            case MotionEvent.ACTION_UP:
                break;
        }
        return true;
    }
}
