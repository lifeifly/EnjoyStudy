package com.lifei.customview.自定义LayoutManager;

import android.content.Context;
import android.graphics.Canvas;
import android.util.TypedValue;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;

import com.lifei.customview.自定义LayoutManager.adapter.UniversalAdapter;

import org.jetbrains.annotations.NotNull;

import java.util.List;

public class SlideCallback extends ItemTouchHelper.SimpleCallback {

    private RecyclerView mRv;
    private UniversalAdapter<SlideCardBean> adapter;
    private List<SlideCardBean> mDatas;

    public SlideCallback(RecyclerView mRv,
                         UniversalAdapter<SlideCardBean> adapter, List<SlideCardBean> mDatas) {
        super(0, ItemTouchHelper.LEFT|ItemTouchHelper.RIGHT|ItemTouchHelper.DOWN|ItemTouchHelper.UP);
        this.mRv = mRv;
        this.adapter = adapter;
        this.mDatas = mDatas;
    }
    //拖拽的时候
    @Override
    public boolean onMove(@NonNull @NotNull RecyclerView recyclerView, @NonNull @NotNull RecyclerView.ViewHolder viewHolder, @NonNull @NotNull RecyclerView.ViewHolder target) {
        return false;
    }
    //滑动的时候
    @Override
    public void onSwiped(@NonNull @NotNull RecyclerView.ViewHolder viewHolder, int direction) {
        //滑出的时候，将数据加入到最后
        int position=viewHolder.getLayoutPosition();
        SlideCardBean remove = mDatas.remove(position);
        //数据在前，就显示在后
        mDatas.add(0,remove);
        adapter.notifyDataSetChanged();
    }

    @Override
    public void onChildDraw(@NonNull @NotNull Canvas c, @NonNull @NotNull RecyclerView recyclerView, @NonNull @NotNull RecyclerView.ViewHolder viewHolder, float dX, float dY, int actionState, boolean isCurrentlyActive) {
        super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive);

        double maxDistance = recyclerView.getWidth() * 0.5f;
        double distance = Math.sqrt(dX * dX + dY * dY);
        double fraction = distance / maxDistance;


        if (fraction>1){
            fraction=1;
        }

        int itemCount = recyclerView.getChildCount();

        for (int i = 0; i < itemCount; i++) {
            //先获取的是底部那个View
            View view = recyclerView.getChildAt(i);

            int level = itemCount - i - 1;//3...0

            if (level > 0) {
                if (level < CardConfig.MAX_SHOW_COUNT - 1) {
                    //2...1，中间的需要向上移并且放大一个层次
                    view.setTranslationY((float) (CardConfig.TRANS_Y_GAP * level - fraction * CardConfig.TRANS_Y_GAP));
                    view.setScaleX((float) (1 - CardConfig.SCALE_GAP * level + fraction * CardConfig.SCALE_GAP));
                    view.setScaleY((float) (1 - CardConfig.SCALE_GAP * level + fraction * CardConfig.SCALE_GAP));
                }
            }
        }
    }
}
