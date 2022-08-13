package com.lifei.customview;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

public class FlowLayout extends ViewGroup {
    private static final int HORIZONTAL_SPACE = 10;
    private static final int VERTICAL_SPACE = 10;
    //记录每行的子View
    private List<List<View>> mViewsEveryRow;
    //记录每行的高度
    private List<Integer> mViewsHeightEveryRow;

    public FlowLayout(Context context) {
        this(context,null);
    }

    public FlowLayout(Context context, AttributeSet attrs) {
        this(context, attrs,0);
    }

    public FlowLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init(){
        mViewsEveryRow=new ArrayList<>();
        mViewsHeightEveryRow=new ArrayList<>();
    }
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        //onMeasure可能会被调用多次
        mViewsHeightEveryRow.clear();
        mViewsEveryRow.clear();

        //当前View最大宽度
        int maxWidth = MeasureSpec.getSize(widthMeasureSpec);

        //每行累加宽度
        int sumWidth = 0;
        //累加高度
        int sumHeight = 0;
        //每行最大高度
        int maxHeightForRow = 0;
        //最大的行宽
        int maxWidthForRow=0;
        //每行View集合
        List<View> views = new ArrayList<>();
        //测量所有子View
        for (int i = 0; i < getChildCount(); i++) {
            View childView = getChildAt(i);
            LayoutParams params = childView.getLayoutParams();
            int childWidthMs = getChildMeasureSpec(widthMeasureSpec, getPaddingLeft() + getPaddingRight(), params.width);
            int childHeightMs = getChildMeasureSpec(heightMeasureSpec, getPaddingTop() + getPaddingBottom(), params.height);

            childView.measure(childWidthMs, childHeightMs);

            //如果当前子View宽度加上之前的宽度大于最大宽度，需要换行
            if (sumWidth + HORIZONTAL_SPACE + childView.getMeasuredWidth()> maxWidth) {
                maxWidthForRow=Math.max(sumWidth,maxWidthForRow);

                sumWidth = 0;

                mViewsEveryRow.add(views);
                views = new ArrayList<>();
                mViewsHeightEveryRow.add(maxHeightForRow);

                sumHeight += maxHeightForRow+VERTICAL_SPACE;
                maxHeightForRow = 0;
            }
            sumWidth = sumWidth + HORIZONTAL_SPACE + childView.getMeasuredWidth();
            maxHeightForRow = Math.max(childView.getMeasuredHeight(), maxHeightForRow);
            views.add(childView);
        }

        //最后一行单独统计
        maxWidthForRow=Math.max(sumWidth,maxWidthForRow);
        sumHeight+=maxHeightForRow+VERTICAL_SPACE;
        mViewsEveryRow.add(views);
        mViewsHeightEveryRow.add(maxHeightForRow);

        int realWidth=MeasureSpec.getMode(widthMeasureSpec)==MeasureSpec.EXACTLY?MeasureSpec.getMode(widthMeasureSpec):maxWidthForRow;
        int realHeight=MeasureSpec.getMode(heightMeasureSpec)==MeasureSpec.EXACTLY?MeasureSpec.getMode(heightMeasureSpec):sumHeight;

        setMeasuredDimension(realWidth,realHeight);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        int paddingTop=getPaddingTop();
        int paddingBottom = getPaddingBottom();
        int paddingLeft = getPaddingLeft();
        int paddingRight = getPaddingRight();
        //高度累计
        int sumHeight=paddingTop;
        for (int i = 0; i < mViewsEveryRow.size(); i++) {
            //当前行的子View
            List<View> viewForRow=mViewsEveryRow.get(i);
            //当前行的高度
            int heightForRow=mViewsHeightEveryRow.get(i);
            //每行的宽度累计
            int widthForWidth=paddingLeft;
            for (int j = 0; j < viewForRow.size(); j++) {
                View view=viewForRow.get(j);

                view.layout(widthForWidth,sumHeight,widthForWidth+view.getMeasuredWidth(),sumHeight+view.getMeasuredHeight());

                widthForWidth+=view.getMeasuredWidth()+HORIZONTAL_SPACE;

            }
            sumHeight+=(heightForRow+VERTICAL_SPACE);
        }
    }
}
