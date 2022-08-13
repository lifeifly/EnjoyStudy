package com.lifei.customview.懒加载viewpager;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.lifei.customview.R;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class LazyFragment2 extends AbsLazyFragment {
    public LazyFragment2(String TAG) {
        super(TAG);
    }

    public static Fragment newInstance(String tag) {
        LazyFragment2 lazyFragment = new LazyFragment2(tag);
        return lazyFragment;
    }

    private ViewPager vp;

    @Override
    protected int getLayoutRes() {
        return R.layout.lazy_fragment2;
    }

    @Override
    protected void findView(View rootView) {
        vp=rootView.findViewById(R.id.vp_v);
    }

    @Override
    protected void initView(View rootView) {
        List<Fragment> fragments = new ArrayList<>();
        fragments.add(LazyFragment.newInstance("Fragment Child 1"));
        fragments.add(LazyFragment.newInstance("Fragment Child 2"));
        fragments.add(LazyFragment.newInstance("Fragment Child 3"));
        fragments.add(LazyFragment.newInstance("Fragment Child 4"));
        fragments.add(LazyFragment.newInstance("Fragment Child 5"));

        vp.setAdapter(new FragmentPagerAdapter(getChildFragmentManager()) {
            @NonNull
            @NotNull
            @Override
            public Fragment getItem(int position) {
                return fragments.get(position);
            }

            @Override
            public int getCount() {
                return fragments.size();
            }
        });
    }

    @Override
    protected void onLoad() {
        super.onLoad();

    }

    @Override
    protected void onStopLoad() {
        super.onStopLoad();
    }
}
