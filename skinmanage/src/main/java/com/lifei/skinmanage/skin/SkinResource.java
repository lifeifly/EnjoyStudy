package com.lifei.skinmanage.skin;

import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.text.TextUtils;

public class SkinResource {
    //皮肤包名称
    private String mSkinPkgName;
    private boolean isDeafaultSkin = true;
    //app原始resources
    private Resources mAppResources;
    //皮肤包resources
    private Resources mSkinResources;

    private SkinResource(Context context) {
        mAppResources = context.getResources();
    }

    //防止指令重排序
    private static volatile SkinResource instance;

    public static void init(Context context) {
        if (instance == null) {
            synchronized (SkinResource.class) {
                if (instance == null) {
                    instance = new SkinResource(context);
                }
            }
        }
    }
    public static SkinResource getInstance(){
        return instance;
    }
    public void reset() {
        mSkinResources = null;
        mSkinPkgName = "";
        isDeafaultSkin = true;
    }

    public void applySkin(Resources resources, String pkgName) {
        mSkinResources = resources;
        mSkinPkgName = pkgName;
        isDeafaultSkin = TextUtils.isEmpty(pkgName) || resources == null;
    }

    /**
     * 通过原包资源Id获取到原包资源名
     * 根据资源名获取皮肤包的Id
     *
     * @param resId 原包资源Id
     * @return
     */
    public int getIdentifier(int resId) {
        if (isDeafaultSkin) {
            return resId;
        }
        String resName = mAppResources.getResourceEntryName(resId);
        String resType = mAppResources.getResourceTypeName(resId);
        int skinId = mSkinResources.getIdentifier(resName, resType, mSkinPkgName);
        return skinId;
    }

    public int getColor(int resId) {
        if (isDeafaultSkin) {
            return mAppResources.getColor(resId);
        }
        int skinId = getIdentifier(resId);
        if (skinId == 0) {
            return mAppResources.getColor(resId);
        }
        return mSkinResources.getColor(skinId);
    }

    public ColorStateList getColorStateList(int resId) {
        if (isDeafaultSkin) {
            return mAppResources.getColorStateList(resId);
        }
        int skinId = getIdentifier(resId);
        if (skinId == 0) {
            return mAppResources.getColorStateList(resId);
        }
        return mSkinResources.getColorStateList(skinId);
    }

    public Drawable getDrawble(int resId) {
        if (isDeafaultSkin) {
            return mAppResources.getDrawable(resId);
        }
        int skinId = getIdentifier(resId);
        if (skinId == 0) {
            return mAppResources.getDrawable(resId);
        }
        return mSkinResources.getDrawable(skinId);
    }
    public Object getBackground(int resId){
        String resourceTypeName=mAppResources.getResourceTypeName(resId);
        if ("color".equals(resourceTypeName)){
            return getColor(resId);
        }else {
            return getDrawble(resId);
        }
    }
}
