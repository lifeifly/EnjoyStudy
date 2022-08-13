package com.lifei.skinmanage.lifecycle;

import android.app.Activity;
import android.app.Application;
import android.os.Bundle;
import android.text.Layout;
import android.util.ArrayMap;
import android.view.LayoutInflater;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.view.LayoutInflaterCompat;

import com.lifei.skinmanage.factory2.SkinLayoutInflaterFactory;
import com.lifei.skinmanage.skin.SkinManager;
import com.lifei.skinmanage.utils.SkinThemeUtils;

import java.lang.reflect.Field;
import java.util.Observable;

public class ApplicationActivityLifecycle implements Application.ActivityLifecycleCallbacks {
    private Observable mObservable;
    private ArrayMap<Activity, SkinLayoutInflaterFactory> mLayoutInflaterFactories=new ArrayMap<>();

    public ApplicationActivityLifecycle(Observable mObservable) {
        this.mObservable = mObservable;
    }

    @Override
    public void onActivityCreated(@NonNull Activity activity, @Nullable Bundle savedInstanceState) {
        //更新状态栏
        SkinThemeUtils.updateStatusBarColor(activity);

        //更新布局试图
        LayoutInflater inflater=activity.getLayoutInflater();

        //将mFactorySet置为false，否则多次设置factory会报错
        try {
            Field field=LayoutInflater.class.getDeclaredField("mFactorySet");
            field.setAccessible(true);
            field.setBoolean(inflater,false);
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }

        //反射设置factory
        SkinLayoutInflaterFactory skinLayoutInflaterFactory=new SkinLayoutInflaterFactory(activity);
        LayoutInflaterCompat.setFactory2(inflater,skinLayoutInflaterFactory);

        //添加观察者
        mObservable.addObserver(skinLayoutInflaterFactory);
    }

    @Override
    public void onActivityStarted(@NonNull Activity activity) {

    }

    @Override
    public void onActivityResumed(@NonNull Activity activity) {

    }

    @Override
    public void onActivityPaused(@NonNull Activity activity) {

    }

    @Override
    public void onActivityStopped(@NonNull Activity activity) {

    }

    @Override
    public void onActivitySaveInstanceState(@NonNull Activity activity, @NonNull Bundle outState) {

    }

    @Override
    public void onActivityDestroyed(@NonNull Activity activity) {
        SkinLayoutInflaterFactory observer=mLayoutInflaterFactories.remove(activity);
        SkinManager.getInstance().deleteObserver(observer);
    }
}
