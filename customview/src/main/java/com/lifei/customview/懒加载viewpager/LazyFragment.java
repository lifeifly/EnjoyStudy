package com.lifei.customview.懒加载viewpager;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.lifei.customview.R;

public class LazyFragment extends AbsLazyFragment {
    public LazyFragment(String TAG) {
        super(TAG);
    }

    public static Fragment newInstance(String tag) {
        LazyFragment lazyFragment = new LazyFragment(tag);
        return lazyFragment;
    }

    private TextView tv;

    @Override
    protected int getLayoutRes() {
        return R.layout.lazy_fragment;
    }

    @Override
    protected void findView(View rootView) {
        tv=rootView.findViewById(R.id.tv);
    }

    @Override
    protected void initView(View rootView) {
        
    }

    @Override
    protected void onLoad() {
        super.onLoad();
        tv.setText(TAG);
    }

    @Override
    protected void onStopLoad() {
        super.onStopLoad();
    }
}
