package com.lifei.customview.自定义LayoutManager;

import android.content.Context;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;

import androidx.recyclerview.widget.RecyclerView;

public class SlideLayoutManager extends RecyclerView.LayoutManager {


    public SlideLayoutManager( ) {

    }

    @Override
    public RecyclerView.LayoutParams generateDefaultLayoutParams() {
        return new RecyclerView.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
    }

    @Override
    public void onLayoutChildren(RecyclerView.Recycler recycler, RecyclerView.State state) {
        //这是个空方法，子类必须重写
        super.onLayoutChildren(recycler, state);
        //回收ViewHolder
        detachAndScrapAttachedViews(recycler);


        //总数据数
        int itemCount = getItemCount();
        //测量、布局子View,数量不超过最大显示个数
        int bottomPosition;
        if (itemCount >CardConfig. MAX_SHOW_COUNT) {
            bottomPosition = itemCount -CardConfig. MAX_SHOW_COUNT;
        } else {
            bottomPosition = 0;
        }

        for (int i = bottomPosition; i < itemCount; i++) {
           //复用或创建ViewHolder,并绑定数据
            View view = recycler.getViewForPosition(i);
            addView(view);
            //测量子View
            measureChildWithMargins(view,0,0);
            //布局
            int left=(getWidth()-getDecoratedMeasuredWidth(view))/2;
            int top=(getHeight()-getDecoratedMeasuredHeight(view))/2;
            layoutDecorated(view,left,top,left+getDecoratedMeasuredWidth(view),top+getDecoratedMeasuredHeight(view));

            //平移加缩放
            int level=itemCount-i-1;//3...0

            if (level>0){
                if (level<CardConfig.MAX_SHOW_COUNT-1){//1...2需要缩放平移
                    view.setTranslationY(CardConfig.TRANS_Y_GAP * level);
                    view.setScaleX(1 - CardConfig.SCALE_GAP * level);
                    view.setScaleY(1 -CardConfig. SCALE_GAP * level);
                }else {
                    //3 底部的那个和上一个平移缩放一致
                    view.setTranslationY(CardConfig.TRANS_Y_GAP * (level - 1));
                    view.setScaleX(1 -CardConfig. SCALE_GAP * (level - 1));
                    view.setScaleY(1 - CardConfig.SCALE_GAP * (level - 1));
                }

            }
        }


    }
}
