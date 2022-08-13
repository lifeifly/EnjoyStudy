package com.lifei.customview.conflict;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.viewpager.widget.ViewPager;

public class MyViewPager extends ViewPager {
    private float lastX;
    private float lastY;

    public MyViewPager(@NonNull Context context) {
        super(context);
    }

    public MyViewPager(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }
//外部拦截法
//    @Override
//    public boolean onInterceptTouchEvent(MotionEvent ev) {
//        float x = ev.getX();
//        float y = ev.getY();
//
//        switch (ev.getAction()) {
//            case MotionEvent.ACTION_DOWN:
//                lastX=x;
//                lastY=y;
//                break;
//            case MotionEvent.ACTION_MOVE:
//                if (Math.abs(lastX-x)>Math.abs(lastY-y)){
//                    return true;
//                }
//                break;
//            case MotionEvent.ACTION_UP:
//                break;
//        }
//        return super.onInterceptTouchEvent(ev);
//    }


    //内部拦截法需要

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        //事件开始ACTION_DOWN不拦截
        if (ev.getAction()==MotionEvent.ACTION_DOWN){
            super.onInterceptTouchEvent(ev);
            return false;
        }
        return true;
    }
}
