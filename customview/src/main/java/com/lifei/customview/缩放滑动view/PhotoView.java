package com.lifei.customview.缩放滑动view;

import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;
import android.view.View;
import android.widget.ImageView;
import android.widget.OverScroller;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import org.jetbrains.annotations.NotNull;

public class PhotoView extends View {
    //记录当前是否放大
    private boolean isLarge;
    //需要画的图片
    private Bitmap mBitmap;
    //图片放在中间时，画的起始点位置
    private int originOffsetX;
    private int originOffsetY;
    //当前画布缩放比
    private float currentScale;
    //画布最小缩放比
    private float smallScale;
    //画布最大缩放比
    private float maxScale;
    //当前需要位移量
    private float offsetX;
    private float offsetY;
    //画笔
    private Paint mPaint;
    //缩放中心
    private int centerX;
    private int centerY;
    //缩放动画执行类
    private ObjectAnimator mScaleAnimator;
    //双击、点击手势检测类
    private GestureDetector mGestureDetector;
    //缩放检测类
    private ScaleGestureDetector mScaleGestureDetector;
    //滑动处理类，带有动画回弹效果
    private OverScroller mOverScroller;

    public PhotoView(@NonNull @NotNull Context context) {
        this(context, null);
    }

    public PhotoView(@NonNull @NotNull Context context, @Nullable @org.jetbrains.annotations.Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public PhotoView(@NonNull @NotNull Context context, @Nullable @org.jetbrains.annotations.Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context) {
        mPaint = new Paint();
        mPaint.setAntiAlias(true);

        mGestureDetector = new GestureDetector(context, new PhotoGestureListener());
        mScaleGestureDetector = new ScaleGestureDetector(context, new PhotoScaleGestureListener());

        mOverScroller = new OverScroller(context);
    }

    public void setDrawable(Bitmap bitmap) {

        this.mBitmap = bitmap;
        invalidate();

    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        if (mBitmap != null) {
            //图片放在中间时，原始位置
            originOffsetX = (getWidth() - mBitmap.getWidth()) / 2;
            originOffsetY = (getHeight() - mBitmap.getHeight()) / 2;
            //缩放中序
            centerX = getWidth() / 2;
            centerY = getHeight() / 2;
            //根据图片宽高比和View的宽高比确定最大缩放比例和最小缩放比例
            if (getWidth() / getHeight() > mBitmap.getWidth() / mBitmap.getHeight()) {
                //说明图片高度高于View高度，或图片宽度小于View宽度，适合以高度撑满View
                maxScale = (float) getWidth() / mBitmap.getWidth() * 1.5f;
                smallScale = (float) getHeight() / mBitmap.getHeight();
            } else {
                //说明图片高度小于View高度，或图片宽度大于View宽度，适合以宽度撑满View
                maxScale = (float) getHeight() / mBitmap.getHeight() * 1.5f;
                smallScale = (float) getWidth() / mBitmap.getWidth();
            }
            //此时画布需要以最小缩放比，撑满View
            currentScale = smallScale;
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        if (mBitmap != null) {
            //缩放比,撑满屏幕时不位移
            float fraction = (currentScale - smallScale) / (maxScale - smallScale);
            //对画布进行平移
            canvas.translate(offsetX * fraction, offsetY * fraction);
            //画布进行缩放
            canvas.scale(currentScale, currentScale, centerX, centerY);

            canvas.drawBitmap(mBitmap, originOffsetX, originOffsetY, mPaint);
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        boolean result = mScaleGestureDetector.onTouchEvent(event);
        if (!mScaleGestureDetector.isInProgress()) {
            result = mGestureDetector.onTouchEvent(event);
        }
        return result;
    }

    private ObjectAnimator getScaleAnimator() {
        if (mScaleAnimator == null) {
            mScaleAnimator = ObjectAnimator.ofFloat(this, "currentScale",0);
        }
        return mScaleAnimator;
    }

    public float getCurrentScale() {
        return currentScale;
    }

    public void setCurrentScale(float currentScale) {
        //属性动画不断调用刷新
        this.currentScale = currentScale;
        invalidate();
    }

    /**
     * 限制位移边界
     */
    private void fixOffsetBound() {
        offsetX = Math.max(offsetX, -(mBitmap.getWidth() * maxScale - getWidth()) / 2.0f);
        offsetX = Math.min(offsetX, (mBitmap.getWidth() * maxScale - getWidth()) / 2.0f);

        offsetY = Math.max(offsetY, -(mBitmap.getHeight() * maxScale - getHeight()) / 2.0f);
        offsetY = Math.min(offsetY, (mBitmap.getHeight() * maxScale - getHeight()) / 2.0f);
    }

    //实现了手势点击和双击处理
    //SimpleOnGestureListener implements OnGestureListener, OnDoubleTapListener,  OnContextClickListener
    private class PhotoGestureListener extends GestureDetector.SimpleOnGestureListener {

        //点击按下
        @Override
        public boolean onDown(MotionEvent e) {
            //消费事件
            return true;
        }

        //双击
        @Override
        public boolean onDoubleTap(MotionEvent e) {
            //将上个缩放状态取反
            isLarge = !isLarge;
            //执行动画进行缩放
            if (isLarge) {
                //点击哪里放大哪里
                offsetX = (e.getX() - getWidth() / 2.0f) - (e.getX() - getWidth() / 2.0f) * (maxScale / smallScale);
                offsetY = (e.getY() - getHeight() / 2.0f) - (e.getY() - getHeight() / 2.0f) * (maxScale / smallScale);
                //限制边界
                fixOffsetBound();
                //需要放大
                ObjectAnimator animator=getScaleAnimator();
                animator.setFloatValues(currentScale,maxScale);
                animator.start();
            } else {
                //需要缩小
                ObjectAnimator animator=getScaleAnimator();
                animator.setFloatValues(currentScale,smallScale);
                animator.start();
            }
            return super.onDoubleTap(e);
        }

        //滑动
        @Override
        public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
            //放大状态下，才能位移
            if (isLarge) {
                offsetX -= distanceX;
                offsetY -= distanceY;
                //限制边界
                fixOffsetBound();

                invalidate();
            }
            return super.onScroll(e1, e2, distanceX, distanceY);
        }

        //惯性滑动
        @Override
        public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
            mOverScroller.fling((int) offsetX, (int) offsetY,
                    (int) velocityX, (int) velocityY,
                    -(int) ((mBitmap.getWidth() * maxScale - getWidth()) / 2.0f),
                    (int) ((mBitmap.getWidth() * maxScale - getWidth()) / 2.0f),
                    -(int) ((mBitmap.getHeight() * maxScale - getHeight()) / 2.0f),
                    (int) ((mBitmap.getHeight() * maxScale - getHeight()) / 2.0f),
                    300,300);
            //不断刷新位置,每一帧调用一次
            postOnAnimation(new FlingRunnable());
            return super.onFling(e1, e2, velocityX, velocityY);
        }
    }

    private class FlingRunnable implements Runnable {

        @Override
        public void run() {
            //如果正在执行惯性滑动，就不断调用自己
            if (mOverScroller.computeScrollOffset()) {
                postOnAnimation(this);

                offsetX = mOverScroller.getCurrX();
                offsetY = mOverScroller.getCurrY();
                invalidate();
            }
        }
    }

    private class PhotoScaleGestureListener implements ScaleGestureDetector.OnScaleGestureListener {
        //其实缩放比
        private float initScale;

        @Override
        public boolean onScale(ScaleGestureDetector detector) {
            //缩放的时候，不断相乘
            currentScale = initScale * detector.getScaleFactor();
            //及时更新缩放状态,后续双击直接恢复
            if ((currentScale < smallScale && isLarge) || (currentScale > smallScale && !isLarge)) {
                isLarge = !isLarge;
            }
            invalidate();
            return false;
        }

        @Override
        public boolean onScaleBegin(ScaleGestureDetector detector) {
            initScale = currentScale;
            return true;
        }

        @Override
        public void onScaleEnd(ScaleGestureDetector detector) {

        }
    }

}
