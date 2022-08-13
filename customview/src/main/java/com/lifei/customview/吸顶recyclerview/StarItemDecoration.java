package com.lifei.customview.吸顶recyclerview;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import org.jetbrains.annotations.NotNull;

public class StarItemDecoration extends RecyclerView.ItemDecoration {
    private static final int HEADER_SPACE = 100;
    private static final int NORMAL_SPACE = 6;

    private Paint backgroundPaint;
    private Paint textPaint;

    public StarItemDecoration() {
        backgroundPaint = new Paint();
        backgroundPaint.setColor(Color.RED);
        backgroundPaint.setAntiAlias(true);
        backgroundPaint.setDither(true);

        textPaint = new Paint();
        textPaint.setColor(Color.WHITE);
        textPaint.setAntiAlias(true);
        textPaint.setDither(true);
    }

    @Override
    public void onDraw(@NonNull @NotNull Canvas c, @NonNull @NotNull RecyclerView parent, @NonNull @NotNull RecyclerView.State state) {
        super.onDraw(c, parent, state);
        RecyclerView.Adapter adapter = parent.getAdapter();
        if (adapter instanceof StarAdapter) {
            int left = parent.getPaddingLeft();
            int right = parent.getWidth() - parent.getPaddingRight();
            //当前子View数量
            int childCount = parent.getChildCount();
            for (int i = 0; i < childCount; i++) {
                View child = parent.getChildAt(i);
                //获取位置
                int position = parent.getChildLayoutPosition(child);
                //判断是否是组头,如果超过paddingTop就不用画了
                if (((StarAdapter) adapter).isGroupHeader(position)&&child.getTop()-HEADER_SPACE>=parent.getPaddingTop()) {
                    c.drawRect(left, child.getTop() - HEADER_SPACE, right, child.getTop(), backgroundPaint);
                    //计算基线
                    Paint.FontMetricsInt fontMetrics = textPaint.getFontMetricsInt();
                    int baseline = child.getTop() - HEADER_SPACE / 2 + (fontMetrics.descent - fontMetrics.ascent) / 2 - fontMetrics.descent;
                    String groupName = ((StarAdapter) adapter).getItemGroupName(position);
                    c.drawText(groupName, 0, groupName.length(), left + 10, baseline, textPaint);
                } else if (child.getTop()-NORMAL_SPACE>=parent.getPaddingTop()){
                    c.drawRect(left, child.getTop() - NORMAL_SPACE, right, child.getTop(), backgroundPaint);
                }
            }
        }
    }

    @Override
    public void onDrawOver(@NonNull @NotNull Canvas c, @NonNull @NotNull RecyclerView parent, @NonNull @NotNull RecyclerView.State state) {
        super.onDrawOver(c, parent, state);
        //获取第一个可见位置
        LinearLayoutManager linearLayoutManager = (LinearLayoutManager) parent.getLayoutManager();
        int firstVisiblePosition = linearLayoutManager.findFirstVisibleItemPosition();
        View firstVisibleView = parent.findViewHolderForAdapterPosition(firstVisiblePosition).itemView;

        //获取最后一个可见位置
        int lastVisiblePosition = linearLayoutManager.findLastVisibleItemPosition();
        View lastVisibleView = parent.findViewHolderForAdapterPosition(lastVisiblePosition).itemView;
        //适配器
        StarAdapter adapter = (StarAdapter) parent.getAdapter();
        //查询是否存在不一样的组头
        int groupHeaderPosition = -1;
        for (int i = firstVisiblePosition; i <= lastVisiblePosition; i++) {
            if (adapter.isGroupHeader(i)&&!adapter.getItemGroupName(i).equals(adapter.getItemGroupName(firstVisiblePosition))) {
                groupHeaderPosition = i;
                break;
            }
        }
//        boolean isGroupHeader = adapter.isGroupHeader(firstVisiblePosition + 1);
        int left = parent.getPaddingLeft();
        int right = parent.getWidth() - parent.getPaddingRight();
        int top = parent.getPaddingTop();
        String groupName = ((StarAdapter) adapter).getItemGroupName(firstVisiblePosition);

        Paint.FontMetricsInt fontMetrics = textPaint.getFontMetricsInt();

        if (groupHeaderPosition == -1) {
            //没有组头，固定画顶部
            c.drawRect(left, top, right, top + HEADER_SPACE, backgroundPaint);
            //计算基线
            int baseline = top + HEADER_SPACE / 2 + (fontMetrics.descent - fontMetrics.ascent) / 2 - fontMetrics.descent;
            c.drawText(groupName, 0, groupName.length(), left + 10, baseline, textPaint);
        } else {
            //有组头，就需要根据组头上一个item的bottom，不断向上推
            RecyclerView.ViewHolder viewHolder = parent.findViewHolderForAdapterPosition(groupHeaderPosition - 1);
            if (viewHolder == null) {
                return;
            }
            View preView = viewHolder.itemView;
            if (preView.getBottom() <= top) {
                return;
            }
            int bottom = Math.min(top + HEADER_SPACE, preView.getBottom());
            c.drawRect(left, top, right, bottom, backgroundPaint);

            // 绘制文字的高度不能超出区域
            c.clipRect(left, top, right, bottom);
            //计算基线
            int baseline = bottom - HEADER_SPACE / 2 + (fontMetrics.descent - fontMetrics.ascent) / 2 - fontMetrics.descent;
            c.drawText(groupName, 0, groupName.length(), left + 10, baseline, textPaint);
        }
//        if (isGroupHeader) {
//            //下一个位置是组头，就需要根据第一个可见位置的bottom，不断向上推
//            int bottom = Math.min(top + HEADER_SPACE, firstVisibleView.getBottom());
//            c.drawRect(left, top, right, bottom, backgroundPaint);
//            //计算基线
//            int baseline = bottom - HEADER_SPACE / 2 + (fontMetrics.descent - fontMetrics.ascent) / 2 - fontMetrics.descent;
//            c.drawText(groupName, 0, groupName.length(), left + 10, baseline, textPaint);
//        } else {
//            //下一个位置不是组头，固定在顶部画头
//            c.drawRect(left, top, right, top + HEADER_SPACE, backgroundPaint);
//            //计算基线
//            int baseline = top + HEADER_SPACE / 2 + (fontMetrics.descent - fontMetrics.ascent) / 2 - fontMetrics.descent;
//            c.drawText(groupName, 0, groupName.length(), left + 10, baseline, textPaint);
//        }
    }

    @Override
    public void getItemOffsets(@NonNull @NotNull Rect outRect, @NonNull @NotNull View view, @NonNull @NotNull RecyclerView parent, @NonNull @NotNull RecyclerView.State state) {
        super.getItemOffsets(outRect, view, parent, state);
        //为分割线预留空间
        RecyclerView.Adapter adapter = parent.getAdapter();
        if (adapter instanceof StarAdapter) {
            //获取当前view在parent中的位置
            int position = parent.getChildLayoutPosition(view);
            //判断是否是组头
            if (((StarAdapter) adapter).isGroupHeader(position)) {
                outRect.set(0, HEADER_SPACE, 0, 0);
            } else {
                outRect.set(0, NORMAL_SPACE, 0, 0);
            }
        }
    }
}
