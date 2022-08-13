package com.lifei.skinmanage.factory2;

import android.app.Activity;
import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.android.material.slider.LabelFormatter;
import com.lifei.skinmanage.skin.SkinAttribute;
import com.lifei.skinmanage.utils.SkinThemeUtils;

import java.lang.reflect.Constructor;
import java.util.HashMap;
import java.util.Observable;
import java.util.Observer;

public class SkinLayoutInflaterFactory implements LayoutInflater.Factory2, Observer {
    private static final String[] mClassPrefixList = {
            "android.widget.",
            "android.webkit.",
            "android.app.",
            "android.view."
    };
    //记录对应VIEW的构造函数
    private static final Class<?>[] mConstructorSignature = new Class[] {
            Context.class, AttributeSet.class};
    //缓存每个构造器
    private static final HashMap<String, Constructor<? extends View>> mConstructorMap =
            new HashMap<String, Constructor<? extends View>>();

    // 当选择新皮肤后需要替换View与之对应的属性
    // 页面属性管理器
    private SkinAttribute skinAttribute;
    // 用于获取窗口的状态框的信息
    private Activity activity;

    public SkinLayoutInflaterFactory(Activity activity) {
        this.activity = activity;
        this.skinAttribute=new SkinAttribute();
    }

    @Nullable
    @Override
    public View onCreateView(@Nullable View parent, @NonNull String name, @NonNull Context context, @NonNull AttributeSet attrs) {
        View view=createSDKView(name,context,attrs);
        if (null==view){
            view=createView(name,context,attrs);
        }
        //将换肤属性加入
        if (null!=view){
            skinAttribute.look(view,attrs);
        }
        return null;
    }

    private View createSDKView(String name, Context context, AttributeSet attrs) {
        //包含.就是自定义View
        if (-1!=name.indexOf('.')){
            return null;
        }
        //依次尝试反射创建系统控件
        for (int i = 0; i < mClassPrefixList.length; i++) {
            View view=createView(mClassPrefixList[i]+name,context,attrs);
            if (view!=null){
                return view;
            }
        }
        return null;
    }

    private View createView(String name, Context context, AttributeSet attrs) {
        Constructor<? extends View> constructor=findConstructor(context,name);
        try {
            return constructor.newInstance(context, attrs);
        } catch (Exception e) {
        }
        return null;
    }

    private Constructor<? extends View> findConstructor(Context context, String name) {
        Constructor<? extends View> constructor=mConstructorMap.get(name);
        if (constructor==null){
            try {
                Class<? extends View> clazz=context.getClassLoader().loadClass(name).asSubclass(View.class);
                constructor=clazz.getConstructor(mConstructorSignature);
                mConstructorMap.put(name,constructor);
            } catch (ClassNotFoundException | NoSuchMethodException e) {
                e.printStackTrace();
            }
        }
        return constructor;
    }

    //这个一般不会调用
    @Nullable
    @Override
    public View onCreateView(@NonNull String name, @NonNull Context context, @NonNull AttributeSet attrs) {
        return null;
    }

    @Override
    public void update(Observable o, Object arg) {
        SkinThemeUtils.updateStatusBarColor(activity);
        skinAttribute.applySkin();
    }
}
