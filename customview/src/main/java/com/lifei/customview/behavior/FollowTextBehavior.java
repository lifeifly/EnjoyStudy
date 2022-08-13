package com.lifei.customview.behavior;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.core.view.ViewCompat;
import androidx.core.view.ViewParentCompat;
import androidx.recyclerview.widget.RecyclerView;

import org.jetbrains.annotations.NotNull;

public class FollowTextBehavior extends CoordinatorLayout.Behavior<RecyclerView> {
    public FollowTextBehavior() {
    }

    public FollowTextBehavior(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    /**
     * 依赖TextView位置变化，从而实现自身上下移动
     *
     * @param parent
     * @param child
     * @param dependency
     * @return
     */
    @Override
    public boolean layoutDependsOn(@NonNull @NotNull CoordinatorLayout parent, @NonNull @NotNull RecyclerView child, @NonNull @NotNull View dependency) {
        return dependency instanceof TextView;
    }


    @Override
    public boolean onDependentViewChanged(@NonNull @NotNull CoordinatorLayout parent, @NonNull @NotNull RecyclerView child, @NonNull @NotNull View dependency) {
        int dependencyBottom = dependency.getBottom();
        int childTop = child.getTop();
        int offset = dependencyBottom - childTop;
        ViewCompat.offsetTopAndBottom(child, offset);
        return super.onDependentViewChanged(parent, child, dependency);
    }
}
