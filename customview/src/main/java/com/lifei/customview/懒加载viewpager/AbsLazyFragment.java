package com.lifei.customview.懒加载viewpager;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.logging.Logger;

public abstract class AbsLazyFragment extends Fragment {
    protected String TAG = "";
    private View rootView;
    //上次是否可见
    public boolean lastVisibleHint = false;

    public AbsLazyFragment(String TAG) {
        this.TAG = TAG;
    }

    @Override
    public void onAttach(@NonNull @NotNull Context context) {
        super.onAttach(context);

        Log.i(TAG, "onAttach");
    }

    @Override
    public void onCreate(@Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        Log.i(TAG, "onCreate");
    }

    @Nullable
    @org.jetbrains.annotations.Nullable
    @Override
    public View onCreateView(@NonNull @NotNull LayoutInflater inflater, @Nullable @org.jetbrains.annotations.Nullable ViewGroup container, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        Log.i(TAG, "onCreateView");
        if (rootView == null) {
            rootView = inflater.inflate(getLayoutRes(), container, false);
        }
        findView(rootView);
        initView(rootView);
        //如果第一次创建视图，需要手动分发，因为上层setUserVisibleHint不会分发
        if (getUserVisibleHint()) {
            setUserVisibleHint(true);
        }
        return rootView;
    }


    /**
     * 页面未缓存加载时，此方法会在所有生命周期方法前调用
     * 页面加载后点击页面通过该方法去使页面可见
     *
     * @param isVisibleToUser
     */
    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        //如果此时还没创建视图，不分发事件，待创建视图后手动分发
        if (rootView == null) return;

        if (!lastVisibleHint && isVisibleToUser) {
            //上次不可见这次可见，需要加载
            dispatchUserVisibleHint(true);
            //子Fragment也需要去分发事件
            dispatchChildUserVisibleHint(true);
        } else if (lastVisibleHint && !isVisibleToUser) {
            //上次可见这次不可见，需要停止加载
            dispatchUserVisibleHint(false);
            dispatchChildUserVisibleHint(false);
        }
    }

    // TODO 判断 父控件 是否可见， 什么意思？ 例如：  Fragment2_vp1子Fragment  的  父亲/父控件==Fragment2
    private boolean isParentInvisible() {
        Fragment parentFragment = getParentFragment();
        if (parentFragment instanceof LazyFragment2) {
            LazyFragment2 fragment = (LazyFragment2) parentFragment;
            return !fragment.lastVisibleHint;
        }
        return false;
    }

    public void dispatchUserVisibleHint(boolean isVisible) {
        //如果父View不可见，就不去分发
        if (isParentInvisible()) return;

        lastVisibleHint = isVisible;
        if (isVisible) {
            //可见事件，加载数据
            onLoad();
        } else {
            onStopLoad();
        }
    }

    private void dispatchChildUserVisibleHint(boolean isVisible) {
        List<Fragment> children = getChildFragmentManager().getFragments();
        for (Fragment child : children) {
            if (child instanceof LazyFragment
                    && !child.isHidden()
                    && child.getUserVisibleHint()) {
                ((LazyFragment) child).dispatchUserVisibleHint(isVisible);
            }
        }
    }

    /**
     * 加载数据
     */
    protected void onLoad() {
        Log.i(TAG, "加载数据中");
    }

    /**
     * 停止加载数据
     */
    protected void onStopLoad() {
        Log.i(TAG, "停止加载数据");
    }

    protected abstract int getLayoutRes();

    protected abstract void findView(View rootView);

    protected abstract void initView(View rootView);

    @Override
    public void onActivityCreated(@Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        Log.i(TAG, "onActivityCreated");
    }

    @Override
    public void onStart() {
        super.onStart();
        Log.i(TAG, "onStart");
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.i(TAG, "onResume");
        //如果从此fragment跳转到其它页面，不会走setUserVisibleHint方法，需要去停止加载
        //如果此时不可见，但是userVisbibleHint为true，实际就是可见的，可以重新加载
        if (!lastVisibleHint && getUserVisibleHint()) {
            dispatchUserVisibleHint(true);
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        Log.i(TAG, "onPause");

        //如果从此fragment跳转到其它页面，不会走setUserVisibleHint方法，需要去停止加载
        //如果此时可见，需要停止加载
        if (getUserVisibleHint() && lastVisibleHint) {
            dispatchUserVisibleHint(false);
        }
    }

    @Override
    public void onStop() {
        super.onStop();
        Log.i(TAG, "onStop");
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        Log.i(TAG, "onDestroyView");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.i(TAG, "onDestroy");
    }

    @Override
    public void onDetach() {
        super.onDetach();
        Log.i(TAG, "onDetach");
    }


}
