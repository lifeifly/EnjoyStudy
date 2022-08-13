package com.lifei.customview.taobao;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.widget.NestedScrollView;

public class MyNestedScrollView extends NestedScrollView {
    //ScrollView内部只有一个子View
    private ViewGroup contentView;
    private View headView;
    private View bottomView;
    public MyNestedScrollView(@NonNull  Context context) {
        super(context);
    }

    public MyNestedScrollView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public MyNestedScrollView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        //获取到子ViewGroup
        contentView= (ViewGroup) getChildAt(0);
        //获取上部HeadView
        headView=contentView.getChildAt(0);
        //获取到下部View
        bottomView=contentView.getChildAt(1);
        //将下部View设置为自身大小就可实现吸顶效果
        ViewGroup.LayoutParams layoutParams = bottomView.getLayoutParams();
        layoutParams.height=getMeasuredHeight();
        bottomView.setLayoutParams(layoutParams);
    }
    //Action_move,recycleView首先会把事件传到嵌套滑动的parent
    @Override
    public void onNestedPreScroll(@NonNull View target, int dx, int dy,  int[] consumed, int type) {
        super.onNestedPreScroll(target, dx, dy, consumed, type);
        //如果向上拉,headview未完全隐藏就消费当前滑动
        boolean hideTop=dy>0&&getScrollY()-headView.getMeasuredHeight()>0;
        if (!hideTop){
            //表示已经消费了
            consumed[1]=dy;
        }
    }

    @Override
    public void fling(int velocityY) {
        super.fling(velocityY);
    }
}
