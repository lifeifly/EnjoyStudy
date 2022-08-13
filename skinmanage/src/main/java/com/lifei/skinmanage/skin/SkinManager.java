package com.lifei.skinmanage.skin;

import android.app.Application;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.res.AssetManager;
import android.content.res.Resources;
import android.text.TextUtils;

import com.lifei.skinmanage.lifecycle.ApplicationActivityLifecycle;

import java.io.File;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Observable;

public class SkinManager extends Observable {
    private volatile static SkinManager instance;

    private ApplicationActivityLifecycle skinActivityLifecycle;
    private Application mContext;

    /**
     * 初始化 必须在Application中先进行初始化
     *
     * @param application
     */
    public static void init(Application application) {
        if (instance == null) {
            synchronized (SkinManager.class) {
                if (instance == null) {
                    instance = new SkinManager(application);
                }
            }
        }
    }

    public static SkinManager getInstance() {
        return instance;
    }


    private SkinManager(Application application){
        mContext=application;
        //初始化持久化
        SkinPreference.init(application);
        //初始化资源
        SkinResource.init(application);
        //注册ActivityLifecycleCallback
        skinActivityLifecycle=new ApplicationActivityLifecycle(this);
        application.registerActivityLifecycleCallbacks(skinActivityLifecycle);
        //加载上次使用保存的皮肤
        loadSkin(SkinPreference.getInstance().getSkin());
    }

    public void loadSkin(String path) {
        if (TextUtils.isEmpty(path)) {
            //还原默认皮肤
            SkinPreference.getInstance().reset();
            SkinResource.getInstance().reset();
        }else {
            File file=new File(path);
            if (!file.exists()){
                //还原默认皮肤
                SkinPreference.getInstance().reset();
                SkinResource.getInstance().reset();
                return;
            }
            try {
                //宿主app的 resources;
                Resources appResource = mContext.getResources();

                //反射创建AssetsManager与resource
                AssetManager assetManager=AssetManager.class.newInstance();
                //反射addAssetPath方法
                Method addAssetPath=AssetManager.class.getMethod("addAssetPath",String.class);
                addAssetPath.invoke(assetManager,path);

                //根据当前设备的显示器细腻些与配置创建Resource
                Resources skinResource=new Resources(assetManager,appResource.getDisplayMetrics(),appResource.getConfiguration());

                //获取外部Apk包名
                PackageManager packageManager=mContext.getPackageManager();
                PackageInfo info=packageManager.getPackageArchiveInfo(path,PackageManager.GET_ACTIVITIES);
                String packageName=info.packageName;
                SkinResource.getInstance().applySkin(skinResource,packageName);

                //记录当前皮肤包
                SkinPreference.getInstance().setSkin(path);
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (InstantiationException e) {
                e.printStackTrace();
            } catch (NoSuchMethodException e) {
                e.printStackTrace();
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            }
        }

        //通知采集的View 更新皮肤
        //被观察者改变 通知所有观察者
        setChanged();
        notifyObservers(null);
    }


}
