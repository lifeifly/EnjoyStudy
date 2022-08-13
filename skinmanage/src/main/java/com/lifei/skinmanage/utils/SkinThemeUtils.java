package com.lifei.skinmanage.utils;

import android.app.Activity;
import android.content.Context;
import android.content.res.TypedArray;
import android.os.Build;

import com.lifei.skinmanage.skin.SkinResource;

public class SkinThemeUtils {
    private static int[] APPCOMPAT_COLOR_PRIMARY_DARK_ATTRS = {
            androidx.appcompat.R.attr.colorPrimaryDark
    };

    private static int[] STATUSBAR_COLOR_ATTRS={android.R.attr.statusBarColor,android.R.attr.navigationBarColor};

    /**
     * 获取Theme中属性定义的资源id
     * @param context
     * @param attrs
     * @return
     */
    public static int[] getResId(Context context,int[] attrs){
        int[] resIds=new int[attrs.length];
        TypedArray a=context.obtainStyledAttributes(attrs);
        for (int i = 0; i < attrs.length; i++) {
            resIds[i]=a.getResourceId(i,0);
        }
        a.recycle();
        return resIds;
    }

    public static void updateStatusBarColor(Activity activity){
        //5.0以上才能修改
        if (Build.VERSION.SDK_INT<Build.VERSION_CODES.LOLLIPOP){
            return;
        }

        //获取状态栏、导航栏颜色
        int[] resIds=getResId(activity,STATUSBAR_COLOR_ATTRS);
        int statusBarColorResId=resIds[0];
        int navigationBarColorResId=resIds[1];
        if (statusBarColorResId!=0){
            int color= SkinResource.getInstance().getColor(statusBarColorResId);
            activity.getWindow().setStatusBarColor(color);
        }else {
            //获取colorPrimaryDark
            int colorPrimaryDarkResId=getResId(activity,APPCOMPAT_COLOR_PRIMARY_DARK_ATTRS)[0];

            if (colorPrimaryDarkResId!=0){
                int color=SkinResource.getInstance().getColor(colorPrimaryDarkResId);
                activity.getWindow().setStatusBarColor(color);
            }
        }
        if (navigationBarColorResId != 0) {
            int color = SkinResource.getInstance().getColor
                    (navigationBarColorResId);
            activity.getWindow().setNavigationBarColor(color);
        }
    }
}
