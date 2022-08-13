package com.lifei.customview.behavior;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.util.LogPrinter;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.core.view.ViewCompat;

import org.jetbrains.annotations.NotNull;

public class ScrollBehavior extends CoordinatorLayout.Behavior<TextView> {
    private static final String TAG = "ScrollBehavior";
    private int mOffsetTopAndBottom;
    private int mLayoutTop;

    public ScrollBehavior() {
    }

    public ScrollBehavior(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public boolean onLayoutChild(@NonNull @NotNull CoordinatorLayout parent, @NonNull @NotNull TextView child, int layoutDirection) {
        parent.onLayoutChild(child, layoutDirection);
        //每次布局时获取当前child的高度
        mLayoutTop = child.getTop();
        return super.onLayoutChild(parent, child, layoutDirection);
    }

    /**
     * 开启嵌套滑动
     * @param coordinatorLayout
     * @param child
     * @param directTargetChild
     * @param target
     * @param axes
     * @param type
     * @return
     */
    @Override
    public boolean onStartNestedScroll(@NonNull @NotNull CoordinatorLayout coordinatorLayout, @NonNull @NotNull TextView child, @NonNull @NotNull View directTargetChild, @NonNull @NotNull View target, int axes, int type) {
        return true;
    }

    /**
     * 嵌套滑动预先处理
     *
     * @param coordinatorLayout
     * @param child
     * @param target
     * @param dx
     * @param dy
     * @param consumed
     * @param type
     */
    @Override
    public void onNestedPreScroll(@NonNull @NotNull CoordinatorLayout coordinatorLayout, @NonNull @NotNull TextView child, @NonNull @NotNull View target, int dx, int dy, @NonNull @NotNull int[] consumed, int type) {
        super.onNestedPreScroll(coordinatorLayout, child, target, dx, dy, consumed, type);

//        boolean isHideTop = isHideTop(dy, child, consumed);
//        boolean isShowTop = isShowTop(dy, child, consumed);
//        if (isHideTop || isShowTop) {
//            ViewCompat.offsetTopAndBottom(child, -consumed[1]);
//        }
        int consumedy = 0; // 记录我们消费的距离
        int offset = mOffsetTopAndBottom - dy;
        int minOffset = -getChildScrollRang(child);
        int maxOffset = 0;
        offset = offset < minOffset ? minOffset : (offset > maxOffset ? maxOffset : offset);
        ViewCompat.offsetTopAndBottom(child, offset - (child.getTop() - mLayoutTop));
        consumedy = mOffsetTopAndBottom - offset;
        // 将本次滚动到的位置记录下来
        mOffsetTopAndBottom = offset;
        consumed[1] = consumedy;
    }
    // 获取childView最大可滑动距离
    private int getChildScrollRang(View childView) {
        if (childView == null) {
            return 0;
        }
        return childView.getHeight();
    }
    private boolean isShowTop(int dy, TextView child, int[] consumed) {
        if (dy < 0 && child.getTop() <= 0) {
            Log.i(TAG,"dy:"+dy+" bottom:"+child.getBottom());
            consumed[1] = dy <= child.getTop() ? child.getTop() : dy;
            return true;
        }
        return false;
    }

    /**
     * 上滑时是否需要隐藏顶部View
     *
     * @param dy
     * @param child
     * @return
     */
    private boolean isHideTop(int dy, TextView child, int[] consumed) {
        if (dy > 0 && child.getBottom() >= 0) {
            consumed[1] = dy >= child.getBottom() ? child.getBottom() : dy;
            return true;
        }
        return false;
    }
}
