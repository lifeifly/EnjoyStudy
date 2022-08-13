package com.lifei.webview;

import android.content.Context;
import android.content.Intent;

import androidx.fragment.app.Fragment;

import com.google.auto.service.AutoService;
import com.lifei.common.autoservice.IWebViewService;
import com.lifei.webview.utils.Constants;

@AutoService(IWebViewService.class)
public class WebViewServiceImpl implements IWebViewService {
    @Override
    public void startWebViewActivity(Context context, String url, String title, boolean isShowActionBar) {
        if (context == null) return;
        Intent intent = new Intent(context, WebViewActivity.class);
        intent.putExtra(Constants.TITLE, title);
        intent.putExtra(Constants.URL, url);
        intent.putExtra(Constants.SHOW_ACTIONBAR, isShowActionBar);
        context.startActivity(intent);
    }

    @Override
    public Fragment getWebViewFragment(String url, boolean autoRefresh) {
        return WebViewFragment.newInstance(url, autoRefresh);
    }

    @Override
    public void startDemoHtml(Context context) {
        if (context == null) return;
        Intent intent = new Intent(context, WebViewActivity.class);
        intent.putExtra(Constants.TITLE, "测试");
        intent.putExtra(Constants.URL, Constants.ANDROID_ASSETS_URL + "demo.html");
        intent.putExtra(Constants.SHOW_ACTIONBAR, false);
        context.startActivity(intent);
    }
}
