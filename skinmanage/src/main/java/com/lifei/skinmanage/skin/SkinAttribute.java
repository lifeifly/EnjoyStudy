package com.lifei.skinmanage.skin;

import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.lifei.skinmanage.utils.SkinThemeUtils;

import java.util.ArrayList;
import java.util.List;

public class SkinAttribute {
    private static final List<String> mAttributes = new ArrayList<>();

    static {
        mAttributes.add("background");
        mAttributes.add("src");
        mAttributes.add("textColor");
        mAttributes.add("drawableLeft");
        mAttributes.add("drawableRight");
        mAttributes.add("drawaleTop");
        mAttributes.add("drawableBottom");
    }

    //记录换肤需要的每个View及每个View需要换肤的信息
    private List<SkinView> mSkinViews = new ArrayList<>();

    public void look(View view, AttributeSet attrs) {
        List<SkinPair> mSkinPairs = new ArrayList<>();

        for (int i = 0; i < attrs.getAttributeCount(); i++) {
            //获取属性名
            String attributeName = attrs.getAttributeName(i);
            if (mAttributes.contains(attributeName)) {
                //写死的#000000不能加入
                //@0000和？0000可以加入
                String attributeValue = attrs.getAttributeValue(i);
                if (attributeValue.startsWith("#")) {
                    continue;
                }
                int resId;
                //以？开头
                if (attributeValue.startsWith("?")) {
                    int attrId = Integer.parseInt(attributeValue.substring(1));
                    resId = SkinThemeUtils.getResId(view.getContext(), new int[]{attrId})[0];
                } else {
                    //以@开头的
                    resId = Integer.parseInt(attributeValue.substring(1));
                }

                SkinPair skinPair = new SkinPair(attributeName, resId);
                mSkinPairs.add(skinPair);
            }
        }
        if (!mSkinPairs.isEmpty() || view instanceof SkinViewSupport) {
            SkinView skinView = new SkinView(view, mSkinPairs);
            //如果选择皮肤，重新调用一次加载皮肤资源
            skinView.applySKin();
            mSkinViews.add(skinView);
        }
    }

    /*
       对所有的view中的所有的属性进行皮肤修改
     */
    public void applySkin() {
        for (SkinView mSkinView : mSkinViews) {
            mSkinView.applySKin();
        }
    }

    static class SkinView {
        View view;
        //记录每个属性名和属性名对应的属性值id
        List<SkinPair> skinPairs;

        public SkinView(View view, List<SkinPair> skinPairs) {
            this.view = view;
            this.skinPairs = skinPairs;
        }

        public void applySKin() {
            applySkinSupport();
            for (SkinPair skinPair : skinPairs) {
                Drawable left = null, right = null, top = null, bottom = null;
                switch (skinPair.attributeName) {
                    case "background":
                        Object background = SkinResource.getInstance().getBackground(skinPair.resId);
                        if (background instanceof Integer) {
                            view.setBackgroundColor((Integer) background);
                        } else {
                            view.setBackground((Drawable) background);
                        }
                        break;
                    case "src":
                        background = SkinResource.getInstance().getBackground(skinPair.resId);
                        if (background instanceof Integer) {
                            ((ImageView) view).setImageDrawable(new ColorDrawable((Integer) background));
                        } else {
                            ((ImageView) view).setImageDrawable((Drawable) background);
                        }
                        break;
                    case "textColor":
                        background = SkinResource.getInstance().getColorStateList(skinPair.resId);
                        ((TextView) view).setTextColor((ColorStateList) background);
                        break;
                    case "drawableLeft":
                        left = SkinResource.getInstance().getDrawble(skinPair.resId);
                        break;
                    case "drawableRight":
                        right = SkinResource.getInstance().getDrawble(skinPair.resId);
                        break;
                    case "drawableTop":
                        top = SkinResource.getInstance().getDrawble(skinPair.resId);
                        break;
                    case "drawableBottom":
                        bottom = SkinResource.getInstance().getDrawble(skinPair.resId);
                        break;
                    default:
                        break;
                }
                if (left != null || right != null || top != null || bottom != null) {
                    ((TextView) view).setCompoundDrawablesWithIntrinsicBounds(left, top, right, bottom);
                }
            }
        }

        private void applySkinSupport() {
            if (view instanceof SkinViewSupport) {
                ((SkinViewSupport) view).applySkin();
            }
        }
    }

    static class SkinPair {
        String attributeName;
        int resId;

        public SkinPair(String attributeName, int resId) {
            this.attributeName = attributeName;
            this.resId = resId;
        }
    }
}
